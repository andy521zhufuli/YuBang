package com.car.yubangapk.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.bean.search.SearchResultProductPackageRows;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.search.HttpReqGetSearchReuslt;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class SearchResultProductPackageActivity extends BaseActivity implements HttpReqCallback, PullToRefreshBase.OnRefreshListener2{


    private Context                 mContext;
    private final static String     TAG = "SearchResultProductPackageActivity";
    ImageView                       img_back;//返回
    PullToRefreshListView           search_result_pull_refresh_list;//
    HttpReqGetSearchReuslt          mHttpReqGetSearchReuslt;
    SearchResultListViewAdapter     mListViewAdapter;

    boolean                         isFirstRequest = true;

    String                          mCarType;
    String                          mHotWord;
    String                          mUserid;
    int                             mCurrentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_result_product_package);
        mContext = this;

        findViews();

        Bundle bundle = getIntent().getExtras();
        mCarType = bundle.getString("carType");
        mHotWord = bundle.getString("word");
        mUserid = bundle.getString("userid");

        mHttpReqGetSearchReuslt = new HttpReqGetSearchReuslt();
        mHttpReqGetSearchReuslt.setCallback(this);
        //第一次请求
        getSearchResultByWord(mCarType, mCurrentPage + "", "5", mHotWord, mUserid);


    }

    /**
     *
     * @param word
     * @param carType
     */
    private void getSearchResultByWord(String carType, String page, String row, String word, String userid) {
        mHttpReqGetSearchReuslt.getSearchResult(carType, page, row, word, userid);
    }

    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回
        search_result_pull_refresh_list = (PullToRefreshListView) findViewById(R.id.search_result_pull_refresh_list);//

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onFail(int errorCode, String message) {
        toastMgr.builder.display(message, 1);
        if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
        {
            NotLogin.gotoLogin(SearchResultProductPackageActivity.this);
        }
    }

    @Override
    public void onSuccess(Object object) {
        List<SearchResultProductPackageRows> rows = (List<SearchResultProductPackageRows>) object;
        mCurrentPage++;
        if (isFirstRequest)
        {
            initIndicator();
            mListViewAdapter = new SearchResultListViewAdapter(rows);
            search_result_pull_refresh_list.setOnRefreshListener(this);
            search_result_pull_refresh_list.setAdapter(mListViewAdapter);

            isFirstRequest = false;
        }
        else
        {
            search_result_pull_refresh_list.onRefreshComplete();
            List<SearchResultProductPackageRows> list = mListViewAdapter.getList();
            for (SearchResultProductPackageRows row : rows) {
                list.add(row);
            }
            mListViewAdapter.refresh(list);
        }


    }


    private void initIndicator()
    {
        ILoadingLayout startLabels = search_result_pull_refresh_list
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = search_result_pull_refresh_list.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开加载更多...");// 下来达到一定距离时，显示的提示
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        //没有下啦刷新的功能, 还有上啦加载
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getSearchResultByWord(mCarType, mCurrentPage + "", "5", mHotWord, mUserid);
    }


    class SearchResultListViewAdapter extends BaseAdapter
    {

        private List<SearchResultProductPackageRows> repairServiceList;

        public SearchResultListViewAdapter(List<SearchResultProductPackageRows> list)
        {
            this.repairServiceList = list;
        }

        public void refresh(List<SearchResultProductPackageRows> list)
        {
            this.repairServiceList = list;
            notifyDataSetChanged();
        }

        public List<SearchResultProductPackageRows> getList()
        {
            return this.repairServiceList;
        }


        @Override
        public int getCount() {
            return repairServiceList.size();
        }

        @Override
        public Object getItem(int position) {
            return repairServiceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

             /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_search_result_listview, null);

                holder.pp_name = (TextView) view.findViewById(R.id.pp_name);
                holder.result_item = (RelativeLayout) view.findViewById(R.id.result_item);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            //String pathcode = repairServiceList.get(position).getPathCode();
            //String photoname = repairServiceList.get(position).getPhotoName();
            //String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            holder.pp_name.setText(repairServiceList.get(position).getPackageName());
            //ImageLoaderTools.getInstance(mContext).displayImage(url, holder.repair_service_pic);
            holder.result_item.setOnClickListener(new OnItemClick(repairServiceList.get(position)));
            return view;
        }

        class ViewHolder
        {
            TextView        pp_name;//名字
            RelativeLayout  result_item;
        }
    }

    class OnItemClick implements View.OnClickListener
    {
        SearchResultProductPackageRows row;

        public OnItemClick(SearchResultProductPackageRows _row)
        {
            this.row = _row;
        }

        @Override
        public void onClick(View view) {
            String repairService = row.getRepairService();
            Bundle bundle = new Bundle();
            bundle.putString(Configs.serviceId, repairService);
            bundle.putString(Configs.mCarType, mCarType);
            bundle.putString(Configs.FROM, Configs.FROM_SHOPPINGMALL);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(SearchResultProductPackageActivity.this, ShoppingMallGoodsActivity.class);
            SearchResultProductPackageActivity.this.startActivity(intent);
        }
    }

}
