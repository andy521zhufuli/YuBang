package com.sales.common.until;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.service.UserService;

public class HttpUtil {
	
	final private static JLogger logger = LoggerUtils
			.getLogger(HttpUtil.class);
	
	public static String getResponse(String url) throws Exception{
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
		response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {

			return EntityUtils.toString(response.getEntity(), "utf-8");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
		return null;
	}
	
	public static String getResponse(HttpPost httpPost) throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
		response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {

			return EntityUtils.toString(response.getEntity(), "utf-8");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
		return null;
	}
	

}
