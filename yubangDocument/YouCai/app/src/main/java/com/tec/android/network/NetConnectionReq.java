package com.tec.android.network;

import android.os.AsyncTask;

import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接网络 使用回调函数
 *
 * @author andyzhu
 *
 */
public class NetConnectionReq
{

	private static final String TAG = "NetConnectionReq";

	// TODO 要注意  这里面拿到的到底是不是html
	// 还有一个就是html里面的css  js  怎么办
	public NetConnectionReq(final String url, final SuccessCallback successCallback,
							final FailCallback failCallback, final String json
	)
	{

		// 轻量级异步线程 很好用
		new AsyncTask<Void, Void, String>()
		{

			@Override
			protected String doInBackground(Void... arg0) {
				HttpPost request = new HttpPost(url);
				HttpResponse response = null;
				StringBuffer paramsStr = new StringBuffer();
				String result = null;
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// get httpclient对象 是DefaultHttpClient 默认的
				DefaultHttpClient client = getHttpClient();
				try {
					StringEntity stringEntity = new StringEntity(json.toString(), HTTP.UTF_8);

					request.setEntity(stringEntity);
					//设置cookie  不需要  看情况吧
					//client.setCookieStore(cookieStore);
					response = client.execute(request);

					L.i("Request url:" + url);
					L.i("Request data:" + paramsStr);

					if (response.getStatusLine().getStatusCode() == 200) {
						result = EntityUtils.toString(response.getEntity());

						L.i(TAG, "注册,连接网络,200代码,");
					}

					L.i("Result:" + result);
					if (result == null) {
						return null;
					}
					return result.toString();

				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}catch (ConnectTimeoutException e)
				{
					return null;
				} catch (IOException e) {
					System.out.println("--" + e.toString());
					e.printStackTrace();
					return null;
				}

				// return null;

			}

			/**
			 * 在执行完doInBackground之后就会执行这个onPostExecute函数 传进来的参数就是doInBackground的返回值 result
			 */
			@Override
			protected void onPostExecute(String result)
			{

				if (result != null)
				{
					if (successCallback != null)
					{
						// 这个回调函数就是负责通知调用者,就是外界,执行的结果
						// 这就是这几成回调函数的初衷
						successCallback.onSuccess(result);
					}
				}
				else
				{
					if (failCallback != null)
					{
						failCallback.onFail(result);
					}
				}

				// super.onPostExecute(result);
			}
		}.execute();

	}

	// 初始化HttpClient，并设置超时
	private DefaultHttpClient getHttpClient()
	{
		// TODO Auto-generated method stub
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Configs.timeout);
		HttpConnectionParams.setSoTimeout(httpParams, Configs.SO_TIMEOUT);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);

		// 判断是否保存了 cookie 如果有 就设置cookie 如果没有就不设置cookie

		return client;
	}

	// 回调函数
	public interface SuccessCallback
	{
		void onSuccess(String result);
	}

	public interface FailCallback
	{
		void onFail(String error);
	}
}
