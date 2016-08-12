package com.sales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.logging.resources.logging;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.google.gson.Gson;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.dao.impl.TConfigDAOImpl;
import com.sales.model.TConfig;
import com.sales.vo.ShareReq;
import com.sales.vo.ShareResp;
import com.sales.web.user.CashOutAction;

@Service
@Transactional
public class ConfigService {

	final private static JLogger logger = LoggerUtils
			.getLogger(ConfigService.class);
	
	@Autowired
	private TConfigDAOImpl tConfigDAO;
	
	/**
	 * ��ȡϵͳ����
	 * @param key ���ùؼ���
	 * @return ���õ�����
	 */
	public String getSystemConfig(String key){
		TConfig config = tConfigDAO.findById(key);
		if(config == null){
			logger.error("config is null ");
			return "";
		}
		return config.getValue();
	}
	
	public ShareResp getShareConfig(String userid , String accessToken){
		String config  =  getSystemConfig(CommonDefs.SHARECONFIG);
		Gson gson = new Gson();
		ShareResp shareResp = gson.fromJson(config,ShareResp.class);
		shareResp.setWebpageUrl(shareResp.getWebpageUrl()+"?userid="+userid);
		shareResp.setUserid(userid);
		shareResp.setAccessToken(accessToken);
		shareResp.setReturncode(ReturnCode.RET_SUCCESS);
		return shareResp;
	}

	public TConfigDAOImpl gettConfigDAO() {
		return tConfigDAO;
	}

	public void settConfigDAO(TConfigDAOImpl tConfigDAO) {
		this.tConfigDAO = tConfigDAO;
	}
	
	
}
