package com.car.yubangapk.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.Json2CarSeriesBean;
import com.car.yubangapk.json.formatJson.Json2CarBrand;
import com.car.yubangapk.json.formatJson.Json2CarSeries;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * RegisterDetailChooseCarYearActivity: 注册详情  选择车辆信息-->选择汽车子品牌-->选择年份
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0.6
 * @created 2016-04-3
 */
public class RegisterDetailChooseCarYearActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "RegisterDetailChooseCarYearActivity";
    private Context mContext;


    private ImageView img_back;
    private TextView choose_car_year_catelog;
    private ListView choose_car_year_listview;


    List<Json2CarSeriesBean> mJson2CarYearBeanList;


    private CarYearAdapter brandAdapter;
    String yearId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail_choose_car_year);

        mContext = this;

        findViews();

        Bundle bundle = getIntent().getExtras();
        yearId = bundle.getString("brand");


        getCarBrandInfo(yearId);
    }


    /**
     * 去拿车的品牌信息
     */
    private void getCarBrandInfo(String yearId)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientGetCarSeries")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.brand", yearId)
                .build()
                .execute(new GetCarBrandCallback());
        L.i(TAG, "车辆year url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientGetCarSeries&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.brand=" + yearId);
    }
    class GetCarBrandCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "汽车year名字 json -= " + response);
            List<Json2CarSeriesBean> json2CarYearBeanList = new ArrayList<Json2CarSeriesBean>();
            Json2CarSeries json2CarSeries= new Json2CarSeries(response);
            json2CarYearBeanList = json2CarSeries.getSeriesList();
            mJson2CarYearBeanList = json2CarYearBeanList;
            //拿到信息 然后是显示出来
            //显示到
            brandAdapter = new CarYearAdapter(json2CarYearBeanList);
            choose_car_year_listview.setAdapter(brandAdapter);



        }
    }



    /**
     * 去拿车的品牌信息
     */
    private void getCarYesrInfo(String yearId)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientProduceYear")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new GetCarYearCallback());
        L.i(TAG, "车辆year url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientGetCarSeries&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.brand=" + yearId);
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
            List<Json2CarSeriesBean> json2CarYearBeanList = new ArrayList<Json2CarSeriesBean>();
            Json2CarSeries json2CarSeries= new Json2CarSeries(response);
            json2CarYearBeanList = json2CarSeries.getSeriesList();

            //拿到信息 然后是显示出来
            //显示到
            brandAdapter = new CarYearAdapter(json2CarYearBeanList);
            choose_car_year_listview.setAdapter(brandAdapter);



        }
    }


    /**
     * 绑定views
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        choose_car_year_catelog = (TextView) findViewById(R.id.choose_car_year_catelog);
        choose_car_year_listview = (ListView) findViewById(R.id.choose_car_year_listview);

        img_back.setOnClickListener(this);
        choose_car_year_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                toastMgr.builder.display("position = " + position, 1);

                getCarYesrInfo(mJson2CarYearBeanList.get(position).getId());

            }
        });

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


    class CarYearAdapter extends BaseAdapter
    {

        List<Json2CarSeriesBean> brandList;

        public CarYearAdapter(List<Json2CarSeriesBean> list) {

            this.brandList = list;
        }

        @Override
        public int getCount() {
            return brandList.size();
        }

        @Override
        public Object getItem(int position) {
            return brandList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            final Json2CarSeriesBean mContent = brandList.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.brandList.get(position).getCarSeriesName());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
