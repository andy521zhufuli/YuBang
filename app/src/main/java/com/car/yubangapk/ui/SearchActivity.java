package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.search.HotWordBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.search.HttpReqGerHotSearchWord;
import com.car.yubangapk.utils.RecordSQLiteOpenHelper;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.ForbiddenScrollGridView;
import com.andy.android.yubang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SearchActivity: 搜索
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-24
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private final static String TAG = "SearchActivity";
    private RelativeLayout          layout_search_topbar;
    private ForbiddenScrollGridView search_recommend_gridview;//热门搜索  搜索推荐
    private ListView history_search_listview;//搜索历史记录
    private View        clearView;
    private TextView    search_listview_clear;

    private ImageView img_back;//返回
    private EditText textView2;//搜索框
    private TextView tv_search;//搜索
    private RelativeLayout  history_search;


    SearchHistoryAdapter mSearchHitoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        mContext = this;
        findViews();

        mHistoryDBHelper = new RecordSQLiteOpenHelper(mContext);

        getHotWord();

        getSearchHistory();

        history_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if((adapterView.getCount() - 1) == position)
                {
                    toastMgr.builder.display("清除历史记录", 1);
                    deleteData();
                    mSearchHitoryAdapter.reFresh(new ArrayList<String>());
                }
                else
                {
                    toastMgr.builder.display("-->>" + position, 1);
                    TextView textView = (TextView) view.findViewById(R.id.item_search_history_textview);
                    textView2.setText(textView.getText().toString());
                    //执行搜索
                    searchHotWorld(textView.getText().toString());
                }
            }
        });

    }

    /**
     * 获取搜索历史
     */
    private void getSearchHistory()
    {
        queryData("");
    }

    private SQLiteDatabase db;
    RecordSQLiteOpenHelper mHistoryDBHelper;

    /**
     * 插入数据  每次数据点击的时候  就要插入
     */
    private void insertData(String tempName)
    {
        //首先判断这个字段在不在数据库里面
        if (!isWordInHistory(tempName))//数据库里面没有这个字段, 才放进去
        {
            db = mHistoryDBHelper.getWritableDatabase();
            db.execSQL("insert into records(name) values('" + tempName + "')");
            db.close();
        }
    }

    private boolean isWordInHistory(String word)
    {
        Cursor cursor = mHistoryDBHelper.getReadableDatabase().rawQuery("select id as _id,name from records where name like '%" + "" + "%' order by id desc ", null);
        Map<String, Integer> history = new HashMap<>();
        while (cursor.moveToNext())
        {
            String history_item = cursor.getString(cursor.getColumnIndex("name"));
            history.put(history_item, 1);
        }
        if (history.containsKey(word))
        {
            return true;
        }
        else
        {
            return false;
        }


    }



    /**
     * 模糊查询数据
     */
    private void queryData(String tempName)
    {
        Cursor cursor = mHistoryDBHelper.getReadableDatabase().rawQuery("select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        List<String> hist = new ArrayList<>();
        while (cursor.moveToNext())
        {
            String history_item = cursor.getString(cursor.getColumnIndex("name"));
            hist.add(history_item);
        }
        if (hist.size() == 0)
        {
            toastMgr.builder.display("history null", 1);
        }
        else
        {
            // 创建adapter适配器对象
            mSearchHitoryAdapter = new SearchHistoryAdapter(hist);
            history_search_listview.addFooterView(clearView);
            history_search_listview.setAdapter(mSearchHitoryAdapter);
        }

    }

    /**
     * 清空数据
     */
    private void deleteData()
    {
        db = mHistoryDBHelper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    /**
     * 拿到搜索热词
     */
    private void getHotWord() {
        HttpReqGerHotSearchWord hotSearchWord = new HttpReqGerHotSearchWord();
        hotSearchWord.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                //这里请求失败, 那就隐藏GridView  不让他显示
                setHotWordGridView(null, false);
            }

            @Override
            public void onSuccess(Object object) {
                List<HotWordBean> hotWordBeans = (List<HotWordBean>) object;
                //在GridView里面显示
                setHotWordGridView(hotWordBeans, true);
            }
        });
        hotSearchWord.getHotWord();
    }

    /**
     * 设置热词显示
     * @param hotWordBeans 热词列表
     * @param isHotWordDisplay 是否显示Gridview
     */
    private void setHotWordGridView(List<HotWordBean> hotWordBeans, boolean isHotWordDisplay)
    {
        if (isHotWordDisplay)
        {
            //显示
            //商品种类点击提示  热门搜索
            search_recommend_gridview.setAdapter(new SearchRecommendAdapter(hotWordBeans));
            search_recommend_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String word = (String) view.getTag();
                    toastMgr.builder.display("-->>" + position + "word = "  + word, 1);
                    //把点击的这个房在
                    hotWordClick(word, view, position);

                }
            });
        }
        else
        {
            //不显示
            search_recommend_gridview.setVisibility(View.GONE);
            layout_search_topbar.setVisibility(View.GONE);
        }
    }

    /**
     * 热词搜索点击
     * @param word
     * @param view
     * @param position
     */
    private void hotWordClick(String word, View view, int position)
    {
        textView2.setText(word);

        searchHotWorld(word);
    }

    private void searchHotWorld(String word)
    {

        String carType = Configs.getLoginedInfo(mContext).getCarType();
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        if ("".equals(carType))
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("提醒")
                    .setMsg("请添加车型")
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setPositiveButton("添加", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
            return;
        }

        //查询的时候, 才会加入
        insertData(word);

        Bundle bundle = new Bundle();
        bundle.putString("carType", carType);
        bundle.putString("userid", userid);
        bundle.putString("word", word);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, SearchResultProductPackageActivity.class);
        startActivity(intent);
    }


    private void findViews() {
        //热门搜索
        layout_search_topbar = (RelativeLayout) findViewById(R.id.layout_search_topbar);
        search_recommend_gridview = (ForbiddenScrollGridView) findViewById(R.id.search_recommand);
        //搜索历史
        history_search_listview = (ListView) findViewById(R.id.history_search_listview);
        img_back = (ImageView) findViewById(R.id.img_back);
        textView2 = (EditText) findViewById(R.id.textView2);
        tv_search = (TextView) findViewById(R.id.tv_search);

        history_search = (RelativeLayout) findViewById(R.id.history_search);

        //搜索历史记录
        clearView = LayoutInflater.from(this).inflate(R.layout.search_listview_footer, null);
        search_listview_clear = (TextView) clearView.findViewById(R.id.search_listview_clear);

        //设置监听器
        img_back.setOnClickListener(this);
        textView2.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //返回
            case R.id.img_back:
                finish();
                break;
            //搜索
            case R.id.tv_search:
                //执行搜索的操作
                String word = textView2.getText().toString().trim();
                if (!"".equals(word))
                {
                    searchHotWorld(word);
                }
                else
                {
                    toastMgr.builder.display("请输入搜索关键词", 1);
                }
                break;
            //文本框
            case R.id.textView2:
                //点击 调出输入法
                break;
        }
    }


    /**
    * 适配器的定义,要继承BaseAdapter
    */
    public class SearchRecommendAdapter extends BaseAdapter {

        List<HotWordBean> mSearchList;
        public SearchRecommendAdapter(List<HotWordBean> searchList)
        {
            this.mSearchList = searchList;
        }

        @Override
        public int getCount() {
            return mSearchList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSearchList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_search_recommand_gridview, null);
            TextView species = (TextView) view.findViewById(R.id.item_search_recommend_textview);
            species.setText(mSearchList.get(position).getWorld());
            view.setTag(mSearchList.get(position).getWorld());
            return view;
        }
    }

    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class SearchHistoryAdapter extends BaseAdapter
    {
        List<String> mHistorys;
        public SearchHistoryAdapter(List<String> list)
        {
            this.mHistorys = list;
        }
        public void reFresh(List<String> list)
        {
            this.mHistorys = list;
            notifyDataSetChanged();
        }
        public List<String> getList()
        {
            return mHistorys;
        }

        @Override
        public int getCount() {
            return mHistorys.size();
        }

        @Override
        public Object getItem(int position) {
            return mHistorys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_search_history_listview, null);
            TextView species = (TextView) view.findViewById(R.id.item_search_history_textview);
            species.setText(mHistorys.get(position));
            return view;
        }
    }

}
