package com.sales.service;

import java.awt.Image;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.logging.resources.logging;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.HttpUtil;
import com.sales.common.until.ImgUtils;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.MD5Util;
import com.sales.dao.impl.TAccesstokenDAOImpl;
import com.sales.dao.impl.TAddressDAOImpl;
import com.sales.dao.impl.TConfigDAOImpl;
import com.sales.dao.impl.TUsrDAOImpl;
import com.sales.dao.impl.VUsrAccountinfoDAOImpl;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TAccesstoken;
import com.sales.model.TAddress;
import com.sales.model.TUsr;
import com.sales.model.VUsrAccountinfoId;
import com.sales.service.qq.QQAccessTokenModel;
import com.sales.service.qq.QQ_User_Info;
import com.sales.service.qq.QQopenIdModel;
import com.sales.service.weixin.WeiXinAccessModel;
import com.sales.service.weixin.WeiXinUserInfoModel;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
import com.sales.vo.GetAddressListReq;
import com.sales.vo.GetAddressListResp;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.GetCashInfoResp;
import com.sales.vo.PostLoginReq;
import com.sales.vo.PostLoginResp;
import com.sales.vo.UploadImgReq;
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.FriendInfo;
import com.sales.web.login.ExitLoginAction;
import com.sun.imageio.plugins.common.ImageUtil;

import java.sql.Date;
import java.sql.Timestamp;

@Service
@Transactional
public class UserService {

	final private static JLogger logger = LoggerUtils
			.getLogger(UserService.class);
	
	@Autowired
	private TUsrDAOImpl tUsrDAO;
	
	@Autowired
	private TConfigDAOImpl tConfigDAO;
	
	@Autowired
	private VUsrAccountinfoDAOImpl vUsrAccountInfoDAO;
	
	@Autowired
	private TAddressDAOImpl tAddressDAO;
	
	@Autowired
	private TAccesstokenDAOImpl tAccesstokenDAO;
	
	public PostLoginResp loginQQ(PostLoginReq loginReq) throws Exception{
		PostLoginResp loginResp = new PostLoginResp();
		
			QQAccessTokenModel accessTokenModel = getQQAccessToken(loginReq);
			QQopenIdModel qopenIdModel = getQQopenID(accessTokenModel.getAccess_token());
			QQ_User_Info qq_User_Info = getQQUseInfo(accessTokenModel.getAccess_token(),qopenIdModel.getOpenid());
			return login(qopenIdModel.getOpenid(), accessTokenModel.getAccess_token(), CommonDefs.UST_TYPE_QQ, qq_User_Info.getFigureurl(),qq_User_Info.getNickname());
	}
	
	private QQAccessTokenModel getQQAccessToken(PostLoginReq  loginReq) throws Exception{
		String url = CommonDefs.QQ_URL+"/oauth2.0/token?grant_type=authorization_code";
		url += "&client_id="+tConfigDAO.findById(CommonDefs.QQAPPID).getValue();
		url+="&client_secret="+tConfigDAO.findById(CommonDefs.QQAPPKEY).getValue();
		url+="&code=="+loginReq.getCode();
		url+="&redirect_uri=="+tConfigDAO.findById(CommonDefs.REDIRECT_URI).getValue();
		String response = HttpUtil.getResponse(url);
		//返回错误
		if(response.contains("error")){
			logger.error(response);
			throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
					"qqAccessToken error : " + response);
		}else{
			String[] message = response.split("&");
			if(message.length!=2){
				throw new Exception("qq response is error");
			}else{
				QQAccessTokenModel qqAccessTokenModel = new QQAccessTokenModel();
				String[] accesss = message[0].split("=");
				if(accesss.length!=2){
					throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
							"qqAccessToken error : " + response);
				}else{
					qqAccessTokenModel.setAccess_token(accesss[1]);
				}
				String[] expires_ins = message[1].split("=");
				if(expires_ins.length!=2){
					throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
							"qqAccessToken error : " + response);
				}else{
					qqAccessTokenModel.setExpires_in(message[1]);
				}
				return qqAccessTokenModel;
			}
		}
	}
	
	public QQopenIdModel getQQopenID(String accesstoken) throws Exception{
		String url = CommonDefs.QQ_URL+"/oauth2.0/me?access_token=";
		url+=accesstoken;
		String response = HttpUtil.getResponse(url);
		String[] getPreStr = response.split("(");
		if(getPreStr.length<2){
			throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
					"qqopenID error : " + response);
		}else{
			String[] getJson = getPreStr[1].split(")");
			if(getJson.length<2){
				throw new SalesInternalException(ReturnCode.RET_SMS_ERROR,
						"qqopenID error : " + response);
			}else{
				Gson gson = new Gson();
				QQopenIdModel qopenIdModel = gson.fromJson(getJson[0],QQopenIdModel.class);
				return qopenIdModel;
			}
		}
	}
	
	public QQ_User_Info getQQUseInfo(String accesstoken , String openid) throws Exception{
		String url = CommonDefs.QQ_URL+"/user/get_user_info?";
		url+="access_token="+accesstoken;
		url+="&oauth_consumer_key="+tConfigDAO.findById(CommonDefs.QQAPPID).getValue();
		url+="&openid="+openid;
		String response = HttpUtil.getResponse(url);
		Gson gson = new Gson();
		QQ_User_Info qq_User_Info = gson.fromJson(response,QQ_User_Info.class);
		return qq_User_Info;
	}
	
	private PostLoginResp login(String openid,String accessToken , String type,String headimgurl , String nickname) throws Exception{
		PostLoginResp loginResp = new PostLoginResp();
		 TUsr useExample = new TUsr();
		    useExample.setUsername(openid);
		    useExample.setType(type);
		    List<TUsr> tUsrs = tUsrDAO.findByExample(useExample);
		    //不存在该用户则创建用户
		    if(tUsrs == null){
		    	TUsr tUsr = new TUsr();
		    	tUsr.setHeadimgurl(headimgurl);
		    	tUsr.setNickname(nickname);
		    	tUsr.setUsername(openid);
		    	tUsr.setAccesstoken(MD5Util.MD5(accessToken));
		    	//单独的一个事务
		    	addUser(tUsr);
		    }
		    tUsrs = tUsrDAO.findByExample(useExample);
		    loginResp.setReturncode(ReturnCode.RET_ERROR);
		    if(tUsrs==null){
		    	throw new SalesLogicalException(ReturnCode.RET_LOGICAL_LOGIN_USERISNULL, "userid is null ");
		    }else if(tUsrs.size()>1){
		    	throw new SalesLogicalException(ReturnCode.RET_LOGICAL_LOGIN_USERISTWO, "has two user has some userid ");
		    }else if(tUsrs.size() ==0){
		    	throw new SalesLogicalException(ReturnCode.RET_LOGICAL_LOGIN_USERISNOTEXIT, "user is not exit");
		    }else{
		    	TUsr tUsr = tUsrs.get(0);
		    	tUsr.setAccesstoken(MD5Util.MD5(accessToken));
		    	tUsrDAO.attachDirty(tUsr);
		    	loginResp.setUserid(tUsrs.get(0).getUserid()+"");
		    	loginResp.setAuthorizedtoken(tUsrs.get(0).getAccesstoken());
		    	loginResp.setReturncode(ReturnCode.RET_SUCCESS);
		    }
			return loginResp;
	}
	
	public PostLoginResp loginWeiXin(PostLoginReq  loginReq) throws Exception{
		WeiXinAccessModel weiXinAccessModel = getAsscessTokenFromWeiXin(loginReq.getCode());
	    WeiXinUserInfoModel weiXinUserInfoModel = getUseInfoFromWeiXin(weiXinAccessModel);
		return login(weiXinAccessModel.getOpenid(),weiXinAccessModel.getAccess_token(),CommonDefs.UST_TYPE_WEIXIN,weiXinUserInfoModel.getHeadimgurl(),weiXinUserInfoModel.getNickname());
	}
	
	@Transactional
	private void addUser(TUsr tUsr){
		tUsrDAO.save(tUsr);
	}
	
	private WeiXinAccessModel getAsscessTokenFromWeiXin(String code) throws Exception{
		String url = CommonDefs.WEIXIN_URL+"/sns/oauth2/access_token" + "?";
		url += "appid = "+tConfigDAO.findById(CommonDefs.APPID).getValue();
		url += "&secre="+tConfigDAO.findById(CommonDefs.SECRET).getValue();
		url += "&code="+code;
		url += "&grant_type="+CommonDefs.AUTHORIZATION_CODE;
		String response = HttpUtil.getResponse(url);
		Gson gson = new Gson();	
		WeiXinAccessModel weiXinAccessModel = gson.fromJson(response,WeiXinAccessModel.class);
		return weiXinAccessModel;
	}
	
	private WeiXinAccessModel refreshAsscessTokenFromWeiXin(WeiXinAccessModel weiXinAccessModel){
		
		return weiXinAccessModel;
	}
	
	private WeiXinUserInfoModel getUseInfoFromWeiXin(WeiXinAccessModel weiXinAccessModel) throws Exception{
		String url = CommonDefs.WEIXIN_URL+"/sns/userinfo" + "?";
		url+="access_token="+weiXinAccessModel.getAccess_token();
		url+="openid="+weiXinAccessModel.getOpenid();
		String response = HttpUtil.getResponse(url);
		Gson gson = new Gson();	
		WeiXinUserInfoModel weiXinUserInfoModel = gson.fromJson(response, WeiXinUserInfoModel.class);
		return weiXinUserInfoModel;
	}
	 
	public String uploadImage(UploadImgReq uploadImgReq){
		try{
			File upl = uploadImgReq.getImage_file();
			String[] strings = upl.getName().split(".");
			File file=new File(ImgUtils.getImgPath(ImgUtils.IMG_TYPE_HEAD_IMG, strings[1]));
			FileUtils.copyFile(uploadImgReq.getImage_file(),file);
			TUsr tUsr =tUsrDAO.findById(Integer.parseInt(uploadImgReq.getUserid()));
			tUsr.setHeadimgurl(file.getPath());
			tUsrDAO.attachClean(tUsr);
			return file.getPath();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			return "";
		}
	}
	
	public void loginOut(String userid){
		TUsr tUsr = tUsrDAO.findById(Integer.parseInt(userid));
		tUsr.setAccesstoken("");
		tUsrDAO.attachClean(tUsr);
	}
	
	public GetAccountInfoResp getAccountInfo(GetAccountInfoReq req)
	{
		//VUsrAccountinfoId accountInfo = vUsrAccountInfoDAO.findByUsrid(Integer.valueOf(req.getUserid()));
		List<VUsrAccountinfoId> accountInfoList = vUsrAccountInfoDAO.findByProperty("", Integer.valueOf(req.getUserid()));
		if (null == accountInfoList || accountInfoList.size() <= 0)
		{
		// TODO 异常处理流，判断用户是否为空
		}
		
		VUsrAccountinfoId accountInfo =  accountInfoList.get(0);		
		GetAccountInfoResp resp = new GetAccountInfoResp();
		resp.setUserid(String.valueOf(accountInfo.getUserid()));
		resp.setNickname(accountInfo.getNickname());
		resp.setHeadimgurl(accountInfo.getHeadimgurl());
		resp.setFriendnum(accountInfo.getFriendnum());
		resp.setOrdernum(accountInfo.getOrdernum());
		resp.setCashbalance(accountInfo.getCashbalance());
		resp.setTotalincome(accountInfo.getTotalincome().longValue());
		resp.setOrdervalueofmonth(accountInfo.getOrdervalueofmonth().longValue());
	
		return resp;
	}
	
	public List<AddressInfo> getAddressList(int usrid)
	{
		List<TAddress>  addressList = tAddressDAO.getAddressList(usrid);
		
		GetAddressListResp resp = new GetAddressListResp();
		List<AddressInfo> list = new ArrayList<AddressInfo>();
		if (addressList != null && addressList.size() >0)
		{
			for(TAddress address : addressList)
			{
				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setAddress(address.getAddress());
				addressInfo.setAddressname(address.getName());
				addressInfo.setAddressphone(address.getPhone());
				addressInfo.setAddressid(String.valueOf(address.getId()));
				addressInfo.setSelected(address.getSelected());
				list.add(addressInfo);				
			}
		}
		resp.setAddresslist(list);
		
		return list;
	}
	
	public List<FriendInfo> getFriendList(int userid)
	{
		List<FriendInfo> friendInfoList = new ArrayList<FriendInfo>();
		
		List<TUsr>  friendList = tUsrDAO.findByPuserid(userid);
		if (friendList != null && friendList.size() > 0)
		{
			for (TUsr usr : friendList)
			{
				FriendInfo friend = new FriendInfo();
				friend.setFuserid(String.valueOf(usr.getUserid()));
				friend.setUsername(usr.getUsername());
				friend.setNickname(usr.getNickname());
				friend.setHeadimgurl(usr.getHeadimgurl());
				friend.setUsertype(usr.getType());
				friendInfoList.add(friend);				
			}
		}
		
		return friendInfoList;
	}

	/**
	 * 添加新的收件信息
	 * */
	public void addAddress(String userid, AddressInfo info) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 修改收件信息
	 * */
	public void modifyAddress(String userid, AddressInfo info) {
		// TODO Auto-generated method stub
		
	}
	
}
