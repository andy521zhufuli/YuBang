package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2RecommendPartnerUploadPhotosBean;
import com.car.yubangapk.json.bean.Json2RecommendShopDetailInfoBean;
import com.car.yubangapk.json.bean.RecommendPartnerShopBaseInfo;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqRecommendDetailInfo;
import com.car.yubangapk.network.myHttpReq.HttpReqRecommendPartnerUploadPhotos;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiscoverRecommendAddPartnerActivity: 发现界面的新增合伙人
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-02
 */
public class DiscoverRecommendAddPartnerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView   img_back;
    private ImageView   add_photo_shop;//添加店铺靓照
    private Button      add_photo_save_shop;//保存店铺靓照

    private ImageView   add_photo_yingyezheng;//添加营业执照
    private Button      add_photo_save_yingjiezheng;//保存店铺靓照

    private ImageView   add_photo_id_card;//添加身份证
    private Button      add_photo_save_id_card;//保存身份证

    private ImageView   add_photo_shop_location;//添加店铺位置
    private Button      add_photo_save_shop_location;//保存店铺位置

    private Button      btn_save;//保存

    private Context     mContext;


    RecommendPartnerShopBaseInfo mShopBaseInfo;//基本信息传过来的基本信息
    Json2RecommendShopDetailInfoBean mShopDetailInfoBean;//基本信息上传完成之后的shopid

    CustomProgressDialog mProgress;

    HttpReqRecommendPartnerUploadPhotos mUpLoadPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover_recommend_add_partner);

        findViews();

        mContext = this;


        Bundle bundle = getIntent().getExtras();
        mShopBaseInfo = (RecommendPartnerShopBaseInfo) bundle.getSerializable("shopBaseInfo");

        mProgress = new CustomProgressDialog(mContext);

        mUpLoadPhotos = new HttpReqRecommendPartnerUploadPhotos(mContext);
        mUpLoadPhotos.setCallback(new UpLoadPhoto());

    }

    private void findViews() {


        img_back = (ImageView) findViewById(R.id.img_back);

        add_photo_shop = (ImageView) findViewById(R.id.add_photo_shop);//添加店铺靓照
        add_photo_save_shop = (Button) findViewById(R.id.add_photo_save_shop);//保存店铺靓照

        add_photo_yingyezheng = (ImageView) findViewById(R.id.add_photo_yingyezheng);//添加营业执照
        add_photo_save_yingjiezheng = (Button) findViewById(R.id.add_photo_save_yingjiezheng);//保存店铺靓照

        add_photo_id_card = (ImageView) findViewById(R.id.add_photo_id_card);//添加身份证
        add_photo_save_id_card = (Button) findViewById(R.id.add_photo_save_id_card);//保存身份证

        add_photo_shop_location = (ImageView) findViewById(R.id.add_photo_shop_location);//添加店铺位置
        add_photo_save_shop_location = (Button) findViewById(R.id.add_photo_save_shop_location);//保存店铺位置

        btn_save = (Button) findViewById(R.id.btn_save);//保存

        /**
         * 添加监听器
         */
        img_back.setOnClickListener(this);
        add_photo_shop.setOnClickListener(this);//添加店铺靓照
        add_photo_save_shop.setOnClickListener(this);//保存店铺靓照
        add_photo_yingyezheng.setOnClickListener(this);//添加营业执照
        add_photo_save_yingjiezheng.setOnClickListener(this);//保存店铺靓照
        add_photo_id_card.setOnClickListener(this);//添加身份证
        add_photo_save_id_card.setOnClickListener(this);//保存身份证
        add_photo_shop_location.setOnClickListener(this);//添加店铺位置
        add_photo_save_shop_location.setOnClickListener(this);//保存店铺位置
        btn_save.setOnClickListener(this);//保存

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //返回
            case R.id.img_back:
                finish();
                break;
            //添加店铺靓照
            case R.id.add_photo_shop:
                //打开选择照片的方式
                choosePicMetod(REQUEST_SHOP_PHOTO);
                break;
            //保存店铺靓照
            case R.id.add_photo_save_shop:
                break;

            //添加营业执照
            case R.id.add_photo_yingyezheng:
                //打开选择照片的方式
                choosePicMetod(REQUEST_YINGYE_ZHIZHAO);
                break;
            //保存店铺靓照
            case R.id.add_photo_save_yingjiezheng:
                break;

            //添加身份证
            case R.id.add_photo_id_card:
                //打开选择照片的方式
                choosePicMetod(REQUEST_ID_CARD);
                break;
            //保存身份证
            case R.id.add_photo_save_id_card:
                break;
            //添加店铺位置
            case R.id.add_photo_shop_location:
                break;
            //保存店铺位置
            case R.id.add_photo_save_shop_location:
                break;
            //保存
            case R.id.btn_save:
                uploadShopBaseInfo();
                break;
        }
    }

    /**
     * 上传所有信息
     */
    private void uploadShopBaseInfo()
    {

        if (mGetedPhotoMap.size() != 3)
        {
            toastMgr.builder.display("信息不完整...", 1);
            return;
        }

        mProgress = mProgress.show(mContext, "正在上传店铺基本信息...", false, null);
        //1. 首先上传之前的基本信息
        HttpReqRecommendDetailInfo putDetailInfo = new HttpReqRecommendDetailInfo();
        putDetailInfo.setListener(new PutInfo());
        putDetailInfo.putRecommendShopInfo(mShopBaseInfo);
    }

    class PutInfo implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {

            mProgress.dismiss();

            if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display("网络错误, 推荐合伙人失败", 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                NotLogin.gotoLogin(DiscoverRecommendAddPartnerActivity.this);
            }
            else
            {
                toastMgr.builder.display(message + "推荐合伙人失败", 1);
            }
        }

        @Override
        public void onSuccess(Object object) {
            mProgress.setMessage("正在上传店铺照片...");
            mShopDetailInfoBean = (Json2RecommendShopDetailInfoBean) object;

            uploadShopPhotos(mGetedPhotoMap.get(REQUEST_SHOP_PHOTO), SHOP_PHOTO_CODE,mShopDetailInfoBean.getShopId());

        }
    }
    private String BASE_INFO_CODE = "-1";
    private String SHOP_PHOTO_CODE = "0";
    private String SHOP_ID_CODE = "1";
    private String ID_CARD_CODE = "2";

    private String currentUploadType;//当前是谁正在上传
    /**
     * 上传店铺照片信息
     * @param
     * @param
     * @param
     */
    private void uploadShopPhotos(String filepath, String filecode, String shopid) {


        mUpLoadPhotos.recommendPartnerUploadPhotos(filepath, filecode, shopid);
    }

    class UpLoadPhoto implements HttpReqRecommendPartnerUploadPhotos.UploadShopPhotosCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            mProgress.dismiss();

            if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
            {
                toastMgr.builder.display("网络错误, 推荐合伙人失败", 1);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                NotLogin.gotoLogin(DiscoverRecommendAddPartnerActivity.this);
            }
            else
            {
                toastMgr.builder.display(message + "推荐合伙人失败", 1);
            }
        }

        @Override
        public void onSuccess(Json2RecommendPartnerUploadPhotosBean bean, String fileCode) {
            if (fileCode.equals(SHOP_PHOTO_CODE))
            {
                //店铺照片上传成功
                mProgress.setMessage("正在上传营业执照...");
                uploadShopPhotos(mGetedPhotoMap.get(REQUEST_YINGYE_ZHIZHAO), SHOP_ID_CODE, mShopDetailInfoBean.getShopId());
            }
            else if (fileCode.equals(SHOP_ID_CODE))
            {
                //营业执照上传成功
                mProgress.setMessage("正在上传身份证...");
                uploadShopPhotos(mGetedPhotoMap.get(REQUEST_ID_CARD), ID_CARD_CODE,mShopDetailInfoBean.getShopId());
            }
            else if (fileCode.equals(ID_CARD_CODE))
            {
                mProgress.dismiss();
                toastMgr.builder.display("恭喜您,推荐合伙人成功", 1);
                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setTitle("提示")
                        .setMsg("恭喜您,推荐合伙人成功")
                        .setCancelable(false)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoDiscoverPage();
                            }
                        })
                        .show();
            }
        }
    }

    private void gotoDiscoverPage()
    {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("otherActivity", "addPartner");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /**
     * 打开选择照片的界面
     */
    private void choosePicMetod(int method)
    {
        Intent intent = new Intent();
        intent.setClass(mContext, UploadFileSelectPicActivity.class);
        startActivityForResult(intent, method);
    }

    private int REQUEST_SHOP_PHOTO = 0X11;
    private int REQUEST_YINGYE_ZHIZHAO = 0X22;
    private int REQUEST_ID_CARD         = 0X33;

    /**
     * 从选择图片界面回来
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            //拿到图片的路径  解析成bitmap
            String imagepath = data.getStringExtra(UploadFileSelectPicActivity.KEY_PHOTO_PATH);
            Bitmap gettedImage = getSmallBitmap(imagepath);
            String[] name = imagepath.split("/");
            String imagename = name[name.length - 1];
            toastMgr.builder.display("您选择上传的图片是:-->" + imagename, 0);
            if (requestCode == REQUEST_SHOP_PHOTO)
            {
                add_photo_shop.setImageBitmap(gettedImage);
                mGetedPhotoMap.put(REQUEST_SHOP_PHOTO, imagepath);
            }
            else if (requestCode == REQUEST_YINGYE_ZHIZHAO)
            {
                add_photo_yingyezheng.setImageBitmap(gettedImage);
                mGetedPhotoMap.put(REQUEST_YINGYE_ZHIZHAO, imagepath);
            }
            else if (requestCode == REQUEST_ID_CARD)
            {
                add_photo_id_card.setImageBitmap(gettedImage);
                mGetedPhotoMap.put(REQUEST_ID_CARD, imagepath);
            }
        }
    }



    private Map<Integer, String> mGetedPhotoMap = new HashMap<>();

    /**
     * 压缩图片
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    //计算图片的缩放值
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
