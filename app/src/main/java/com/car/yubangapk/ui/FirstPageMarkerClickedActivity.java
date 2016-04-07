package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.andy.android.yubang.R;

import java.util.List;

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

    List<Json2FirstPageShopBean> mJson2FirstPageShopBeans;//店铺信息
    String mShopBean;
    //店铺信息
    private String shopInfoId;
    private String shopInfoShopAddress;
    private String shopInfoPhoneNum;
    private String shopInfoShopPhoto;
    private String shopInfoPathCode;
    private String shopInfoShopName;
    private String shopInfoCompany;
    private double shopInfoShopLatitude;
    private double shopInfoShopLongitude;
    private String shopInfoStar;
    private int    shopInfoOrder;
    int width;
    int height;

    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件","保养维护", "电子电路","保养维护", "电子电路" };

    //{"total":0,"rows":[
    // {"shopLatitude":23.125221,
    // "id":"0513ba78-0ce8-4e00-b6c5-168d5544c4ef",
    // "shopAddress":"广东广州番禺",
    // "phoneNum":"13454678764",
    // "pathCode":"6",
    // "shopPhoto":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg",
    // "distance":7661.0,
    // "order":1,
    // "shopName":"强哥维修",
    // "company":"30fd0183-ea3a-11e5-81df-28d244001fe5",
    // "star":"5",
    // "shopLongitude":113.32771}]}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(true);//
        setContentView(R.layout.activity_first_page_marker_clicked);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int size = 0;
        String shopBean = getIntent().getStringExtra("shopBean");
        Json2FirstPageShop json2FirstPageShop1 = new Json2FirstPageShop(shopBean);
        List<Json2FirstPageShopBean> json2FirstPageShopBeans1 = json2FirstPageShop1.getFirstPageShop();
        mJson2FirstPageShopBeans = json2FirstPageShopBeans1;
        size = mJson2FirstPageShopBeans.size();

        mContext = this;
        WindowManager wm = getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        findViews(size);


        if (shopBean != null)
        {
            Json2FirstPageShop json2FirstPageShop = new Json2FirstPageShop(shopBean);
            List<Json2FirstPageShopBean> json2FirstPageShopBeans = json2FirstPageShop.getFirstPageShop();
            mShopBean = shopBean;

            mJson2FirstPageShopBeans = json2FirstPageShopBeans;

            size = mJson2FirstPageShopBeans.size();

            if (size == 1)
            {
                clicked_shop_listview.setVisibility(View.GONE);
                shopInfoId              = mJson2FirstPageShopBeans.get(0).getId();
                shopInfoShopAddress     = mJson2FirstPageShopBeans.get(0).getShopAddress();
                shopInfoPhoneNum        = mJson2FirstPageShopBeans.get(0).getPhoneNum();
                shopInfoShopPhoto       = mJson2FirstPageShopBeans.get(0).getShopPhoto();
                shopInfoPathCode        = mJson2FirstPageShopBeans.get(0).getPathCode();
                shopInfoShopName        = mJson2FirstPageShopBeans.get(0).getShopName();
                shopInfoCompany         = mJson2FirstPageShopBeans.get(0).getCompany();
                shopInfoShopLatitude    = mJson2FirstPageShopBeans.get(0).getShopLatitude();
                shopInfoShopLongitude   = mJson2FirstPageShopBeans.get(0).getShopLongitude();
                shopInfoStar            = mJson2FirstPageShopBeans.get(0).getStar();
                shopInfoOrder           = mJson2FirstPageShopBeans.get(0).getOrder();

                //设置给第一个


                //clicked_shop_photo
                String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + shopInfoPathCode + "&fileReq.fileName=" + shopInfoShopPhoto;
                L.d("marker shop photo url = " + url);
                ImageLoaderTools.getInstance(mContext).displayImage(url, clicked_shop_photo);
//                shop_like
                clicked_shop_num.setText(shopInfoOrder +"");
                clicked_shop_photo = null;
                clicked_shop_name.setText(shopInfoShopName);
                clicked_shop_level.setText(shopInfoStar);
                clicked_shop_dan_num.setText("160单");

            }
            else if (size > 1)
            {//这里需要改//TODO
                clicked_shop_listview.setVisibility(View.VISIBLE);
                for (int i = 0; i < size; i++)
                {
                    shopInfoId              = mJson2FirstPageShopBeans.get(i).getId();
                    shopInfoShopAddress     = mJson2FirstPageShopBeans.get(i).getShopAddress();
                    shopInfoPhoneNum        = mJson2FirstPageShopBeans.get(i).getPhoneNum();
                    shopInfoShopPhoto       = mJson2FirstPageShopBeans.get(i).getShopPhoto();
                    shopInfoPathCode        = mJson2FirstPageShopBeans.get(i).getPathCode();
                    shopInfoShopName        = mJson2FirstPageShopBeans.get(i).getShopName();
                    shopInfoCompany         = mJson2FirstPageShopBeans.get(i).getCompany();
                    shopInfoShopLatitude    = mJson2FirstPageShopBeans.get(i).getShopLatitude();
                    shopInfoShopLongitude   = mJson2FirstPageShopBeans.get(i).getShopLongitude();
                    shopInfoStar            = mJson2FirstPageShopBeans.get(i).getStar();
                    shopInfoOrder           = mJson2FirstPageShopBeans.get(i).getOrder();
                }
            }
            else
            {
                //根本就不可能
                toastMgr.builder.display("首页传的数据出错", 0);
            }



        }




        clicked_shop_listview.setAdapter(new ClickedShopAdapter());
    }

    private void findViews(int size)
    {
        bottom = (LinearLayout) findViewById(R.id.bottom);

        shop_like               = (ImageView) findViewById(R.id.shop_like);//关注

        clicked_shop_num        = (TextView) findViewById(R.id.clicked_shop_num);//店铺排序

        clicked_shop_photo      = (ImageView) findViewById(R.id.clicked_shop_photo);//店铺照片

        clicked_shop_name       = (TextView) findViewById(R.id.clicked_shop_name);//店铺名字

        clicked_shop_level      = (TextView) findViewById(R.id.clicked_shop_level);//店铺星际

        clicked_shop_dan_num    = (TextView) findViewById(R.id.clicked_shop_dan_num);//接单数

        clicked_shop_call_phone = (TextView) findViewById(R.id.clicked_shop_call_phone);//电话

        clicked_shop_close      = (ImageView) findViewById(R.id.clicked_shop_close);//关闭

        clicked_shop_listview   = (ListView) findViewById(R.id.clicked_shop_listview);//列表  大于2的时候才会显示

        //
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) bottom.getLayoutParams();
        if (size == 1)
        {
            linearParams.height = 240;
        }
        else
        {
            linearParams.height = height / 2;
        }

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

                break;
            case R.id.clicked_shop_call_phone:
                toastMgr.builder.display("打电话给店主吗", 0);
                final String phoneNUm = shopInfoPhoneNum;
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
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:"+ phoneNUm));
                                mContext.startActivity(intent);
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

                intent.setClass(mContext, FirstPageShopShowActivity.class);
                intent.putExtra("shopBean",mShopBean);
                intent.putExtra("item", -1);//-1代表最上面一个点击
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
