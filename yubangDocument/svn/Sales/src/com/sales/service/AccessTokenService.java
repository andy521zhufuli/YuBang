package com.sales.service;

import java.awt.Image;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.dao.impl.TAccesstokenDAOImpl;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TAccesstoken;


import java.sql.Date;
import java.sql.Timestamp;

@Service
@Transactional
public class AccessTokenService {

	final private static JLogger logger = LoggerUtils
			.getLogger(AccessTokenService.class);
		
	@Autowired
	private TAccesstokenDAOImpl tAccesstokenDAO;
	
	private TAccesstoken tAccesstoken = null;
	

	/**
	 * 鉴权， 检查用户是否登录
	 * */
	public  void authAccessToken(int userid, String accessToken) throws Exception
	{
		tAccesstoken = tAccesstokenDAO.findById(userid);
		// 校验用户是否存在
		if (null == tAccesstoken)
		{
			logger.debug("user is not existed for userid:" + userid);
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_NOT_LOGINED, "user is not existed for userid:" + userid );
		}
		try
		{
			verifyAccessToken(userid, accessToken);
		}
		finally
		{
			int triggers = tAccesstoken.getTriggers();
			tAccesstoken.setTriggers(triggers + 1);
			tAccesstokenDAO.save(tAccesstoken);
		}
	}
	
	/**
	 * 校验用户令牌
	 * 
	 * */
	private void  verifyAccessToken(int userid, String accessToken) throws SalesLogicalException
	{
		String localAccessToken = tAccesstoken.getAccesstoken();
		// 校验令牌是否存在
		if (null == localAccessToken)
		{
			logger.debug("accessToken is not existed for userid:" + userid);
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_NOT_LOGINED, "accessToken is not existed for userid:" + userid);
		}
		
		// 校验令牌是否与库里的令牌相同
		if (!localAccessToken.equals(accessToken))
		{
			logger.debug("accessToken is not illegal for userid:" + userid);
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_LOGINED_EXPIRED, "accessToken is not illegal for userid:" + userid);
		}
		
		// 校验令牌是否在有效期内
		Timestamp timestamp = tAccesstoken.getUpdatetime();
		long accesstokenMillis = timestamp.getTime();
		long currentMills = System.currentTimeMillis();
		
		long delta = ( currentMills -  accesstokenMillis ) / 1000000;     
		if (  delta > 1000)
		{
			logger.debug("accessToken is expired for userid:" + userid );
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_LOGINED_EXPIRED, "accessToken is expired for userid:" + userid);
		}	
	}
	
	public void loginOut(int userid)
	{
		// TODO 清理用户的令牌
	}
	
}
