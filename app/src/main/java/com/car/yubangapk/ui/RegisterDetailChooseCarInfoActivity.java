package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.Json2CarCompanyBean;
import com.car.yubangapk.json.formatJson.Json2CarBrand;
import com.car.yubangapk.json.formatJson.Json2CarCompany;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
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
 * RegisterDetailChooseCarInfoActivity: 注册详情  选择车辆信息 提交验证资料  选择车辆生产厂商
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailChooseCarInfoActivity extends BaseActivity {

    private static final String TAG = "RegisterDetailChooseCarInfoActivity";
    private Context mContext;

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private ImageView img_back;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;


    List<Json2CarCompanyBean> mJson2CarCompanyList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_info);

        mContext =this;


        initViews();

        //获取汽车生产厂商信息
        getCarCompanyInfo();

    }


    /**
     * 去拿车辆公司信息
     */
    private void getCarCompanyInfo()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName","clientGetCarCompany")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new GetCarCompanyCallback());
        L.i(TAG, "车辆公司信息 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                + "sqlName=clientGetCarCompany&dataReqModel.args.needTotal=needTotal");
    }
    class GetCarCompanyCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "汽车公司名字 json = " + response);
            Json2CarCompany json2CarCompany = new Json2CarCompany(response);
            List<Json2CarCompanyBean> json2CarCompanyBeans = json2CarCompany.getCarCompanyList();
            mJson2CarCompanyList = json2CarCompanyBeans;
            initViews1(mJson2CarCompanyList);
            //拿到信息 然后是显示出来
        }
    }


    /**
     * 去拿车的品牌信息
     */
    private void getCarBrandInfo(String companyId)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientGetCarBrand")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.carCompany", companyId)
                .build()
                .execute(new GetCarBrandCallback());
        L.i(TAG, "车辆品牌brand url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
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
            L.d(TAG, "汽车公司名字 json -= " + response);
            List<Json2CarBrandBean> json2CarBrandBeanList = new ArrayList<>();
            Json2CarBrand json2CarBrand = new Json2CarBrand(response);
            json2CarBrandBeanList = json2CarBrand.getBrandList();
            //拿到信息 然后是显示出来
            //显示到
        }
    }



    private void initViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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




        sortListView = (ListView) findViewById(R.id.country_lvcountry);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String name = mJson2CarCompanyList.get(position).getCompanyName();
                toastMgr.builder.display("您选择的是" + name, 1);
                String companyId = mJson2CarCompanyList.get(position).getId();
                SPUtils.put(mContext, "companyId", companyId);


                //去到子品牌
                Intent intent = new Intent();
                intent.setClass(RegisterDetailChooseCarInfoActivity.this, RegisterDetailChooseBrandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("companyId", RegisterDetailChooseCarInfoActivity.this.mJson2CarCompanyList.get(position).getId());
                SPUtils.put(mContext, "chooseedCompany", mJson2CarCompanyList.get(position).getCompanyName());
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });
    }


    /**
     * 这里需要优化
     * @param mJson2CarCompanyList
     */
    private void initViews1(List<Json2CarCompanyBean> mJson2CarCompanyList) {
        int size =  this.mJson2CarCompanyList.size();

        List<String> companyName = new ArrayList<>();
        String[] names  = new String[size];
        for (int i= 0; i< size;i++)
        {
            String name = this.mJson2CarCompanyList.get(i).getCompanyName();
            names[i] = name;
        }
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


}
