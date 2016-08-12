package com.tec.android.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.android.R;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;

import org.w3c.dom.Text;

/**
 * 选择图片
 */
public class UploadFileSelectPicActivity extends BaseActivity implements View.OnClickListener{


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload_file_select_pic);

        mContext = this;

        findViews();
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        text_take_photo = (TextView) findViewById(R.id.text_take_photo);//拍照
        text_pick_photo = (TextView) findViewById(R.id.text_pick_photo);//从相册选择照片
        text_cancle = (TextView) findViewById(R.id.text_cancle);//取消

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
                pickPhoto();
                break;
            //取消
            case R.id.text_cancle:
                finish();
                break;
        }
    }
}
