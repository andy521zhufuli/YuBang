package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyStringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * ShoppingmallProductModifyActivity: 产品包界面 点击修改
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */

public class ShoppingmallProductModifyActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private static final String TAG = ShoppingmallProductModifyActivity.class.getName();

    private ImageView           img_back;
    private TextView            header_name;//
    private ListView            product_service_modify;//需要更改的服务的列表
    private Button              info_check_sure_btn;//确定按钮
    private CustomProgressDialog mProgressDialog;

    private List<Json2ShoppingmallBottomPicsBean> mBeans;//商城首页的每个(保养维护对应的6个东西 以及更多)

    private String mCarType;
    private List<Json2ProductPackageIdBean> mJson2ProductPackageIdBeans;
    private Map<Integer,List<Json2ProductPackageIdBean>> mMap = new HashMap<>();//存放每一个item对应的list
    private int mSizeBean;

    private ProductServiceModifyAdapter mProductServiceModifyAdapter;
    private ShopServiceModifyAdapter   mShopServiceAdapter;


    private String mFrom;


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_shoppingmall_product_modify);

        mContext = this;

        mProgressDialog = new CustomProgressDialog(mContext);

        findViews();

        Bundle bundle = getIntent().getExtras();
        String response = bundle.getString("response");
        String carTyep = bundle.getString(Configs.mCarType);
        mFrom = bundle.getString(Configs.FROM);
        mCarType = carTyep;

        mCheckedProductPackageIdBeans = new ArrayList<>();//记录选中的产品包的名字  id 相关信息
        mCheckedShopServiceBeans = new ArrayList<>();//记录选中的门店服务的信息
        mCheckedBox                   = new ArrayList<>();//记录item中的哪些checkbox被选中


        List<Json2ShoppingmallBottomPicsBean> list = null;
        List<Json2ShopServiceBean>              shopServiceList;
        if (Configs.FROM_SHOPPINGMALL.equals(mFrom))
        {
            list = (List<Json2ShoppingmallBottomPicsBean>) bundle.getSerializable("shoppingmallBottomPicBeanList");
            getAllRepareServiceByIdsNew(list);
        }
        else
        {
            shopServiceList = (List<Json2ShopServiceBean>) bundle.getSerializable("shopServiceBean");

            getAllShopServiceByIds(shopServiceList);
        }

    }

    /**
     * 从门店传进来  去拿修改的列表
     * @param shopServiceList
     */
    private void getAllShopServiceByIds(List<Json2ShopServiceBean> shopServiceList) {

        mShopServiceAdapter = new ShopServiceModifyAdapter(shopServiceList);
        product_service_modify.setAdapter(mShopServiceAdapter);


    }


    private void getAllRepareServiceByIdsNew(List<Json2ShoppingmallBottomPicsBean> list) {


        List<Json2ShoppingmallBottomPicsBean> beans = list;
        if (beans == null)
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("没有可修改")
                    .setCancelable(false)
                    .setNegativeButton("返回", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .show();
            return;
        }
        else
        {
            mBeans = beans;
            int size = beans.size();
            mSizeBean = size;
            for (int position = 0; position < size; position++)
            {
                String id = beans.get(position).getId();

                httpGetProductPackageId(id,mCarType, position);
            }
        }


    }


    /**
     * 通过传过来的Json2ProductPackageBean  是字符串  拿到里面所有的id  然后遍历每个id里面的产品包的名字
     */
    private void getAllRepareServiceByIds(String response) {

        Json2ShoppingmallBottomPics pic = new Json2ShoppingmallBottomPics(response);
        List<Json2ShoppingmallBottomPicsBean> beans = pic.getShoppingmallBottomPics();
        if (beans == null)
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("没有可修改")
                    .setCancelable(false)
                    .setNegativeButton("返回", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .show();
            return;
        }
        else
        {
            mBeans = beans;
            int size = beans.size();
            mSizeBean = size;
            for (int position = 0; position < size; position++)
            {
                String id = beans.get(position).getId();

                httpGetProductPackageId(id,mCarType, position);
            }
        }


    }

    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        header_name = (TextView) findViewById(R.id.header_name);//
        product_service_modify = (ListView) findViewById(R.id.product_service_modify);//需要更改的服务的列表
        info_check_sure_btn = (Button) findViewById(R.id.info_check_sure_btn);//确定按钮


        header_name.setText("保养项目选择");
        img_back.setOnClickListener(this);
        info_check_sure_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;

            case R.id.info_check_sure_btn:
                //mCheckedProductPackageIdBeans ;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                if(mFrom.equals(Configs.FROM_SHOPPINGMALL))
                {
                    bundle.putSerializable("bean", (Serializable) mCheckedProductPackageIdBeans);
                }
                else
                {
                    bundle.putSerializable("bean", (Serializable) mCheckedShopServiceBeans);
                }


                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(Activity.RESULT_CANCELED);

    }

    /**
     * 根据参数去获取产品包id
     * @param serviceId 门店id
     * @param carType 车主车类型
     */
    private synchronized void  httpGetProductPackageId(String serviceId, String carType, int position) {

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchCarRepairServiceProductPackage")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.carType", carType)
                .addParams("dataReqModel.args.repairService",serviceId)
                .build()
                .executeMy(new GetProductPackageIdCallback(), position);

        L.i(TAG, "获取产皮包修改界面 id url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchCarRepairServiceProductPackage"
                        + "&dataReqModel.args.carType=" + carType
                        + "&dataReqModel.args.needTotal=needTotal"
                        + "&dataReqModel.args.repairService=" + serviceId
        );

    }
    class GetProductPackageIdCallback extends MyStringCallback {


        @Override
        public void onError(Call call, int position, Exception e) {
            toastMgr.builder.display("服务器错误", 1);

            //这里应该在布局文件里面写多一个  就是提示用户 没有相关产品包
        }

        @Override
        public void onResponse(String response, int position) {
            L.d(TAG, "产品包id json = " + response);

            synchronized (this)
            {


                Json2ProductPackageId json2ProductId = new Json2ProductPackageId(response);
                final List<Json2ProductPackageIdBean> json2ProductIdBeanList = json2ProductId.getProductIds();
                if (json2ProductId == null)
                {
                    toastMgr.builder.display("您当前版本太低,请升级版本", 1);

                }
                else
                {
                    if (json2ProductIdBeanList.get(0).isHasData() == false)
                    {

                    }
                    else
                    {
                        //就放到listview里面去显示
                        if (isFirstShowListview == false)
                        {
                            isFirstShowListview = true;
                            mJson2ProductPackageIdBeans = json2ProductIdBeanList;
                            mProductServiceModifyAdapter = new ProductServiceModifyAdapter(mJson2ProductPackageIdBeans);
                            product_service_modify.setAdapter(mProductServiceModifyAdapter);
                        }
                        else
                        {
                            int length = json2ProductIdBeanList.size();
                            for (int i = 0; i < length; i++)
                            {
                                mJson2ProductPackageIdBeans.add(json2ProductIdBeanList.get(i));
                                mProductServiceModifyAdapter.refresh(mJson2ProductPackageIdBeans);
                            }
                        }


                    }
                }
            }




        }
    }



    private class ShopServiceModifyAdapter extends BaseAdapter
    {

        List<Json2ShopServiceBean> beans;

        ShopServiceModifyAdapter(List<Json2ShopServiceBean> beans)
        {
            this.beans = beans;
        }


        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int i) {
            return beans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (view == null)
            {
                holder =  new ViewHolder();
                view = View.inflate(mContext, R.layout.item_product_service_modify_item, null);

                holder.product_service_modify_left_image = (ImageView) view.findViewById(R.id.product_service_modify_left_image);
                holder.product_service_modify_name = (TextView) view.findViewById(R.id.product_service_modify_name);
                holder.product_service_modify_check = (CheckBox) view.findViewById(R.id.product_service_modify_check);
                holder.product_service_modify_layout = (LinearLayout) view.findViewById(R.id.product_service_modify_layout);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            holder.product_service_modify_name.setText(beans.get(i).getServiceName());

            holder.product_service_modify_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (holder.product_service_modify_check.isChecked() == false)
                    {
                        holder.product_service_modify_check.setChecked(true);

                        mCheckBoxMap.put(i, holder.product_service_modify_check);
                    }
                    else
                    {
                        holder.product_service_modify_check.setChecked(false);
//                mCheckedBox.remove(mPositoin);
                        mCheckBoxMap.remove(i);
                    }

                    //遍历mCheckBoxMap 中有几个 然后就有几个放进去了
                    int index = 0;

                    mCheckedShopServiceBeans.clear();
                    for (index = 0; index < beans.size(); index++)
                    {


                        if (mCheckBoxMap.containsKey(index) == true)
                        {
                            Json2ShopServiceBean bean = beans.get(index);
                            mCheckedShopServiceBeans.add(bean);
                        }
                    }

                    int count = 0;
                    count = mCheckedShopServiceBeans.size();

                    info_check_sure_btn.setText("确定("+ count + ")");

                }
            });





            return view;
        }


        class ViewHolder
        {
            ImageView       product_service_modify_left_image;//左侧图片


            TextView        product_service_modify_name;//服务名字

            CheckBox        product_service_modify_check;//选择框

            LinearLayout    product_service_modify_layout;//整个item布局
        }
    }



    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class ProductServiceModifyAdapter extends BaseAdapter {

        List<Json2ProductPackageIdBean> mpplist;


        public ProductServiceModifyAdapter() {
        }

        public ProductServiceModifyAdapter(List<Json2ProductPackageIdBean> json2ProductPackageBeanList) {
            this.mpplist = json2ProductPackageBeanList;
        }


        public void refresh(List<Json2ProductPackageIdBean> json2ProductPackageBeanList) {
            mpplist = json2ProductPackageBeanList;
            notifyDataSetChanged();
            L.d("refresh");
        }


        @Override
        public int getCount() {
            return mpplist.size();
        }

        @Override
        public Object getItem(int position) {
            return mpplist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null)
            {
                holder =  new ViewHolder();
                view = View.inflate(mContext, R.layout.item_product_service_modify_item, null);

                holder.product_service_modify_left_image = (ImageView) view.findViewById(R.id.product_service_modify_left_image);
                holder.product_service_modify_name = (TextView) view.findViewById(R.id.product_service_modify_name);
                holder.product_service_modify_check = (CheckBox) view.findViewById(R.id.product_service_modify_check);
                holder.product_service_modify_layout = (LinearLayout) view.findViewById(R.id.product_service_modify_layout);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }


            String packageName = mpplist.get(position).getPackageName();

            holder.product_service_modify_name.setText(packageName);

            String pathcode = mpplist.get(position).getPathCode();
            String photoname = mpplist.get(position).getPhotoName();
            //String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;


            holder.product_service_modify_layout.setOnClickListener(new ListViewItemClick(position, holder.product_service_modify_check));
            return view;
        }


        class ViewHolder
        {
            ImageView       product_service_modify_left_image;//左侧图片


            TextView        product_service_modify_name;//服务名字

            CheckBox product_service_modify_check;//选择框

            LinearLayout    product_service_modify_layout;//整个item布局
        }
    }

    class ListViewItemClick implements View.OnClickListener
    {

        int mPositoin;

        CheckBox mImageCheck;

        public ListViewItemClick(int pos, CheckBox check)
        {
            this.mPositoin = pos;
            this.mImageCheck = check;
        }

        @Override
        public void onClick(View view)
        {
            toastMgr.builder.display("item " + mPositoin + "click", 1);

            if (mImageCheck.isChecked() == false)
            {
                mImageCheck.setChecked(true);
//                mCheckedBox.add(mPositoin, mImageCheck);
                mCheckBoxMap.put(mPositoin, mImageCheck);
            }
            else
            {
                mImageCheck.setChecked(false);
//                mCheckedBox.remove(mPositoin);
                mCheckBoxMap.remove(mPositoin);
            }

            //遍历mCheckBoxMap 中有几个 然后就有几个放进去了
            int index = 0;

            mCheckedProductPackageIdBeans.clear();
            for (index = 0; index < mSizeBean; index++)
            {


                if (mCheckBoxMap.containsKey(index) == true)
                {
                    Json2ProductPackageIdBean bean = mJson2ProductPackageIdBeans.get(index);
                    mCheckedProductPackageIdBeans.add(bean);
                }
            }

            int count = 0;
            count = mCheckedProductPackageIdBeans.size();

            info_check_sure_btn.setText("确定("+ count + ")");










        }
    }



    private boolean isFirstShowListview = false;
    private List<CheckBox> mCheckedBox;//记录item中的哪些checkbox被选中
    private List<Json2ProductPackageIdBean> mCheckedProductPackageIdBeans;//记录选中的产品包的名字  id 相关信息
    private Map<Integer, CheckBox> mCheckBoxMap = new HashMap<>();
    private List<Json2ShopServiceBean> mCheckedShopServiceBeans;

}
