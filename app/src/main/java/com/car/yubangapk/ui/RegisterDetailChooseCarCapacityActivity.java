package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2CarCapacityBean;
import com.car.yubangapk.json.formatJson.Json2CarCapacity;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * RegisterDetailChooseCarCapacityActivity: 注册详情  选择车辆信息 提交验证资料  选择车辆排量
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-04-04
 */
public class RegisterDetailChooseCarCapacityActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterDetailChooseCarCapacityActivity";

    private Context mContext;
    private ListView capacityListView;
    private ImageView img_back;
    private CarCapacityAdapter carCapacityAdapter;

    private List<Json2CarCapacityBean> mJson2CarCapacityBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_capacity);

        mContext = this;

        findViews();

        getCarCapacityInfo();


    }

    private void findViews() {

        capacityListView = (ListView) findViewById(R.id.choose_car_capacity_listview);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(this);


        capacityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String name = mJson2CarCapacityBeanList.get(position).getCapacityName();
                toastMgr.builder.display("您选择的是" + name, 1);
                String capacityId = mJson2CarCapacityBeanList.get(position).getId();
                SPUtils.put(mContext, "capacityId", capacityId);
                SPUtils.put(mContext, "chooseedCapacity", mJson2CarCapacityBeanList.get(position).getCapacityName());

                Intent intent = new Intent();

                intent.setClass(mContext, RegisterDetailChooseCarYearActivity.class);
                startActivity(intent);
            }
        });

    }



    private void getCarCapacityInfo()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientCarCapacity")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new GetCarCapacityCallback());
        L.i(TAG, "车辆year url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientCarCapacity&dataReqModel.args.needTotal=needTotal");
    }
    class GetCarCapacityCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "汽车year名字 json -= " + response);
            List<Json2CarCapacityBean> json2CarCapacityBeanList = new ArrayList<Json2CarCapacityBean>();
            Json2CarCapacity json2CarCapacity= new Json2CarCapacity(response);
            json2CarCapacityBeanList = json2CarCapacity.getCarcapacityList();
            mJson2CarCapacityBeanList = json2CarCapacityBeanList;
            //拿到信息 然后是显示出来
            //显示到
            carCapacityAdapter = new CarCapacityAdapter(mJson2CarCapacityBeanList);
            capacityListView.setAdapter(carCapacityAdapter);





        }
    }

    @Override
  public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
        }

    }


    class CarCapacityAdapter extends BaseAdapter
    {

        List<Json2CarCapacityBean> capacityList;

        public CarCapacityAdapter(List<Json2CarCapacityBean> list) {

            this.capacityList = list;
        }

        @Override
        public int getCount() {
            return capacityList.size();
        }

        @Override
        public Object getItem(int position) {
            return capacityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            final Json2CarCapacityBean mContent = capacityList.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.capacityList.get(position).getCapacityName());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
