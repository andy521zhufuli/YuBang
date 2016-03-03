package com.android.andy.yubang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.android.andy.yubang.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * DiscoverRecommendPartnerCommentActivity: 推荐和国人 评论
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-02
 */
public class DiscoverRecommendPartnerCommentActivity extends BaseActivity implements View.OnClickListener {


    private ImageView img_back;

    private ImageView comment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover_recommend_partner_comment);



        findViews();
    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        comment1 = (ImageView) findViewById(R.id.comment1);
        /**
         * 监听器
         */
        img_back.setOnClickListener(this);
        comment1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_back:
                finish();
                break;

            case R.id.comment1:
                toastMgr.builder.display("您是第28位点赞者,随机送您红包0.88元", 0);
                break;
        }
    }
}
