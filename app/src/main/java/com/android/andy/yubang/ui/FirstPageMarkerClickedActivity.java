package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.andy.yubang.utils.L;
import com.android.andy.yubang.utils.toastMgr;
import com.android.andy.yubang.view.ActionSheetDialog;
import com.android.andy.yubang.view.AlertDialog;
import com.andy.android.yubang.R;
/**
 * FirstPageMarkerClickedActivity: 首页marker点击后  弹出来的对话框
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class FirstPageMarkerClickedActivity extends BaseActivity implements View.OnClickListener {

    private Context      mContext;
    private LinearLayout bottom;
    private ImageView    shop_like;
    private TextView     clicked_shop_num;
    private ImageView    clicked_shop_photo;
    private TextView     clicked_shop_name;
    private TextView     clicked_shop_level;
    private TextView     clicked_shop_dan_num;
    private TextView     clicked_shop_call_phone;
    private ImageView    clicked_shop_close;
    private ListView     clicked_shop_listview;

    int width;
    int height;

    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件","保养维护", "电子电路","保养维护", "电子电路" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(true);//
        setContentView(R.layout.activity_first_page_marker_clicked);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mContext = this;
        WindowManager wm = getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        findViews();

        clicked_shop_listview.setAdapter(new ClickedShopAdapter());
    }

    private void findViews()
    {
        bottom = (LinearLayout) findViewById(R.id.bottom);

        shop_like = (ImageView) findViewById(R.id.shop_like);

        clicked_shop_num = (TextView) findViewById(R.id.clicked_shop_num);

        clicked_shop_photo = (ImageView) findViewById(R.id.clicked_shop_photo);

        clicked_shop_name = (TextView) findViewById(R.id.clicked_shop_name);

        clicked_shop_level = (TextView) findViewById(R.id.clicked_shop_level);

        clicked_shop_dan_num = (TextView) findViewById(R.id.clicked_shop_dan_num);

        clicked_shop_call_phone = (TextView) findViewById(R.id.clicked_shop_call_phone);

        clicked_shop_close = (ImageView) findViewById(R.id.clicked_shop_close);

        clicked_shop_listview = (ListView) findViewById(R.id.clicked_shop_listview);

        //
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) bottom.getLayoutParams();

        linearParams.height = height / 2;
        //

        shop_like.setOnClickListener(this);
        clicked_shop_call_phone.setOnClickListener(this);
        clicked_shop_close.setOnClickListener(this);
        clicked_shop_photo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId())
        {
            case R.id.shop_like:
                toastMgr.builder.display("已经关注", Toast.LENGTH_SHORT);
                break;
            case R.id.clicked_shop_call_phone:
                toastMgr.builder.display("打电话给店主吗", 0);
                //或者直接就调用打电话
//                new ActionSheetDialog(mContext)
//                        .builder()
//                        .setTitle("打电话给店主")
//                        .setCancelable(true)
//                        .setCanceledOnTouchOutside(true)
//                        .addSheetItem("拨打电话", ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//                                        toastMgr.builder.display("item" + which, Toast.LENGTH_SHORT);
//                                    }
//                                })
//                        .show();


                new AlertDialog(mContext).builder()
                        .setTitle("打电话给店主吗").setMsg("确定退出吗?").setCancelable(false)
                        .setPositiveButton("现在打", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setNegativeButton("考虑下", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
                break;
            case R.id.clicked_shop_close:
                finish();
                break;
            //第一个 带喜欢的  店铺点击
            case R.id.clicked_shop_photo:
                toastMgr.builder.display("已经关注", Toast.LENGTH_SHORT);
                intent.setClass(mContext, FirstPageShopShowActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    /*
 * 适配器的定义,要继承BaseAdapter
 */
    public class ClickedShopAdapter extends BaseAdapter {

        public ClickedShopAdapter() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_marker_shop_clicked, null);
            TextView species = (TextView) view.findViewById(R.id.item_clicked_shop_name);
            species.setText("中海维修");
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("FirstPageMarkerClickedActivity destory");
    }
}
