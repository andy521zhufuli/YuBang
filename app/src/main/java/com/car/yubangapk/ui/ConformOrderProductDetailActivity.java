package com.car.yubangapk.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.utils.L;

import java.util.List;

public class ConformOrderProductDetailActivity extends BaseActivity {

    Context mContext;

    ListView product_detail_listview;//
    ImageView img_back;

    private List<Json2ProductPackageBean> mProductPackageListToOrderProductDetailPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_conform_order_product_detail);


        Bundle bundle  = getIntent().getExtras();
        mProductPackageListToOrderProductDetailPage = (List<Json2ProductPackageBean>) bundle.getSerializable("productPackageList");

        mContext = this;

        findViews();


        ProductPackageAdapter adapter =  new ProductPackageAdapter(mProductPackageListToOrderProductDetailPage);

        product_detail_listview.setAdapter(adapter);
    }
    /**
     * 绑定
     */
    private void findViews() {

        product_detail_listview = (ListView) findViewById(R.id.product_detail_listview);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    /**
     * 列表暂时适配器
     *
     *TODO 这里要把这个适配器抽象出来 放在adapter下面
     *
     * 适配器的定义,要继承BaseAdapter
     */
    public class ProductPackageAdapter extends BaseAdapter {

        List<Json2ProductPackageBean> mpplist;

        public ProductPackageAdapter(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
            this.mpplist = json2ProductPackageBeanList;


        }


        public void refresh(List<Json2ProductPackageBean> json2ProductPackageBeanList) {
            mpplist = json2ProductPackageBeanList;
            notifyDataSetChanged();
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
                view = View.inflate(mContext, R.layout.item_comform_order_product_detail_item, null);


                holder.produte_pic = (ImageView) view.findViewById(R.id.produte_pic);

                holder.maintenance_produte_name = (TextView) view.findViewById(R.id.maintenance_produte_name);
                holder.produte_price = (TextView) view.findViewById(R.id.produte_price);
                holder.produte_count = (TextView) view.findViewById(R.id.produte_count);
                holder.maintenance_hecheng_1 = (TextView) view.findViewById(R.id.maintenance_hecheng_1);
                holder.product_service_name = (TextView) view.findViewById(R.id.product_service_name);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            String pathcode = mpplist.get(position).getPathCode();
            String photoname = mpplist.get(position).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;

            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.produte_pic);


            holder.maintenance_produte_name.setText(mpplist.get(position).getProductName());
            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");

            holder.maintenance_hecheng_1.setText(mpplist.get(position).getProductShow());

            holder.produte_count.setText(mpplist.get(position).getProductAmount() + "");

            holder.produte_price.setText(mpplist.get(position).getRetailPrice() + "");

            holder.product_service_name.setText(mpplist.get(position).getPackageName());




            L.d("会不会重新刷新");


            return view;
        }

        class ViewHolder
        {
            ImageView       produte_pic;//左侧图片


            TextView        maintenance_produte_name;//产品名字

            TextView        maintenance_hecheng_1;//半合成

            TextView        produte_price;//产品价格
            TextView        produte_count;//产品数量

            TextView        product_service_name;//产品包名字


        }
    }



}
