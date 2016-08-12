package com.tec.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.tec.android.utils.toastMgr;

import com.tec.android.R;

public class ShoppingNoGoodsActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;

    private ImageView title_back;//顶部返回
    private TextView shoppingcar_goto_fisrt_page;//去首页



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_no_goods);
        mContext = this;
        findViews();
    }

    /**
     * 去布局初始化对象
     */
    private void findViews() {
        title_back = (ImageView) findViewById(R.id.title_back);
        shoppingcar_goto_fisrt_page = (TextView) findViewById(R.id.shoppingcar_goto_fisrt_page);

        //监听器
        title_back.setOnClickListener(this);
        shoppingcar_goto_fisrt_page.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title_back:
                toastMgr.builder.display("返回", 0);
                finish();
                break;
            case R.id.shoppingcar_goto_fisrt_page:
                //要判断一下是从哪个页面进来的吧
                //如果不要 那就直接去首页  首页怎么去
                toastMgr.builder.display("去首页", 0);
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("otherActivity","SHOPPINGCAR_NO_GOODS");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            default:

                break;
        }
    }
}
