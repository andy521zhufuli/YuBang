package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * RegisterDetailUploadIDCardActivity: 注册详情界面 上传身份证
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailUploadIDCardActivity extends BaseActivity implements View.OnClickListener
{
    private Context mContext;
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    private static final String TAG = "UploadFileSelectPicActivity";
    /** 获取到的图片路径 */
    private String picPath;

    private Intent lastIntent;

    private Uri photoUri;


    private TextView text_take_photo;//拍照
    private TextView text_pick_photo;//从相册选择照片
    private TextView text_cancle;//取消
    private ImageView   image_show1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_upload_idcard);

        mContext = this;

        findViews();
        getUploadType();
    }

    private void getUploadType() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int type = bundle.getInt("type");
        switch (type)
        {
            case RegisterDetailsActivity.IDCARD:
                text_pick_photo.setText("请上传身份证");
                image_show1.setImageResource(R.mipmap.register_upload_user_id_card);
                break;
            case RegisterDetailsActivity.DRIVER_PHOTO:
                text_pick_photo.setText("请上传司机照片");
                image_show1.setImageResource(R.mipmap.register_upload_driver_photo);
                break;
            case RegisterDetailsActivity.CAR_PHOTO:
                text_pick_photo.setText("请上传车辆照片");
                image_show1.setImageResource(R.mipmap.register_upload_car_photo);
                break;
            case RegisterDetailsActivity.DRIVER_LICENSE:
                text_pick_photo.setText("请上传驾驶证");
                image_show1.setImageResource(R.mipmap.register_upload_driver_lincense);
                break;
            case RegisterDetailsActivity.DRIVING_LICENSE:
                text_pick_photo.setText("请上传行驶证");
                image_show1.setImageResource(R.mipmap.upload_id_card);
                break;
            case RegisterDetailsActivity.TAXI_LICENSE:
                text_pick_photo.setText("请上传营运证");
                image_show1.setImageResource(R.mipmap.upload_id_card);
                break;
        }

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        text_take_photo = (TextView) findViewById(R.id.text_take_photo);//拍照
        text_pick_photo = (TextView) findViewById(R.id.text_pick_photo);//从相册选择照片
        text_cancle = (TextView) findViewById(R.id.text_cancle);//取消
        image_show1 = (ImageView) findViewById(R.id.image_show1);

        //设置监听器
        text_take_photo.setOnClickListener(this);
        text_pick_photo.setOnClickListener(this);
        text_cancle.setOnClickListener(this);
        lastIntent = getIntent();
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto()
    {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED))
        {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            /** ----------------- */
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        }
        else
        {
            toastMgr.builder.display("内存卡不存在", Toast.LENGTH_LONG);
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            doPhoto(requestCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data)
    {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
        {
            if (data == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo =
                { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null)
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        L.i(TAG, "imagePath = " + picPath);
        if (picPath != null
                && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG")))
        {

            toastMgr.builder.display("图片 = " + picPath, 1);
            lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
            setResult(Activity.RESULT_OK, lastIntent);
            finish();

        }
        else
        {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 交互点击
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //拍照
            case R.id.text_take_photo:
                takePhoto();
                break;
            //从相册选择
            case R.id.text_pick_photo:
                //pickPhoto();
                break;
            //取消
            case R.id.text_cancle:
                finish();
                break;
        }
    }



    public void uploadFile(String path)
    {

        File file = new File(picPath);

        if (!file.exists())
        {
            Toast.makeText(RegisterDetailUploadIDCardActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }




        Map<String, String> header = new HashMap<>();
        header.put("enctype", "multipart/form-data");

        String userid = (String) SPUtils.get(mContext, "userid", "");

        Map<String, String> params = new HashMap<>();
        params.put("userReq.fileCode", "0");
        params.put("userReq.userid", userid);

        OkHttpUtils.post()//
                .addFile("userReq.photo", file.getName(), file)//
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_FILE)
                .params(params)
                .build()//
                .execute(new MyStringCallback());
    }


    class MyStringCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("上传失败,网络错误", 1);
        }

        @Override
        public void onResponse(String response) {
            toastMgr.builder.display("Message = " + response, 1);
            L.d("RegisterDetailUploadIDCardActivity", response);
        }
    }


}

