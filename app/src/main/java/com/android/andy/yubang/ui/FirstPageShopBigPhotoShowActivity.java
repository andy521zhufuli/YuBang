package com.android.andy.yubang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;

/**
 * FirstPageShopBigPhotoShowActivity: 循环展示门店图片
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-01
 */
public class FirstPageShopBigPhotoShowActivity extends BaseActivity {

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_page_shop_big_photo_show);
        
        findViews();
        
    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        /**
         * 监听器
         */
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
