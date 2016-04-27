package com.car.yubangapk.ui.myrecommendpartner;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;

/**
 * MyRecommendedPartnerActivity: 我推荐的合伙人
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyRecommendedPartnerActivity extends FragmentActivity {

    private Context mContext;

    private ImageView       img_back;//返回

    private RelativeLayout  my_recommended_partner_layout_1;//已审核
    private RelativeLayout  my_recommended_partner_layout_2;//未审核
    private RelativeLayout  my_recommended_partner_layout_3;//以入伙

    private TextView        tv1;
    private TextView        tv2;
    private TextView        tv3;

    private View            indicator1;
    private View            indicator2;
    private View            indicator3;

    private ListView        my_recommended_partner_listview;//列表



    private FrameLayout     my_recommended_partner_framlayout;//framlayout
    private Fragment        firstFragment;
    private Fragment        secondFragment;
    private Fragment        threeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_recommended_partner);



        mContext =this;

        findViews();


        select(0);


    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        my_recommended_partner_layout_1 = (RelativeLayout) findViewById(R.id.my_recommended_partner_layout_1);//已审核
        my_recommended_partner_layout_2 = (RelativeLayout) findViewById(R.id.my_recommended_partner_layout_2);//未审核
        my_recommended_partner_layout_3 = (RelativeLayout) findViewById(R.id.my_recommended_partner_layout_3);//以入伙
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);


        my_recommended_partner_framlayout = (FrameLayout) findViewById(R.id.my_recommended_partner_framlayout);


        /**
         * 注册监听
         */

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /**
         * 已审核
         */
        my_recommended_partner_layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goods_orders();
                indicator1.setVisibility(View.VISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                tv1.setTextColor(Color.parseColor("#ff0000"));
                tv2.setTextColor(Color.parseColor("#4f4f4f"));
                tv3.setTextColor(Color.parseColor("#4f4f4f"));
                select(0);
            }
        });

        /**
         * 未审核
         */
        my_recommended_partner_layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logistics_orders();
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.VISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                tv1.setTextColor(Color.parseColor("#4f4f4f"));
                tv2.setTextColor(Color.parseColor("#ff0000"));
                tv3.setTextColor(Color.parseColor("#4f4f4f"));
                select(1);
            }
        });

        /**
         * 以入伙
         */
        my_recommended_partner_layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //canceled_orders();
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.VISIBLE);
                tv1.setTextColor(Color.parseColor("#4f4f4f"));
                tv2.setTextColor(Color.parseColor("#4f4f4f"));
                tv3.setTextColor(Color.parseColor("#ff0000"));
                select(2);
            }
        });
    }


    /**实现切换不同的Fragment
     * @param i 点击的第几个按钮
     */
    private void select(int i)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i)
        {
            case 0:
                if (firstFragment == null)
                {
                    firstFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(ALL_PARTNERS);
                    transaction.add(R.id.my_recommended_partner_framlayout, firstFragment);
                } else
                {
                    transaction.show(firstFragment);
                }
                break;
            case 1:
                if (secondFragment == null)
                {
                    secondFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(PENDING_APPROVAL);
                    transaction.add(R.id.my_recommended_partner_framlayout, secondFragment);
                } else
                {
                    transaction.show(secondFragment);
                }
                break;
            case 2:
                if (threeFragment == null)
                {
                    threeFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(NOT_APPROVAL);
                    transaction.add(R.id.my_recommended_partner_framlayout, threeFragment);
                } else
                {
                    transaction.show(threeFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 用于每一显示不同的Fragment时候隐藏之前的所有可能显示的Fragment
     * @param transaction
     *          事物
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        if (firstFragment != null)
        {
            transaction.hide(firstFragment);
        }
        if (secondFragment != null)
        {
            transaction.hide(secondFragment);
        }
        if (threeFragment != null)
        {
            transaction.hide(threeFragment);
        }
    }

    public static final String ALL_PARTNERS = "全部";
    public static final String PENDING_APPROVAL = "待审核";
    public static final String NOT_APPROVAL = "未审核";

}
