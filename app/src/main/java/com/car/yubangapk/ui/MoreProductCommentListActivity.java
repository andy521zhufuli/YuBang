package com.car.yubangapk.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.productDetail.CommentPhotos;
import com.car.yubangapk.json.bean.productDetail.Json2ProductCommentsDetailbean;
import com.car.yubangapk.json.bean.productDetail.Rows;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetProductDetailIinfo;
import com.car.yubangapk.utils.toastMgr;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * MoreProductCommentListActivity: 更多商品评论的列表显示
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class MoreProductCommentListActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2, HttpReqCallback {

    private Context mContext;
    private static final String TAG = MoreProductCommentListActivity.class.getName();

    private ImageView                   img_back;//返回
    private PullToRefreshListView       pull_refresh_list;//
    private LinearLayout                nothing_layout;//没有更多评论了
    String mProductId;
    int mCurrentPage = 1;
    int mPageRow = 5;

    private HttpReqGetProductDetailIinfo mCommentDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_more_product_comment_list);

        mContext = this;
        findViews();
        setProductId();
        mCommentDetailInfo = new HttpReqGetProductDetailIinfo();
        mCommentDetailInfo.setCallback(this);

        firstGetMoreComment();

    }

    /**
     * 获取评论
     */
    private void firstGetMoreComment() {
        mCommentDetailInfo.getProductCommentDetail(getProductId(), mCurrentPage, mPageRow);
    }


    private void setProductId() {

        Bundle bundle = getIntent().getExtras();
        mProductId = bundle.getString("productId");
    }

    private String getProductId()
    {
        return mProductId;
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回
        pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);//
        nothing_layout = (LinearLayout) findViewById(R.id.nothing_layout);//没有更多评论了

        //
        img_back.setOnClickListener(this);
        initIndicator();
        pull_refresh_list.setOnRefreshListener(this);
    }


    private void initIndicator()
    {
        ILoadingLayout startLabels = pull_refresh_list
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = pull_refresh_list.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开加载更多...");// 下来达到一定距离时，显示的提示
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 下拉刷新
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    /**
     * 上拉加载
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        isFirstGetComment = false;
        firstGetMoreComment();
    }

    /**
     * 获取更多评论
     * @param errorCode
     * @param message
     */
    @Override
    public void onFail(int errorCode, String message) {
        toastMgr.builder.display(message,1);
        pull_refresh_list.onRefreshComplete();
    }

    @Override
    public void onSuccess(Object object) {
        Json2ProductCommentsDetailbean commentsDetailbean = (Json2ProductCommentsDetailbean) object;
        List<Rows> rows = commentsDetailbean.getRows();
        int  size = rows.size();

        if (isFirstGetComment)
        {
            mCommentList = rows;
            mCurrentPage += 1;
            //并且去显示listview
            mCommentAdapter = new CommentAdapter(mCommentList);
            pull_refresh_list.setAdapter(mCommentAdapter);
        }
        else
        {
            if (size > 0)
            {
                mCurrentPage += 1;
            }
            for (int i = 0; i < size; i++)
            {
                mCommentList.add(rows.get(i));
            }
            mCommentAdapter.refresh(mCommentList);
            pull_refresh_list.onRefreshComplete();
        }


    }


    List<Rows> mCommentList;

    private boolean isFirstGetComment = true;//  上啦加载的时候为 false
    private CommentAdapter mCommentAdapter;


    class CommentAdapter extends BaseAdapter
    {
        List<Rows> comments;


        public CommentAdapter(List<Rows> comments)
        {
            this.comments = comments;
        }
        public void refresh(List<Rows> comments)
        {
            this.comments = comments;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int i) {
            return comments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ViewHolder holder = null;
            if (view == null)
            {
                view = View.inflate(mContext, R.layout.item_product_comment, null);
                holder = new ViewHolder();
                holder.tv_comment_user_name = (TextView) view.findViewById(R.id.tv_comment_user_name);
                holder.rb_comment_grade = (RatingBar) view.findViewById(R.id.rb_comment_grade);
                holder.tv_comment_user_grade = (TextView) view.findViewById(R.id.tv_comment_user_grade);
                holder.tv_comment_content = (TextView) view.findViewById(R.id.tv_comment_content);
                holder.comment_pics_layout = (LinearLayout) view.findViewById(R.id.comment_pics_layout);
                holder.comment_pic1 = (ImageView) view.findViewById(R.id.comment_pic1);
                holder.comment_pic2 = (ImageView) view.findViewById(R.id.comment_pic2);
                holder.comment_pic3 = (ImageView) view.findViewById(R.id.comment_pic3);
                holder.comment_pic4 = (ImageView) view.findViewById(R.id.comment_pic4);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_comment_user_name.setText(comments.get(position).getPartnerName());

            holder.rb_comment_grade.setRating((float) comments.get(position).getStar());

            holder.tv_comment_user_grade.setText(comments.get(position).getTime());

            holder.tv_comment_content.setText(comments.get(position).getContent());

            //评论的照片
            List<CommentPhotos> commentPhotoses = comments.get(position).getCommentPhotoses();
            if (commentPhotoses == null || commentPhotoses.size() == 0)
            {
                holder.comment_pics_layout.setVisibility(View.GONE);//评论图片的布局 控制显示还是不显示
            }
            else
            {
                int  size = commentPhotoses.size();
                for (int index = 0; index < size; index++)
                {
                    String pathcode = commentPhotoses.get(index).getPathCode();
                    String photoName = commentPhotoses.get(index).getPhotoName();
                    String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoName;
                    if (index == 0)
                    {
                        ImageLoaderTools.getInstance(mContext).displayImage(url, holder.comment_pic1);
                    }
                    else if (index == 1)
                    {
                        ImageLoaderTools.getInstance(mContext).displayImage(url, holder.comment_pic2);
                    }
                    else if (index == 2)
                    {
                        ImageLoaderTools.getInstance(mContext).displayImage(url, holder.comment_pic3);
                    }
                    else if (index == 3)
                    {
                        ImageLoaderTools.getInstance(mContext).displayImage(url, holder.comment_pic4);
                    }
                }
            }

            return view;
        }


        class ViewHolder
        {
            TextView        tv_comment_user_name;
            RatingBar       rb_comment_grade;
            TextView        tv_comment_user_grade;
            TextView        tv_comment_content;
            LinearLayout    comment_pics_layout;
            ImageView       comment_pic1;
            ImageView       comment_pic2;
            ImageView       comment_pic3;
            ImageView       comment_pic4;
        }
    }


}
