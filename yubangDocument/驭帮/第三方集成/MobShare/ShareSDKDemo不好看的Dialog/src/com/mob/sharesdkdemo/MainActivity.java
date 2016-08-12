package com.mob.sharesdkdemo;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity implements OnClickListener,
		PlatformActionListener {

	private Button shareButton;
	ShareDialog shareDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		ShareSDK.initSDK(this);
	}

	private void initView() {
		shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_button:
			shareDialog = new ShareDialog(this);
			shareDialog.setCancelButtonOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					shareDialog.dismiss();

				}
			});
			shareDialog.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
					if (item.get("ItemText").equals("QQ")) {
						ShareParams sp = new ShareParams();
						sp.setTitle("测试分享的标题");
						sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
						sp.setText("测试分享的文本");
						sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");//分享网络图片

						Platform qq = ShareSDK.getPlatform(QQ.NAME);
						qq.setPlatformActionListener(MainActivity.this); // 设置分享事件回调
						// 执行分享
						qq.share(sp);
					} else {
						Toast.makeText(MainActivity.this,"您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
					}
					shareDialog.dismiss();

				}
			});

			break;

		default:
			break;
		}

	}

	@Override
	public void onCancel(Platform arg0, int arg1) {//回调的地方是子线程，进行UI操作要用handle处理
		handler.sendEmptyMessage(2);

	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {//回调的地方是子线程，进行UI操作要用handle处理
		if (arg0.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
			handler.sendEmptyMessage(1);
		}

	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {//回调的地方是子线程，进行UI操作要用handle处理
		arg2.printStackTrace();
		Message msg = new Message();
		msg.what = 3;
		msg.obj = arg2.getMessage();
		handler.sendMessage(msg);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "分享失败" + msg.obj, Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}

	};

}
