package com.car.yubangapk.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.andy.android.yubang.R;

public class MyRecommendedPartnerFirstFragmentActivity extends AppCompatActivity {

    ListView my_recommended_partner_first_fragment_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommended_partner_first_fragment);

        my_recommended_partner_first_fragment_listview = (ListView) findViewById(R.id.my_recommended_partner_first_fragment_listview);




    }





}
