package com.car.yubangapk.ui;

import android.content.Intent;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;
import com.car.yubangapk.ui.myordersfragment.MyOrdersActivity;
import com.car.yubangapk.utils.toastMgr;

public class NofityClick2ShowActivity extends BaseActivity {


    ImageView   img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nofity_click2_show);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gotoMyActiviy();
            }
        });


        Bundle bundle = getIntent().getExtras();
        String orderId = bundle.getString("orderId");
        toastMgr.builder.display("orderid = " + orderId, 1);

    }
    private void gotoMyActiviy()
    {
        Intent intent = new Intent();
        intent.setClass(NofityClick2ShowActivity.this, MyOrdersActivity.class);
        startActivity(intent);
        this.finish();
    }

}
