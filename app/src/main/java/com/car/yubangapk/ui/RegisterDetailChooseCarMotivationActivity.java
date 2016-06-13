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
import com.car.yubangapk.json.bean.Json2MotivationBean;
import com.car.yubangapk.json.formatJson.Json2CarCapacity;
import com.car.yubangapk.json.formatJson.Json2Motivation;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * RegisterDetailChooseCarMotivationActivity: 注册详情  选择车辆信息 --选择车辆马力
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-06-13
 */
public class RegisterDetailChooseCarMotivationActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterDetailChooseCarMotivationActivity";

    private Context mContext;
    private ListView capacityListView;
    private ImageView img_back;
    private CarMotivationAdapter mCarMotivationAdapter;

    private List<Json2MotivationBean> mJson2CarCapacityBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_motivation);

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

                String name = mJson2CarCapacityBeanList.get(position).getMotivation();
                toastMgr.builder.display("您选择的驱动形式是" + name, 1);
//                String capacityId = mJson2CarCapacityBeanList.get(position).getId();
//                SPUtils.put(mContext, "capacityId", capacityId);
                SPUtils.put(mContext, "chooseedMotivation", mJson2CarCapacityBeanList.get(position).getMotivation());

                Intent intent = new Intent();
                String from = (String) SPUtils.get(mContext, "chooseCarFrom", "");
                if ("".equals(from))
                {
                    intent.setClass(mContext, RegisterDetailsActivity.class);
                }
                else
                {
                    intent.setClass(mContext, MyPersonalInfoActivity.class);
                }


                Bundle bundle = new Bundle();
                String carCompany   = (String) SPUtils.get(mContext, Configs.carCompany,"");
                String carBrand     = (String) SPUtils.get(mContext, Configs.carBrand,"");
                String carSeries    = (String) SPUtils.get(mContext, Configs.carSeries,"");
                String produceYear  = (String) SPUtils.get(mContext, Configs.produceYear,"");
                String carCapacity  = (String) SPUtils.get(mContext, Configs.carCapacity,"");
                String carHorsepower  = (String) SPUtils.get(mContext, Configs.carHorsepower,"");
                String carMotivation  = (String) SPUtils.get(mContext, Configs.carMotivation,"");

                bundle.putString(Configs.carCompany,carCompany);
                bundle.putString(Configs.carBrand,carBrand);
                bundle.putString(Configs.carSeries,carSeries);
                bundle.putString(Configs.produceYear,produceYear);
                bundle.putString(Configs.carCapacity,carCapacity);
                bundle.putString(Configs.carHorsepower,carHorsepower);
                bundle.putString(Configs.carMotivation,carMotivation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }



    private void getCarCapacityInfo()
    {
        String companyId;
        String carBrand;
        String childBrand;
        String horsepower;
        companyId = (String) SPUtils.get(mContext, "companyId", "");
        carBrand = (String) SPUtils.get(mContext, "brandId", "");
        childBrand = (String) SPUtils.get(mContext, "seriesId", "");
        horsepower = (String) SPUtils.get(mContext, "chooseedHorsePower", "");
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientGetCarMotivation")
                .addParams("dataReqModel.args.carCompany",companyId)
                .addParams("dataReqModel.args.carBrand", carBrand)
                .addParams("dataReqModel.args.carSeries", childBrand)
                .addParams("dataReqModel.args.horsepower", horsepower)
                .build()
                .execute(new GetCarCapacityCallback());
        L.i(TAG, "车辆驱动形式 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=clientGetCarMotivation"
                        + "&dataReqModel.args.carCompany=" + companyId
                        + "&dataReqModel.args.carBrand=" + carBrand
                        + "&dataReqModel.args.carSeries=" + childBrand
                        + "&dataReqModel.args.horsepower=" + horsepower
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
            L.d(TAG, "汽车year名字 json -= " + response);
            List<Json2MotivationBean> json2CarCapacityBeanList;
            Json2Motivation json2CarCapacity= new Json2Motivation(response);
            json2CarCapacityBeanList = json2CarCapacity.getMotivation();
            mJson2CarCapacityBeanList = json2CarCapacityBeanList;
            //拿到信息 然后是显示出来
            //显示到

            if (json2CarCapacityBeanList.size() == 1)
            {
                if (json2CarCapacityBeanList.get(0).isHasData() == false)
                {
                    toastMgr.builder.display("没有相应车型的驱动形式, 请重新选择", 1);
                    return;
                }
            }

            mCarMotivationAdapter = new CarMotivationAdapter(mJson2CarCapacityBeanList);
            capacityListView.setAdapter(mCarMotivationAdapter);





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


    class CarMotivationAdapter extends BaseAdapter
    {

        List<Json2MotivationBean> capacityList;

        public CarMotivationAdapter(List<Json2MotivationBean> list) {

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
            final Json2MotivationBean mContent = capacityList.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.capacityList.get(position).getMotivation());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
