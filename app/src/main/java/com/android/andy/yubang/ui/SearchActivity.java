package com.android.andy.yubang.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.andy.yubang.view.ForbiddenScrollGridView;
import com.andy.android.yubang.R;

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
    private ForbiddenScrollGridView search_recommend_gridview;//热门搜索  搜索推荐
    private ListView history_search_listview;//搜索历史记录

    private ImageView img_back;//返回
    private EditText textView2;//搜索框
    private TextView tv_search;//搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        mContext = this;
        findViews();

        //商品种类点击提示  热门搜索
        search_recommend_gridview.setAdapter(new SearchRecommend());
        search_recommend_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SearchActivity.this, "-->>" + position, Toast.LENGTH_LONG).show();
            }
        });
        //搜索历史记录
        history_search_listview.setAdapter(new SearchHistory());
        history_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SearchActivity.this, "-->>" + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void findViews() {
        //热门搜索
        search_recommend_gridview = (ForbiddenScrollGridView) findViewById(R.id.search_recommand);
        //搜索历史
        history_search_listview = (ListView) findViewById(R.id.history_search_listview);
        img_back = (ImageView) findViewById(R.id.img_back);
        textView2 = (EditText) findViewById(R.id.textView2);
        tv_search = (TextView) findViewById(R.id.tv_search);

        //设置监听器
        img_back.setOnClickListener(this);
        textView2.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    // 定义图片的资源
    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

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
    public class SearchRecommend extends BaseAdapter {

        public SearchRecommend() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
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
            species.setText(strings[position]);
            return view;
        }
    }

    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class SearchHistory extends BaseAdapter {

        public SearchHistory() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
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
            species.setText(strings[position]);
            return view;
        }
    }

}
