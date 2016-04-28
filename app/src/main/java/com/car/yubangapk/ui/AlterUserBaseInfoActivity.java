package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.toastMgr;

public class AlterUserBaseInfoActivity extends BaseActivity implements View.OnClickListener{


    static final String TAG = AlterUserBaseInfoActivity.class.getSimpleName();
    Context mContext;

    ImageView           img_back;
    TextView            title;
    TextView            alter_base_info_tv;
    EditText            alter_base_info_edittext;
    Button              alter_base_info_btn_alter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alter_user_base_info);

        mContext = this;

        findViews();

        intiVews();

    }

    /**
     * 根据上个界面传来的参数, 设置界面应该显示的内容
     */
    private void intiVews() {
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("TYPE");
        if ("username".equals(type))
        {
            title.setText("修改用户名");
            alter_base_info_tv.setText("用户名: ");
            alter_base_info_edittext.setHint("请输入新用户名");
        }
        else if ("phonenum".equals(type))
        {
            title.setText("修改手机号码");
            alter_base_info_tv.setText("手机号码: ");
            alter_base_info_edittext.setHint("请输入新手机号码");
        }
        else if ("car".equals(type))
        {
            title.setText("修改车型");
            alter_base_info_tv.setText("车型: ");
            alter_base_info_edittext.setHint("请输入新车型");
        }
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
        alter_base_info_tv = (TextView) findViewById(R.id.alter_base_info_tv);
        alter_base_info_edittext = (EditText) findViewById(R.id.alter_base_info_edittext);
        alter_base_info_btn_alter = (Button) findViewById(R.id.alter_base_info_btn_alter);


        img_back.setOnClickListener(this);
        alter_base_info_btn_alter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            case R.id.alter_base_info_btn_alter:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                String altered = alter_base_info_edittext.getText().toString();
                if (altered.equals(""))
                {
                    toastMgr.builder.display("您没有输入, 无法保存", 1);
                    return;
                }
                bundle.putString("message", altered);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }

    }
}
