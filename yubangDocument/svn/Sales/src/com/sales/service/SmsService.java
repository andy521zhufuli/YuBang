package com.sales.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.text.rtf.RTFEditorKit;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.google.gson.Gson;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.HttpUtil;
import com.sales.common.until.ReturnCode;
import com.sales.config.Config;
import com.sales.dao.impl.TConfigDAOImpl;
import com.sales.dao.impl.TMsgDAOImpl;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TMsg;
import com.sales.model.TUsr;
import com.sales.service.sms.SMSContentModel;
import com.sales.service.sms.SMSResponseModel;
import com.sales.vo.SendSmsResp;
import com.sales.vo.VerifySmsResp;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Service
@Transactional
public class SmsService {

	final private static JLogger logger = LoggerUtils
			.getLogger(SmsService.class);

	@Autowired
	private TConfigDAOImpl tConfigDAOImpl;

	@Autowired
	private TMsgDAOImpl tMsgDAOImpl;

	/**
	 * 发送验证码
	 */
	public SendSmsResp getCaptcha(String userid, String phone) throws Exception {
		SendSmsResp sendSmsResp = new SendSmsResp();

		// 校验是否满足发送条件
		verifySendSMS(userid);
		String captcha = generateCaptcha();
		sendSmsResp.setCaptcha(captcha);
		//更新数据库
		saveTmsg(userid, captcha, phone);
		sendCaptchaFromService(phone, captcha);
		return sendSmsResp;
	}

	/**
	 * 验证该用户当天发送的短信数是否超过限制
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	private boolean verifySendSMS(String userid) throws Exception {
		List<TMsg> tMsgs = tMsgDAOImpl.getTMsgByUserIdAndTime(userid);
		if (tMsgs == null || tMsgs.size() == 0 || tMsgs.size() < Config.maxSMS) {
			if (tMsgs.size() > 0) {
				TMsg tMsg = tMsgs.get(0);
				Date date = new Date();
				if ((date.getTime() - tMsg.getUpdatetime().getTime()) < Config.subSMStime&&tMsg.getStatus().equals(CommonDefs.SMSVERFITYCODE)) {
					logger.debug("userid : " + userid + " is on the subtime ");
					throw new SalesLogicalException(
							ReturnCode.RET_LOGICAL_SMS_SUBTIME, "userid : "
									+ userid + " is on the subtime ");
				}
				return true;
			} else {
				return true;
			}
		} else {
			logger.debug("userid : " + userid + " num " + tMsgs.size()
					+ "is over maxSms ");
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_SMS_OVER,
					"userid " + userid + " SMS is over ");
		}
	}

	public String sendCaptchaFromService(String phone, String captcha)
			throws Exception {
		HttpPost httpPost = new HttpPost(CommonDefs.SMS_URL);
		Gson gson = new Gson();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(CommonDefs.SMSACCOUNT, tConfigDAOImpl
				.findById(CommonDefs.SMSACCOUNTCONFIG).getValue()));
		params.add(new BasicNameValuePair(CommonDefs.SMSPASSWORLD,
				tConfigDAOImpl.findById(CommonDefs.SMSPASSWORLDCONFIG)
						.getValue()));
		params.add(new BasicNameValuePair(CommonDefs.SMSMOBILE, phone));
		logger.debug(" send phone sms : " + phone);
		String content = tConfigDAOImpl.findById(CommonDefs.SMSCONTENTCONFIG)
				.getValue();

		logger.debug(" send phone " + phone + "content : " + content.toString());

		// 构建发送文档
		SMSContentModel smsContentModel = gson.fromJson(content,
				SMSContentModel.class);
		smsContentModel.setCaptcha(captcha);

		params.add(new BasicNameValuePair(CommonDefs.SMSCONTENT,
				smsContentModel.toString()));
		String response = HttpUtil.getResponse(httpPost);

		logger.debug("sms response : " + response);

		SMSResponseModel smsResponseModel = gson.fromJson(response,
				SMSResponseModel.class);
		if (!smsResponseModel.getCode().equals(CommonDefs.SUCCESSCODE)) {
			logger.error("sms error code : " + smsResponseModel.getCode());
			throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
					"sms error code : " + smsResponseModel.getCode());
		}
		return "success";
	}
	
	private void saveTmsg(String userid, String captcha,String phone){
		TMsg tMsg = new TMsg();
		tMsg.setUserid(Integer.parseInt(userid));
		tMsg.setStatus(CommonDefs.SMSNOVERFITYCODE);
		tMsg.setPhone(phone);
		tMsg.setCode(captcha);
		Timestamp updatetime = new Timestamp(new Date().getTime());
		tMsg.setUpdatetime(updatetime);
		tMsgDAOImpl.save(tMsg);
	}
	
	public VerifySmsResp verfityCaptcha(String userid,String captcha) throws Exception{
		List<TMsg> tMsgs = tMsgDAOImpl.getTMsgByUserIdAndTime(userid);
		if(tMsgs==null||tMsgs.size()==0){
			throw new SalesInternalException(ReturnCode.RET_LOGICAL_SMS_NOSENDSMS,"no tmsg has find userid "+userid);
		}else{
			TMsg tMsg = tMsgs.get(0);
			if(tMsg.getStatus().equals(CommonDefs.SMSVERFITYCODE)){
				throw new SalesInternalException(ReturnCode.RET_LOGICAL_SMS_HASBEENVERFITY, "sms has been verfity userid : "+userid);
			}else{
				Date date = new Date();
				if((date.getTime() - tMsg.getUpdatetime().getTime()) < Config.subSMStime){
					if(captcha.equals(tMsg.getCode())){
						tMsg.setStatus(CommonDefs.SMSVERFITYCODE);
						tMsgDAOImpl.attachClean(tMsg);
						return new VerifySmsResp();
					}else{
						throw new SalesInternalException(ReturnCode.RET_LOGICAL_SMS_VERFITYERROR, "sms verfityError userid "+userid);
					}
				}else{
					throw new SalesInternalException(ReturnCode.RET_LOGICAL_SMS_OUTOFTIME, "sms has out of time userid : "+userid);
				}
			}
		}
	}

	private String generateCaptcha() {

		StringBuilder captcha = new StringBuilder();

		// 产生4位随机数
		for (int i = 0; i < 4; i++) {
			captcha.append(Math.random() * 10);
		}

		return captcha.toString();
	}

}
