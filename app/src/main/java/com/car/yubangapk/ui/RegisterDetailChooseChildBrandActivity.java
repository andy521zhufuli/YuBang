package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.formatJson.Json2CarBrand;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.sortListview.SortModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * RegisterDetailChooseChildBrandActivity: 注册详情  选择车辆信息-->选择汽车子品牌
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0.6
 * @created 2016-04-3
 */
public class RegisterDetailChooseChildBrandActivity extends BaseActivity implements View.OnClickListener{


    private static final String TAG = "RegisterDetailChooseChildBrandActivity";
    private Context mContext;


    private ImageView img_back;
    private TextView  choose_car_brand_catelog;
    private ListView  choose_car_brand_listview;

    private List<Json2CarBrandBean> mJson2CarBrandBean;

    private CarBrandAdapter brandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_child_brand);

        mContext = this;

        Bundle bundle = getIntent().getExtras();
        String companyId = bundle.getString("companyId");

        findViews();



        getCarBrandInfo(companyId);


    }


    /**
     * 去拿车的品牌信息
     */
    private void getCarBrandInfo(String companyId)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientGetCarBrand")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.carCompany", companyId)
                .build()
                .execute(new GetCarBrandCallback());
        L.i(TAG, "车辆brand url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientGetCarBrand&dataReqModel.args.needTotal=needTotal"
                + "&dataReqModel.args.carCompany=" + companyId);
    }
    class GetCarBrandCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "汽车brand名字 json -= " + response);
            List<Json2CarBrandBean> json2CarBrandBeanList = new ArrayList<>();
            Json2CarBrand json2CarBrand = new Json2CarBrand(response);
            json2CarBrandBeanList = json2CarBrand.getBrandList();
            mJson2CarBrandBean = json2CarBrandBeanList;
            //拿到信息 然后是显示出来
            //显示到
            brandAdapter = new CarBrandAdapter(mJson2CarBrandBean);
            choose_car_brand_listview.setAdapter(brandAdapter);

        }
    }

    /**
     * 绑定views
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        choose_car_brand_catelog = (TextView) findViewById(R.id.choose_car_brand_catelog);
        choose_car_brand_listview = (ListView) findViewById(R.id.choose_car_brand_listview);

        img_back.setOnClickListener(this);
        choose_car_brand_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                toastMgr.builder.display("position = " + position, 1);
                Intent intent =  new Intent();
                intent.setClass(mContext, RegisterDetailChooseCarYearActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("brand", mJson2CarBrandBean.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
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
            default:
                break;
        }
    }


    class CarBrandAdapter extends BaseAdapter
    {

        List<Json2CarBrandBean> brandList;

        public CarBrandAdapter(List<Json2CarBrandBean> list) {

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
            final Json2CarBrandBean mContent = brandList.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_register_detail_choose_car_brand, null);
                viewHolder.choose_car_brand_image = (ImageView) view.findViewById(R.id.choose_car_brand_image);
                viewHolder.choose_car_brand_name = (TextView) view.findViewById(R.id.choose_car_brand_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.choose_car_brand_name.setText(this.brandList.get(position).getCarBrandName());

            return view;
        }



    }

    final static class ViewHolder {
        ImageView choose_car_brand_image;
        TextView choose_car_brand_name;
    }
}
