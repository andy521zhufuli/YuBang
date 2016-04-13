package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;
import com.car.yubangapk.json.Base;

/**
 * ClientServiceActivity:客服界面
 *
 * @author andy
 * @version 1.0
 * @created 2016-02-22
 */
public class ClientServiceActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ClientServiceActivity.class.getName();

    private Context mContext;

    private ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_client_service);

        mContext = this;

        findViews();

    }

    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
