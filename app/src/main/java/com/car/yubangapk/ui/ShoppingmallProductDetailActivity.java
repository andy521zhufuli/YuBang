package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.productDetail.CommentPhotos;
import com.car.yubangapk.json.bean.productDetail.Json2ProductCommentsDetailbean;
import com.car.yubangapk.json.bean.productDetail.Json2ProductDetailInfoBean;
import com.car.yubangapk.json.bean.productDetail.Rows;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetProductDetailIinfo;
import com.car.yubangapk.view.CustomProgressDialog;


import java.util.List;

/**
 * ShoppingmallProductDetailActivity: 商品详情页面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */


public class ShoppingmallProductDetailActivity extends BaseActivity implements View.OnClickListener, HttpReqCallback{

    private Context mContext;
    private static final String TAG = ShoppingmallProductDetailActivity.class.getName();

    private ImageView           img_back;//返回
    private ImageView           product_pic;//图片  默认取第一张显示
    private TextView            product_details_name;//商品的名字
    private TextView            product_price;//商品的价格
    private TextView            product_selled_count;//商品已售数量
    private TextView            product_comment_num;//商品评价人数

    private RelativeLayout      product_detail_layout;//产品详情
    private RelativeLayout      product_detail_parameter;//产品详情的产品参数

    private RelativeLayout      rl_comment_layout_tip;//正在加载评价信息
    private ProgressBar         comment__progressBar;
    private TextView            comment_loading_tips;//

    private LinearLayout        comment_layout;//整个评论布局
    private TextView            tv_comment_user_phone;//评论人电话
    private RatingBar           rb_comment_star;//评分等级
    private TextView            tv_comment_user_date;//评论日期
    private TextView            tv_comment_content;//评论内容
    private LinearLayout        comment_pic_layout;//评论图片的布局 控制显示还是不显示
    private ImageView           comment_pic1;//评论图片
    private ImageView           comment_pic2;//评论图片
    private ImageView           comment_pic3;//评论图片
    private ImageView           comment_pic4;//评论图片
    private LinearLayout        comment_bottom_load_more;//加载更多评论



    private CustomProgressDialog mProgress;
    private int LOADING_PRODUCT_DETAIL = 1;
    private int LOADING_COMMENT_DETAIL = 2;
    private int LOADING_DEFAULT = 0;
    private int mLoadingType1 = 0; //type =1  正在加载产品详情  type = 2 正在加载评论
    private int mLoadingType2 = 0; //type =1  正在加载产品详情  type = 2 正在加载评论

    private String mProductId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_shoppingmall_product_detail);
        mContext = this;

        findViews();
        mProgress = new CustomProgressDialog(mContext);

        setProductId();

        getProductDetailInfo();

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
     * 获取产品详情
     */
    private void getProductDetailInfo() {

        mLoadingType1 = LOADING_PRODUCT_DETAIL;
        mProgress = mProgress.show(mContext, "正在加载...", false, null);
        HttpReqGetProductDetailIinfo getProductDetailIinfo = new HttpReqGetProductDetailIinfo();
        getProductDetailIinfo.setCallback(this);
        getProductDetailIinfo.getProductDetail(getProductId());
    }

    private void getProductCommentsInfo()
    {
        mLoadingType2 = LOADING_COMMENT_DETAIL;
        HttpReqGetProductDetailIinfo httpReqGetProductDetailIinfo = new HttpReqGetProductDetailIinfo();
        httpReqGetProductDetailIinfo.setCallback(this);
        httpReqGetProductDetailIinfo.getProductCommentDetail(getProductId(), 1, 1);
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back = (ImageView) findViewById(R.id.img_back);//返回

        product_pic = (ImageView) findViewById(R.id.product_pic);//图片  默认取第一张显示
        product_details_name = (TextView) findViewById(R.id.product_details_name);//商品的名字
        product_price = (TextView) findViewById(R.id.product_price2);//商品的价格
        product_selled_count = (TextView) findViewById(R.id.product_selled_count);//商品已售数量
        product_comment_num = (TextView) findViewById(R.id.product_comment_num);//商品评价人数

        product_detail_layout = (RelativeLayout) findViewById(R.id.product_detail_layout);;//产品详情
        product_detail_parameter = (RelativeLayout) findViewById(R.id.product_detail_parameter);;//产品详情的产品参数

        //评论相关
        rl_comment_layout_tip = (RelativeLayout) findViewById(R.id.rl_comment_layout_tip);//正在加载评价信息
        comment__progressBar = (ProgressBar) findViewById(R.id.comment__progressBar);
        comment_loading_tips = (TextView) findViewById(R.id.comment_loading_tips);//

        comment_layout = (LinearLayout) findViewById(R.id.comment_layout);
        tv_comment_user_phone = (TextView) findViewById(R.id.tv_comment_user_phone);//评论人电话
        rb_comment_star = (RatingBar) findViewById(R.id.rb_comment_star);//评分等级
        tv_comment_user_date = (TextView) findViewById(R.id.tv_comment_user_date);//评论日期
        tv_comment_content = (TextView) findViewById(R.id.tv_comment_content);//评论内容
        comment_pic_layout = (LinearLayout) findViewById(R.id.comment_pic_layout);//评论图片的布局 控制显示还是不显示
        comment_pic1 = (ImageView) findViewById(R.id.comment_pic1);//评论图片
        comment_pic2 = (ImageView) findViewById(R.id.comment_pic2);//评论图片
        comment_pic3 = (ImageView) findViewById(R.id.comment_pic3);//评论图片
        comment_pic4 = (ImageView) findViewById(R.id.comment_pic4);//评论图片

        comment_bottom_load_more = (LinearLayout) findViewById(R.id.comment_bottom_load_more);//加载更多评论

        //
        img_back.setOnClickListener(this);
        product_detail_layout.setOnClickListener(this);
        rl_comment_layout_tip.setOnClickListener(this);
        comment_bottom_load_more.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.product_detail_layout:
                //产品详情
                displayProductDetailInfoWebview();

                break;
            case R.id.rl_comment_layout_tip:
                //显示正在加载
                getProductCommentsInfo();
                break;
            case R.id.comment_bottom_load_more:
                //加载完成的时候   这里评论就显示, 并且正在加载字样隐藏
                displayMoreCommentInfo();
                break;
        }
    }

    /**
     * 显示产品详情网页
     */
    private void displayProductDetailInfoWebview() {

        Intent intent = new Intent();
        intent.setClass(mContext, ProductDetailInfoWebviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("productId", getProductId());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    /**
     * 去另一个页面, 显示更多的评论信息
     */
    private void displayMoreCommentInfo() {
        Intent intent = new Intent();
        Bundle bundle =  new Bundle();
        bundle.putString("productId", getProductId());
        intent.putExtras(bundle);
        intent.setClass(mContext,MoreProductCommentListActivity.class);
        startActivity(intent);

    }

    /**
     * 网络请求返回 失败
     * @param errorCode
     * @param message
     */
    @Override
    public void onFail(int errorCode, String message) {
        mProgress.dismiss();
        if (mLoadingType1 == LOADING_PRODUCT_DETAIL)
        {
            //加载产品详情
            mLoadingType1 = LOADING_DEFAULT;
        }
        else if (mLoadingType2 == LOADING_COMMENT_DETAIL)
        {
            //加载评论详情
            mLoadingType2 = LOADING_DEFAULT;
            setLoadingCommentTips("点击重新加载评论信息");
        }
    }

    /**
     * 网络请求返回成功
     * @param object
     */
    @Override
    public void onSuccess(Object object) {
        mProgress.dismiss();
        if (mLoadingType1 == LOADING_PRODUCT_DETAIL)
        {
            //加载产品详情
            mLoadingType1 = LOADING_DEFAULT;
            Json2ProductDetailInfoBean productdetail = (Json2ProductDetailInfoBean) object;
            showProductDetail(productdetail);

            //加载评论
            getProductCommentsInfo();
        }
        else if (mLoadingType2 == LOADING_COMMENT_DETAIL)
        {
            //加载评论详情
            mLoadingType2 = LOADING_DEFAULT;
            Json2ProductCommentsDetailbean commentsDetailbean = (Json2ProductCommentsDetailbean) object;
            if (commentsDetailbean.getTotal() == 0)
            {
                setLoadingCommentTips("该商品暂无评论信息");
                //不可点击
                rl_comment_layout_tip.setClickable(false);

            }
            else
            {
                showCommentDetail(commentsDetailbean);
            }

        }
    }

    private void setLoadingCommentTips(String tips)
    {
        rl_comment_layout_tip.setVisibility(View.VISIBLE);
        comment__progressBar.setVisibility(View.GONE);
        comment_loading_tips.setText(tips);//
        comment_layout.setVisibility(View.GONE);
    }

    /**
     * 显示评论详情
     * @param commentsDetailbean
     */
    private void showCommentDetail(Json2ProductCommentsDetailbean commentsDetailbean) {
        rl_comment_layout_tip.setVisibility(View.GONE);
        comment_layout.setVisibility(View.VISIBLE);


        List<Rows> rows = commentsDetailbean.getRows();
        Rows row = null;
        if (rows.size() > 0)
        {
            row = rows.get(0);//只拿第一个评论
        }
        else
        {
            setLoadingCommentTips("该商品暂无评论信息");
            //不可点击
            rl_comment_layout_tip.setClickable(false);
        }
        tv_comment_user_phone.setText(row.getPartnerName());//评论人电话
        double star = row.getStar();

        rb_comment_star.setRating((float)star);//评分等级
        tv_comment_user_date.setText(row.getTime());//评论日期
        tv_comment_content.setText(row.getContent());//评论内容

        //评论的照片
        List<CommentPhotos> commentPhotoses = row.getCommentPhotoses();
        if (commentPhotoses.size() == 0)
        {
            comment_pic_layout.setVisibility(View.GONE);//评论图片的布局 控制显示还是不显示

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
                    ImageLoaderTools.getInstance(mContext).displayImage(url, comment_pic1);
                }
                else if (index == 1)
                {
                    ImageLoaderTools.getInstance(mContext).displayImage(url, comment_pic2);
                }
                else if (index == 2)
                {
                    ImageLoaderTools.getInstance(mContext).displayImage(url, comment_pic3);
                }
                else if (index == 3)
                {
                    ImageLoaderTools.getInstance(mContext).displayImage(url, comment_pic4);
                }
            }
        }
    }

    /**
     * 显示上平详情
     * @param productdetail
     */
    private void showProductDetail(Json2ProductDetailInfoBean productdetail) {
        String productId = productdetail.getProductId();
        String productPhoto = productdetail.getProductPhoto();
        String pathCode = productdetail.getPathCode();
        String productName = productdetail.getProductName();
        double productPrice = productdetail.getProductPrice();
        int    buyNum = productdetail.getBuyNum();

        //显示图片
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathCode + "&fileReq.fileName=" + productPhoto;
        ImageLoaderTools.getInstance(mContext).displayImage(url, product_pic);
        product_details_name.setText(productName);
        product_price.setText(productPrice + "");
        product_selled_count.setText("已售数量" + buyNum);
        product_comment_num.setVisibility(View.GONE);

    }


}
