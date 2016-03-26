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

import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover_recommend_add_partner);

        findViews();

        mContext = this;

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
                choosePicMetod();
                break;
            //保存店铺靓照
            case R.id.add_photo_save_shop:
                break;

            //添加营业执照
            case R.id.add_photo_yingyezheng:
                //打开选择照片的方式
                choosePicMetod();
                break;
            //保存店铺靓照
            case R.id.add_photo_save_yingjiezheng:
                break;

            //添加身份证
            case R.id.add_photo_id_card:
                //打开选择照片的方式
                choosePicMetod();
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
                break;
        }
    }

    /**
     * 打开选择照片的界面
     */
    private void choosePicMetod()
    {
        Intent intent = new Intent();
        intent.setClass(mContext, UploadFileSelectPicActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 从选择图片界面回来
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            //拿到图片的路径  解析成bitmap
            String imagepath = data.getStringExtra(UploadFileSelectPicActivity.KEY_PHOTO_PATH);
            Bitmap gettedImage = getSmallBitmap(imagepath);
            String[] name = imagepath.split("/");
            String imagename = name[name.length - 1];
            L.i("退货 imagename = " + imagename);
            toastMgr.builder.display("您选择上传的图片是:-->" + imagename, 0);
        }
    }


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
