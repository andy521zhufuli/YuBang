package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ChangeableProductBean;
import com.car.yubangapk.json.formatJson.Json2ChangeableProduct;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.List;

import okhttp3.Call;

/**
 * ShoppingmallProductChangeActivity: 产品包 更换产品 界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingmallProductChangeActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = ShoppingmallProductChangeActivity.class.getName();

    private ImageView           img_back;//返回
    private TextView            logolist_text_product_logo;//品牌选择
    private LinearLayout        all_kind_goods_check_layout;//全部商品
    private LinearLayout        logo_selcet_close;//关闭窗口
    private ListView            change_product_listview;//可更换商品列表
    private List<Json2ChangeableProductBean> mJson2ChangeableProductBeanList;


    private ChangeableProductAdapter mChangeableProductAdapter;

    private CustomProgressDialog mProgressDiaolog;

    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_product_change);

        mContext = this;

        findViews();

        String categoryId = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            mPosition = bundle.getInt("position");
            categoryId = bundle.getString(Configs.categoryId);

        }


        mProgressDiaolog = new CustomProgressDialog(mContext);

        listviewItemClicked();

        httpGetChangeProduct(categoryId);
    }

    /**
     * 根据商品的分类id获取可更换商品
     * @param categoryId
     */
    private void httpGetChangeProduct(String categoryId) {

        mProgressDiaolog = mProgressDiaolog.show(mContext,"正在加载...",false,null);

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchCategoryProduct")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.category", categoryId)
                .build()
                .execute(new GetChangeProductCallback());

        L.i(TAG, "获取更换产品url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchCategoryProduct"
                        + "&dataReqModel.args.productPackage=" + categoryId
                        + "&dataReqModel.args.needTotal=needTotal"
        );

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
            case R.id.all_kind_goods_check_layout:
                break;
            case R.id.logo_selcet_close:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
        }
    }

    class GetChangeProductCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请重新连接", 1);
            mProgressDiaolog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG,"获取更换产品 json = " + response);
            mProgressDiaolog.dismiss();
            Json2ChangeableProduct json2ChangeableProduct = new Json2ChangeableProduct(response);
            final List<Json2ChangeableProductBean> json2ChangeableProductBeanList = json2ChangeableProduct.getChangeableProduct();
            if (json2ChangeableProductBeanList == null)
            {
                toastMgr.builder.display("您当前版本太低,请升级版本", 1);

                UpdateApp.gotoUpdateApp(mContext);
            }
            else
            {
                if (json2ChangeableProductBeanList.get(0).isHasData() == false)
                {
                    //没有产品包
                    toastMgr.builder.display("对不起, 没有可更换产品",1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setTitle("提示")
                            .setCancelable(false)
                            .setMsg("没有相关产品包")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            })
                            .show();
                }
                else
                {
                    //拿到产品包  就去listview里面显示
                    mChangeableProductAdapter = new ChangeableProductAdapter(json2ChangeableProductBeanList);
                    mJson2ChangeableProductBeanList = json2ChangeableProductBeanList;
                    change_product_listview.setAdapter(mChangeableProductAdapter);
                }
            }
        }
    }


    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        logolist_text_product_logo = (TextView) findViewById(R.id.logolist_text_product_logo);//品牌选择
        all_kind_goods_check_layout = (LinearLayout) findViewById(R.id.all_kind_goods_check_layout);//全部商品
        logo_selcet_close = (LinearLayout) findViewById(R.id.logo_selcet_close);//关闭窗口
        change_product_listview = (ListView) findViewById(R.id.change_product_listview);//可更换商品列表


        img_back.setOnClickListener(this);
        all_kind_goods_check_layout.setOnClickListener(this);
        logo_selcet_close.setOnClickListener(this);
    }


    private void listviewItemClicked()
    {
        change_product_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            }
        });
    }

    /**
     * 可更换产品适配器的定义,要继承BaseAdapter
     */
    public class ChangeableProductAdapter extends BaseAdapter {

        List<Json2ChangeableProductBean> mpplist;


        public ChangeableProductAdapter() {
        }

        public ChangeableProductAdapter(List<Json2ChangeableProductBean> json2ProductPackageBeanList) {
            this.mpplist = json2ProductPackageBeanList;
        }


        public void refresh(List<Json2ChangeableProductBean> json2ProductPackageBeanList) {
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
                view = View.inflate(mContext, R.layout.item_changeable_product_listview_data, null);

                holder.produte_pic = (ImageView) view.findViewById(R.id.produte_pic);

                holder.maintenance_produte_name = (TextView) view.findViewById(R.id.maintenance_produte_name);

                holder.maintenance_hecheng_1 = (TextView) view.findViewById(R.id.maintenance_hecheng_1);

                holder.produte_price = (TextView) view.findViewById(R.id.produte_price);

                holder.productitem_changge_before = (LinearLayout) view.findViewById(R.id.productitem_changge_before);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            String pathcode = mpplist.get(position).getPathCode();
            String photoname = mpplist.get(position).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;


            L.d(TAG, "产品包图片加载url = " + url);
            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.produte_pic);


            holder.maintenance_produte_name.setText(mpplist.get(position).getProductName());
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");

            holder.maintenance_hecheng_1.setText(mpplist.get(position).getProductShow());


            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");


            holder.productitem_changge_before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastMgr.builder.display("item " + position + "clicked", 1);
                    Json2ChangeableProductBean bean = mJson2ChangeableProductBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("changedProductBean", bean);
                    bundle.putInt("position", mPosition);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);

                    setResult(Activity.RESULT_OK, intent);
                    ShoppingmallProductChangeActivity.this.finish();
                }
            });

            L.d("会不会重新刷新");
            return view;
        }

        class ViewHolder
        {
            ImageView       produte_pic;//左侧图片

            TextView        maintenance_produte_name;//产品名字
            TextView        maintenance_hecheng_1;//半合成
            TextView        produte_price;//产品价格
            LinearLayout    productitem_changge_before;//item

        }
    }
}
