package com.android.andy.yubang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
