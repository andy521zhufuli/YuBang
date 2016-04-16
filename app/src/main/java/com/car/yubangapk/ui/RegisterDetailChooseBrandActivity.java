package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.sortListview.CharacterParser;
import com.car.yubangapk.view.sortListview.ClearEditText;
import com.car.yubangapk.view.sortListview.PinyinComparator;
import com.car.yubangapk.view.sortListview.SideBar;
import com.car.yubangapk.view.sortListview.SortAdapter;
import com.car.yubangapk.view.sortListview.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * RegisterDetailChooseChildBrandActivity: 注册详情  选择车辆信息-->选择汽车品牌
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0.6
 * @created 2016-04-3
 */
public class RegisterDetailChooseBrandActivity extends BaseActivity implements View.OnClickListener{


    private static final String TAG = "RegisterDetailChooseChildBrandActivity";
    private Context mContext;


    private ImageView img_back;
    private TextView  choose_car_brand_catelog;
    private ListView  choose_car_brand_listview;

    private List<Json2CarBrandBean> mJson2CarBrandBean;

    private CarBrandAdapter brandAdapter;


    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;



    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    String mCompanyId;
    String mCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_brand);

        mContext = this;

        Bundle bundle = getIntent().getExtras();
        String companyId = bundle.getString("companyId");

        mCompanyId = companyId;


        initViews();


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
            initViews1(mJson2CarBrandBean);

        }
    }


    private void initViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });




        sortListView = (ListView) findViewById(R.id.choose_car_brand_listview);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                toastMgr.builder.display(((SortModel) adapter.getItem(position)).getName(), 1);

                String name = mJson2CarBrandBean.get(position).getCarBrandName();
                toastMgr.builder.display("您选择的是" + name, 1);
                String brandId = mJson2CarBrandBean.get(position).getId();
                SPUtils.put(mContext, "brandId", brandId);

                //去到子品牌
                Intent intent = new Intent();
                intent.setClass(RegisterDetailChooseBrandActivity.this, RegisterDetailChooseCarChildBrandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("brand", mJson2CarBrandBean.get(position).getId());
                SPUtils.put(mContext, "chooseedBrand",mJson2CarBrandBean.get(position).getCarBrandName());
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



    /**
     * 这里需要优化
     * @param json2CarBrandBeanList
     */
    private void initViews1(List<Json2CarBrandBean> json2CarBrandBeanList) {



        int size =  json2CarBrandBeanList.size();

        List<String> companyName = new ArrayList<>();
        String[] names  = new String[size];
        for (int i= 0; i< size;i++)
        {
            String name = json2CarBrandBeanList.get(i).getCarBrandName();
            names[i] = name;
        }

//        SourceDateList = filledData(getResources().getStringArray(R.array.date));
        SourceDateList = filledData(names);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(String [] date){

        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++)
        {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]"))
            {
                sortModel.setSortLetters(sortString.toUpperCase());
            }else
            {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr)
    {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = SourceDateList;
        }else{
            filterDateList.clear();
            for(SortModel sortModel : SourceDateList){
                String name = sortModel.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
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
