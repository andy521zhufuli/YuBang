package com.tec.android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.android.R;
import com.tec.android.network.UploadImgReqHttp;
import com.tec.android.utils.L;
import com.tec.android.view.ActionSheetDialog;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.CustomProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 退货  上传图片    添加一张, 上传一张  拿到url, 保存
 */
public class ReturnOfGoodsActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext;
    private static final String TAG = "ReturnOfGoodsActivity";



    private TextView        return_of_goods_order_id_textview;
    private RelativeLayout  return_of_goods_choose_reasons_layout;      //点击选择退货原因
    private TextView        return_of_goods_choosed_reason_textview;    //选择好的退货原因
    private TextView        return_of_goods_upload_pic_imageview;       //选择上传图片
    private Button          return_of_goods_choose_submit_apply_button; //确认按钮
    private TextView        return_of_goods_should_know_textview;       //退货须知
    private GridView        return_of_goods_uploaded_image;             //上传图片的现实1
    private TextView        return_of_goods_order_id_editview;          //订单号

    private List<Bitmap> mUploadImages;
    private UploadPicAdapter uploadPicAdapter;

    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_return_of_goods);

        mContext = this;
        //初始化控件
        findViews();
        //拿到订单号
        return_of_goods_order_id_editview.setText("T111111111111");
        customProgressDialog = new CustomProgressDialog(mContext);

        mUploadImages = new ArrayList<>();
        Resources res = getResources();
        BitmapDrawable bd = (BitmapDrawable) res.getDrawable(R.mipmap.item_upload_pic_addpic_focused);
        Bitmap first = bd.getBitmap();
        mUploadImages.add(first);
        uploadPicAdapter = new UploadPicAdapter(mUploadImages);

        return_of_goods_uploaded_image.setAdapter(uploadPicAdapter);


    }

    /**
     * 初始化控件
     */
    private void findViews() {
        return_of_goods_order_id_textview = (TextView) findViewById(R.id.return_of_goods_order_id_textview);
        return_of_goods_choose_reasons_layout = (RelativeLayout) findViewById(R.id.return_of_goods_choose_reasons_layout);
        return_of_goods_choosed_reason_textview = (TextView) findViewById(R.id.return_of_goods_choosed_reason_textview);
        return_of_goods_upload_pic_imageview = (TextView) findViewById(R.id.return_of_goods_upload_pic_imageview);
        return_of_goods_choose_submit_apply_button = (Button) findViewById(R.id.return_of_goods_choose_submit_apply_button);
        return_of_goods_should_know_textview = (TextView) findViewById(R.id.return_of_goods_should_know_textview);
        //Gridview
        return_of_goods_uploaded_image = (GridView) findViewById(R.id.return_of_goods_uploaded_image);
        return_of_goods_order_id_editview = (TextView) findViewById(R.id.return_of_goods_order_id_editview);

        //设置监听器
        return_of_goods_choose_reasons_layout.setOnClickListener(this);
        return_of_goods_upload_pic_imageview.setOnClickListener(this);
        return_of_goods_choose_submit_apply_button.setOnClickListener(this);
        return_of_goods_should_know_textview.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < mUploadImages.size(); i++)
        {
            //释放资源
            mUploadImages.get(i).recycle();
        }


    }

    /**
     * 界面点击交互
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //点击选择退货原因
            case R.id.return_of_goods_choose_reasons_layout:
                new ActionSheetDialog(mContext).builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("退货原因")
                        .addSheetItem("我不想买了", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        return_of_goods_choosed_reason_textview.setText("我不想买了");
                                    }
                                })
                        .addSheetItem("点错了", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        return_of_goods_choosed_reason_textview.setText("点错了");
                                    }
                                })
                        .addSheetItem("下次再买", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        return_of_goods_choosed_reason_textview.setText("下次再买");
                                    }
                                })
                        .addSheetItem("其他", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        return_of_goods_choosed_reason_textview.setText("其他");
                                    }
                                })
                        .show();
                break;
            //选择上传图片
            case R.id.return_of_goods_upload_pic_imageview:
                choosePicMetod();
                break;
            //确认按钮
            case R.id.return_of_goods_choose_submit_apply_button:
                break;
            //退货须知
            case R.id.return_of_goods_should_know_textview:
                Intent intent = new Intent();
                intent.setClass(mContext, RerunOfGoodsShouldKnowActivity.class);
                startActivity(intent);

                break;
            default:
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


            mUploadImages.add(gettedImage);
            uploadPicAdapter.notifyDataSetChanged();
            //这里去上传图片
            upLoadPic(imagepath, imagename);
        }
    }


    /**
     * 上传图片
     */
    private void upLoadPic(String imagepath, String imagename)
    {
        File fileToUpload = new File(imagepath);
        UploadImgReqHttp uploadImgReqHttp = new UploadImgReqHttp(mContext);
        uploadImgReqHttp.sendAndUploadImgReqHttpJson(new UploadImgReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {

            }
        }, new UploadImgReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {

            }
        }, fileToUpload, imagename);
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


    /**
     * 适配器
     */
    class UploadPicAdapter extends BaseAdapter{


        private List<Bitmap>  bitmapList;
        public UploadPicAdapter()
        {

        }

        public UploadPicAdapter(List<Bitmap> _bitmapList)
        {
            this.bitmapList = _bitmapList;
        }

        @Override
        public int getCount() {
            return bitmapList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null)
            {
                holder = new Holder();
                convertView = View.inflate(mContext, R.layout.item_upload_pic, null);
                holder.return_of_goods_add_pic = (ImageView) convertView.findViewById(R.id.return_of_goods_add_pic);
                holder.return_of_goods_delete_pic = (TextView) convertView.findViewById(R.id.return_of_goods_delete_pic);
                convertView.setTag(holder);
            }
            else
            {
                holder = (Holder) convertView.getTag();
            }
            if (position == 0)
            {
                //设置删除按钮不可见
                holder.return_of_goods_delete_pic.setBackgroundResource(R.color.gray_line_3);

            }


            holder.return_of_goods_add_pic.setImageBitmap(bitmapList.get(position));
            holder.return_of_goods_delete_pic.setBackgroundDrawable(getDrawable(R.mipmap.item_upload_pic_quxiao_focused));
            //添加图片的按钮
            holder.return_of_goods_add_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0)
                    {
                        //只有第一个才能添加图片
                        choosePicMetod();
                    }
                }
            });


            holder.return_of_goods_delete_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != 0)
                    {
                        AlertDialog alertDialog = new AlertDialog(mContext);
                        alertDialog.builder()
                                .setTitle("提示:")
                                .setMsg("确定要删除吗?")
                                .setPositiveButton("删除", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Bitmap removed = bitmapList.get(position);
                                        removed.recycle();
                                        bitmapList.remove(position);
                                        uploadPicAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }

                }
            });
            return convertView;
        }

        class Holder{
            ImageView return_of_goods_add_pic;//添加图片
            TextView return_of_goods_delete_pic;//删除图片
        }
    }




}
