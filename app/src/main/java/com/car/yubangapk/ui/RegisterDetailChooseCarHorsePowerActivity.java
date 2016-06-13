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
import com.car.yubangapk.json.bean.Json2HorsePowerBean;
import com.car.yubangapk.json.formatJson.Json2HorsePower;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;


/**
 * RegisterDetailChooseCarCapacityActivity: 注册详情  选择车辆信息 --选择车辆马力
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-06-13
 */
public class RegisterDetailChooseCarHorsePowerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterDetailChooseCarHorsePowerActivity";

    private Context mContext;
    private ListView capacityListView;
    private ImageView img_back;
    private CarHorsePowerAdapter mCarHorsePowerAdapter;

    private List<Json2HorsePowerBean> mJson2CarHorsePowerBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_horse_power);

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

                String name = mJson2CarHorsePowerBeanList.get(position).getHorsepower();
                toastMgr.builder.display("您选择的马力是" + name, 1);
//                String capacityId = mJson2CarHorsePowerBeanList.get(position).getId();
//                SPUtils.put(mContext, "capacityId", capacityId);
                //马力写入SP里面, 选择完毕后,  直接从sp里面取出来
                SPUtils.put(mContext, "chooseedHorsePower", mJson2CarHorsePowerBeanList.get(position).getHorsepower());

                Intent intent = new Intent();
                intent.setClass(mContext, RegisterDetailChooseCarMotivationActivity.class);
                startActivity(intent);
            }
        });

    }



    private void getCarCapacityInfo()
    {
        String companyId;
        String carBrand;
        String childBrand;
        companyId = (String) SPUtils.get(mContext, "companyId", "");
        carBrand = (String) SPUtils.get(mContext, "brandId", "");
        childBrand = (String) SPUtils.get(mContext, "seriesId", "");
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientGetCarHorsepower")
                .addParams("dataReqModel.args.carCompany",companyId)
                .addParams("dataReqModel.args.carBrand", carBrand)
                .addParams("dataReqModel.args.carSeries", childBrand)
                .build()
                .execute(new GetCarCapacityCallback());
        L.i(TAG, "车辆马力 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=clientGetCarHorsepower"
                        + "&dataReqModel.args.carCompany=" + companyId
                        + "&dataReqModel.args.carBrand=" + carBrand
                        + "&dataReqModel.args.carSeries=" + childBrand
        );
    }
    class GetCarCapacityCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "马力 json -= " + response);
            List<Json2HorsePowerBean> json2CarCapacityBeanList;
            Json2HorsePower json2CarCapacity= new Json2HorsePower(response);
            json2CarCapacityBeanList = json2CarCapacity.getHorsePower();
            mJson2CarHorsePowerBeanList = json2CarCapacityBeanList;
            //拿到信息 然后是显示出来
            //显示到
            mCarHorsePowerAdapter = new CarHorsePowerAdapter(mJson2CarHorsePowerBeanList);
            capacityListView.setAdapter(mCarHorsePowerAdapter);





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


    class CarHorsePowerAdapter extends BaseAdapter
    {

        List<Json2HorsePowerBean> json2HorsePowerBeans;

        public CarHorsePowerAdapter(List<Json2HorsePowerBean> list) {

            this.json2HorsePowerBeans = list;
        }

        @Override
        public int getCount() {
            return json2HorsePowerBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return json2HorsePowerBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            final Json2HorsePowerBean mContent = json2HorsePowerBeans.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.json2HorsePowerBeans.get(position).getHorsepower());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
