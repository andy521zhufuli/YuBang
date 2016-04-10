package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.android.yubang.R;


/**
 * UploadedInfosCheckActivity: 上传车辆信息之后的显示正在审核界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */

public class UploadedInfosCheckActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private static final String TAG = UploadedInfosCheckActivity.class.getName();

    private Button info_check_sure_btn;
    private Button info_check_relogin_btn;
    private ImageView img_back;
    private TextView  check_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_uploaded_infos_check);

        mContext = this;
        findViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            String status = bundle.getString("status");
            if (status.equals("0"))
            {
                check_info.setText("您正在审核,请点击重新登陆,刷新状态");
            }
            else if (status.equals("1"))
            {
                check_info.setText("您已经通过审核");
            }
            else if (status.equals("2"))
            {
                check_info.setText("审核不通过  联系客服");
            }
            else if (status.equals("3"))
            {
                check_info.setText("已经忽略, 请联系客服!");
            }
            else if (status.equals("4"))
            {
                check_info.setText("已经屏蔽, 请联系客服");
            }
            else if (status.equals("5"))
            {
                check_info.setText("为添加相片");
            }
        }


    }


    private void findViews()
    {
        info_check_sure_btn = (Button) findViewById(R.id.info_check_sure_btn);
        img_back            = (ImageView) findViewById(R.id.img_back);
        info_check_relogin_btn = (Button) findViewById(R.id.info_check_relogin_btn);
        check_info          = (TextView) findViewById(R.id.check_info);


        info_check_sure_btn.setOnClickListener(this);
        img_back.setOnClickListener(this);
        info_check_relogin_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId())
        {
            case R.id.info_check_sure_btn:
                //到商城页面去

                intent.setClass(mContext, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("otherActivity","check");
                intent.putExtras(bundle);
                startActivity(intent);

//                intent.setClass(mContext, ShoppingmallNewActivity.class);
//                startActivity(intent);

                break;
            case R.id.img_back:
                //TODO  这里不可返回  返回就要重新提交啊
                finish();
                break;
            case R.id.info_check_relogin_btn:

                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
