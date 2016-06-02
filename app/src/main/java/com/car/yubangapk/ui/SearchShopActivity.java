package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.utils.RecordSQLiteOpenHelper;
import com.car.yubangapk.utils.ShopRecordSQLiteOpenHelper;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.ForbiddenScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchShopActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext;
    private final static String TAG = "SearchActivity";
    private RelativeLayout layout_search_topbar;
    private ForbiddenScrollGridView search_recommend_gridview;//热门搜索  搜索推荐
    private ListView history_search_listview;//搜索历史记录
    private View clearView;
    private TextView search_listview_clear;

    private ImageView img_back;//返回
    private EditText textView2;//搜索框
    private TextView tv_search;//搜索
    private RelativeLayout  history_search;

    //当前定位结果的经纬度
    private double latitude;
    private double longitude;


    SearchHistoryAdapter mSearchHitoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_shop);

        mContext = this;
        findViews();

        mHistoryDBHelper = new ShopRecordSQLiteOpenHelper(mContext, RecordSQLiteOpenHelper.SHOP_TYPE);

        getSearchHistory();


    }


    private void findViews()
    {

        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");


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


        /**
         * 搜索历史点击
         */
        history_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if ((adapterView.getCount() - 1) == position) {
                    toastMgr.builder.display("清除历史记录", 1);
                    deleteData();
                    mSearchHitoryAdapter.reFresh(new ArrayList<String>());
                } else {
                    toastMgr.builder.display("-->>" + position, 1);
                    TextView textView = (TextView) view.findViewById(R.id.item_search_history_textview);
                    textView2.setText(textView.getText().toString());
                    //执行搜索
                    searchHotWorld(textView.getText().toString());
                }
            }
        });
    }

    private SQLiteDatabase db;
    ShopRecordSQLiteOpenHelper mHistoryDBHelper;

    /**
     * 获取搜索历史
     */
    private void getSearchHistory()
    {
        queryData("");
    }

    /**
     * 插入数据  每次数据点击的时候  就要插入
     */
    private void insertData(String tempName)
    {
        //首先判断这个字段在不在数据库里面
        if (!isWordInHistory(tempName))//数据库里面没有这个字段, 才放进去
        {
            db = mHistoryDBHelper.getWritableDatabase();
            db.execSQL("insert into shoprecords(name) values('" + tempName + "')");
            db.close();
        }
    }

    private boolean isWordInHistory(String word)
    {
        Cursor cursor = mHistoryDBHelper.getReadableDatabase().rawQuery("select id as _id,name from shoprecords where name like '%" + "" + "%' order by id desc ", null);
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
        Cursor cursor = mHistoryDBHelper.getReadableDatabase().rawQuery("select id as _id,name from shoprecords where name like '%" + tempName + "%' order by id desc ", null);
        List<String> hist = new ArrayList<>();
        while (cursor.moveToNext())
        {
            String history_item = cursor.getString(cursor.getColumnIndex("name"));
            hist.add(history_item);
        }
        if (hist.size() == 0)
        {
            toastMgr.builder.display("没有搜索历史", 1);
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
     * 搜索
     * @param word
     */
    private void searchHotWorld(String word)
    {



        //查询的时候, 才会加入
        insertData(word);

        Bundle bundle = new Bundle();
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("latitude", latitude);
        bundle.putString("word", word);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, SearchShopResultActivity.class);
        startActivity(intent);
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
