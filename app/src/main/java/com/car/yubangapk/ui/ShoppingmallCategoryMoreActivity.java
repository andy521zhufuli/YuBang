package com.car.yubangapk.ui;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

/**
 * ShoppingmallCategoryMoreActivity: 商城分类中 repairService点击更多, 显示更多的服务
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */

public class ShoppingmallCategoryMoreActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private Context mContext;
    private final static String TAG = "ShoppingmallCategoryMoreActivity";
    ImageView                   img_back;//返回
    ListView                    shoppingmall_repairservice_more_listview;//
    private List<Json2ShoppingmallBottomPicsBean> mRepairServiceList;        //保养维护保存获取的图片信息

    private MoreListViewAdapter mMoreRepairServiceAdapter;
    String mRepairService;//这是一个列表
    String from;
    String carType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_category_more);

        mContext = this;

        findViews();



        Bundle bundle = getIntent().getExtras();
        mRepairServiceList = (List<Json2ShoppingmallBottomPicsBean>) bundle.getSerializable(ShoppingMallActivity.BUNDLE_DATA_NAME_MORE_ACTIVITY);
        mRepairService = bundle.getString("repairService");
        from = bundle.getString(Configs.FROM);
        carType = bundle.getString("mCarType");
        mMoreRepairServiceAdapter = new MoreListViewAdapter(mRepairServiceList);

        shoppingmall_repairservice_more_listview.setAdapter(mMoreRepairServiceAdapter);


    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回
        shoppingmall_repairservice_more_listview = (ListView) findViewById(R.id.shoppingmall_repairservice_more_listview);//

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shoppingmall_repairservice_more_listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        onItemClick(position);
    }


    class MoreListViewAdapter extends BaseAdapter
    {

        private List<Json2ShoppingmallBottomPicsBean> repairServiceList;

        public MoreListViewAdapter(List<Json2ShoppingmallBottomPicsBean> list)
        {
            this.repairServiceList = list;
        }

        public void refresh(List<Json2ShoppingmallBottomPicsBean> list)
        {
            this.repairServiceList = list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return repairServiceList.size();
        }

        @Override
        public Object getItem(int position) {
            return repairServiceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

             /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_repair_service_more_listview, null);

                holder.repair_service_pic = (ImageView) view.findViewById(R.id.repair_service_pic);
                holder.repair_service_name = (TextView) view.findViewById(R.id.repair_service_name);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            String pathcode = repairServiceList.get(position).getPathCode();
            String photoname = repairServiceList.get(position).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            holder.repair_service_name.setText(repairServiceList.get(position).getServiceName());
            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.repair_service_pic);
            return view;
        }

        class ViewHolder
        {
            ImageView       repair_service_pic;//照片
            TextView        repair_service_name;//名字
        }
    }



    private void onItemClick(int item)
    {
        toastMgr.builder.display("item" + item + "click", 1);

        Bundle bundle = new Bundle();

        String serviceId6 = mRepairServiceList.get(item).getId();

        bundle.putString("serviceId", serviceId6);
        bundle.putString("repairService",mRepairService);
        bundle.putString("mCarType", carType);
        bundle.putString(Configs.FROM, from);

        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingMallGoodsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
