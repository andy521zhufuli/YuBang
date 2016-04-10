package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.adapter.SectionAdapter;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.swipetoloadlayout.OnLoadMoreListener;
import com.car.yubangapk.swipetoloadlayout.OnRefreshListener;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

public class ListViewRefreshAcivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener
            ,SectionAdapter.OnChildItemClickListener<Character>
            ,SectionAdapter.OnChildItemLongClickListener<Character>{


    public static final String TAG = ListViewRefreshAcivity.class.getSimpleName();
    private Context mContext;
    private SwipeToLoadLayout swipeToLoadLayout;

    private ListView listView;

    private ViewPager viewPager;

    private ViewGroup indicators;

    private SectionAdapter mAdapter;
    private ListViewAdapter mListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_refresh_acivity);

        mContext = this;
        findViews();
    }

    private void findViews()
    {
        mAdapter = new SectionAdapter();
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");
        list1.add("1");

        mListViewAdapter = new ListViewAdapter(list1);
        mAdapter.setOnChildItemClickListener(this);
        mAdapter.setOnChildItemLongClickListener(this);

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView = (ListView) findViewById(R.id.swipe_target);

        listView.setAdapter(mListViewAdapter);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onChildItemClick(int groupPosition, int childPosition, Character character, View view) {

    }

    @Override
    public boolean onClickItemLongClick(int groupPosition, int childPosition, Character character, View view) {
        return false;
    }


    public class ListViewAdapter extends BaseAdapter {

        private List<String> mList;

        public ListViewAdapter(List<String> list)
        {
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {


            /* 1.手工创建对象 2.加载xml文件
                    */
            final ViewHolder holder;
            if (view == null) {
                holder =  new ViewHolder();
                view = View.inflate(mContext, R.layout.item_test_listview_data, null);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            return view;
        }

        class ViewHolder
        {
            ImageView produte_pic;//左侧图片

            LinearLayout productitem_changge_before;//商品名字  价格等等
            TextView maintenance_produte_name;//产品名字
            TextView        maintenance_img_1;//半合成
            TextView        produte_price;//产品价格
            TextView        produte_count;//产品数量
            TextView        maintenance_hecheng_1;//半合成

            LinearLayout    productitem_changge_after;//商品数量删除 加减 更换
            Button jiancount;//减商品数量
            Button          jiacount;//加商品数量
            TextView        count_tx;//商品数量显示
            RelativeLayout delete_bt;//商品删除
            RelativeLayout  change_bt;//商品更换
        }

    }

}
