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
import com.car.yubangapk.json.bean.Json2CarYearBean;
import com.car.yubangapk.json.formatJson.Json2CarYear;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * RegisterDetailChooseCarYear: 注册详情  选择车辆信息 提交验证资料  选择车辆年份
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-04-04
 */

public class RegisterDetailChooseCarYearActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "RegisterDetailChooseCarYearActivity";

    private Context mContext;
    private ListView yearListView;
    private ImageView img_back;
    private CarYearAdapter carYearAdapter;
    List<Json2CarYearBean> mJson2CarYearBeanList;

    private String childbrandid;//穿过来的子品牌id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_year);

        mContext = this;


        findViews();
//        Bundle bundle = getIntent().getExtras();
//        childbrandid = bundle.getString("childbrandid");


        getCarYearInfo();

    }


    private void findViews() {

        yearListView = (ListView) findViewById(R.id.choose_car_year_listview);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(this);
        yearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = mJson2CarYearBeanList.get(position).getCarProduceYear();
                toastMgr.builder.display("您选择的是" + name, 1);
                String yearId = mJson2CarYearBeanList.get(position).getId();
                SPUtils.put(mContext, "yearId", yearId);
                SPUtils.put(mContext,"chooseedYear",mJson2CarYearBeanList.get(position).getCarProduceYear());
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterDetailsActivity.class);
                Bundle bundle = new Bundle();
                String carCompany   = (String) SPUtils.get(mContext, Configs.carCompany,"");
                String carBrand     = (String) SPUtils.get(mContext, Configs.carBrand,"");
                String carSeries    = (String) SPUtils.get(mContext, Configs.carSeries,"");
                String produceYear  = (String) SPUtils.get(mContext, Configs.produceYear,"");
                String carCapacity  = (String) SPUtils.get(mContext, Configs.carCapacity,"");

                bundle.putString(Configs.carCompany,carCompany);
                bundle.putString(Configs.carBrand,carBrand);
                bundle.putString(Configs.carSeries,carSeries);
                bundle.putString(Configs.produceYear,produceYear);
                bundle.putString(Configs.carCapacity,carCapacity);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



    }


    /**
     * 上传车型信息
     */
    private void upLoadCarType()
    {

        String carCompany = (String) SPUtils.get(mContext, Configs.carCompany,"");
        String carBrand = (String) SPUtils.get(mContext, Configs.carBrand,"");
        String carSeries = (String) SPUtils.get(mContext, Configs.carSeries,"");
        String produceYear = (String) SPUtils.get(mContext, Configs.produceYear,"");
        String carCapacity = (String) SPUtils.get(mContext, Configs.carCapacity,"");

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE)
                .addParams("userReq.carCompany", carCompany)
                .addParams("userReq.carBrand", carBrand)
                .addParams("userReq.carSeries", carSeries)
                .addParams("userReq.produceYear", produceYear)
                .addParams("userReq.carCapacity", carCapacity)
                .build()
                .execute(new UploadCarTypeCallback());
        L.i(TAG, "车辆year url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "userReq.carCompany=" +  carCompany
                        + "&userReq.carCompany" + carBrand
                        + "&userReq.carSeries" + carSeries
                        + "&userReq.produceYear" + produceYear
                        + "&userReq.carCapacity" + carCapacity
        );

    }

    class UploadCarTypeCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "上传汽车信息 json -= " + response);
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



    /**
     * 去拿车的品牌信息
     */
    private void getCarYearInfo()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientProduceYear")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new GetCarYearCallback());
        L.i(TAG, "车辆year url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientProduceYear&dataReqModel.args.needTotal=needTotal");
    }
    class GetCarYearCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "汽车year名字 json -= " + response);
            List<Json2CarYearBean> json2CarYearBeanList = new ArrayList<Json2CarYearBean>();
            Json2CarYear json2CarYear= new Json2CarYear(response);
            json2CarYearBeanList = json2CarYear.getCarYearList();
            mJson2CarYearBeanList = json2CarYearBeanList;
            //拿到信息 然后是显示出来
            //显示到
            carYearAdapter = new CarYearAdapter(mJson2CarYearBeanList);
            yearListView.setAdapter(carYearAdapter);




        }
    }

    class CarYearAdapter extends BaseAdapter
    {

        List<Json2CarYearBean> json2carYearList;

        public CarYearAdapter(List<Json2CarYearBean> list) {

            this.json2carYearList = list;
        }

        @Override
        public int getCount() {
            return json2carYearList.size();
        }

        @Override
        public Object getItem(int position) {
            return json2carYearList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            final Json2CarYearBean mContent = json2carYearList.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.json2carYearList.get(position).getCarProduceYear());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
