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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2Login;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * RegisterDetailsActivity: 注册详情界面
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailsActivity extends BaseActivity {

    private static final String TAG = "RegisterDetailsActivity";

    private Context mContext;

    private ImageView       img_back;//返回
    private RelativeLayout  car_info;//车辆信息
    private EditText        register_detail_select_car_num;//车牌号
    private ImageView       image_id_card;//上传身份证
    private ImageView       image_driver_photo;//上传车主照片
    private ImageView       image_car_photo;//上传车辆照片
    private ImageView       image_driver_license;//上传驾驶证
    private ImageView       image_driving_license;//上传行驶证
    private ImageView       image_taxi_license;//上传营运证
    private Button          btn_commit;//提交
    private TextView        register_detail_select_car_info;



    private String          mPicPath;
    private int             mCurrentUploadingStatus = 2;
    private String          mPicPath1;//身份证路径
    private String          mPicPath2;//
    private String          mPicPath3;
    private String          mPicPath4;
    private String          mPicPath5;
    private String          mPicPath6;


    private boolean         isSetPhoto1 = false;
    private boolean         isSetPhoto2 = false;
    private boolean         isSetPhoto3 = false;
    private boolean         isSetPhoto4 = false;
    private boolean         isSetPhoto5 = false;
    private boolean         isSetPhoto6 = false;

    private CustomProgressDialog mProgressDialog;

    String userid;

    static class UploadStatus {
        public final static int CAR_TYPE = 0;
        public final static int CAR_NUM = 1;
        public final static int ID = 2;
        public final static int DRIVER_PHOTO = 3;
        public final static int CAR_PHOTO = 4;
        public final static int DRIVER_LICENSE = 5;
        public final static int DRIVING_LICENSE = 6;
        public final static int TAXI_LICENSE = 7;
        public final static int DONE = 1111;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_details);

        mContext = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
        {

        }
        else
        {
            userid = bundle.getString("userid");
        }
        
        findViews();

        mProgressDialog = new CustomProgressDialog(mContext);
    }

    private void findViews()
    {
        img_back = (ImageView) findViewById(R.id.img_back);//返回

        car_info = (RelativeLayout) findViewById(R.id.car_info  );//车辆信息

        register_detail_select_car_num = (EditText) findViewById(R.id.register_detail_select_car_num  );//车牌号

        image_id_card           = (ImageView) findViewById(R.id.image_id_card  );//上传身份证

        image_driver_photo      = (ImageView) findViewById(R.id.image_driver_photo  );//上传车主照片

        image_car_photo         = (ImageView) findViewById(R.id.image_car_photo  );//上传车辆照片

        image_driver_license    = (ImageView) findViewById(R.id.image_driver_license  );//上传驾驶证

        image_driving_license   = (ImageView) findViewById(R.id.image_driving_license  );//上传行驶证

        image_taxi_license      = (ImageView) findViewById(R.id.image_taxi_license  );//上传营运证

        btn_commit              = (Button) findViewById(R.id.btn_commit  );//提交

        register_detail_select_car_info = (TextView) findViewById(R.id.register_detail_select_car_info);//选择的车型

        /**
         * 设置监听器
         */
        //返回
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //选择车辆信息
        car_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailChooseCarInfoActivity.class);
                startActivity(intent);



            }
        });
        //打开上传身份证界面
        image_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", IDCARD);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, IDCARD);//上传身份证
            }
        });
        //打开上传车主头像界面
        image_driver_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", DRIVER_PHOTO);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, DRIVER_PHOTO);//上传司机照片
            }
        });
        //打开上传车辆照片界面
        image_car_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", CAR_PHOTO);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, CAR_PHOTO);//上传车辆照片
            }
        });
        //打开上传驾驶证界面
        image_driver_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", DRIVER_LICENSE);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, DRIVER_LICENSE);//上传驾驶证
            }
        });
        //打开上传行驶证界面
        image_driving_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", DRIVING_LICENSE);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, DRIVING_LICENSE);//上传行驶证
            }
        });
        //打开上传运营证界面
        image_taxi_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", TAXI_LICENSE);
                intent.putExtras(bundle);
                intent.setClass(RegisterDetailsActivity.this, RegisterDetailUploadIDCardActivity.class);
                startActivityForResult(intent, TAXI_LICENSE);//上传营运证
            }
        });
        //都已经上传了  提交
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentUploadingStatus = UploadStatus.CAR_NUM;


                if (register_detail_select_car_num.getText().toString().equals(""))
                {
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().
                            setCancelable(true)
                            .setTitle("没有填写车牌号")
                            .setPositiveButton("继续继续填写", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .show();
                    return;
                }

//                if ((isSetPhoto1 && isSetPhoto2 && isSetPhoto3 && isSetPhoto4 && isSetPhoto5 && isSetPhoto6) != true)
//                {
//                    AlertDialog alertDialog = new AlertDialog(mContext);
//                    alertDialog.builder().
//                            setCancelable(true)
//                            .setTitle("添加信息不完整")
//                            .setPositiveButton("继续添加", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                }
//                            })
//                            .show();
//                    return;
//                }

                upLoadCarTNum(register_detail_select_car_num.getText().toString(), Configs.getLoginedInfo(mContext).getUserid());
            }
        });
        //TODO 这里 应该是 在下一个界面上传  上传完了回到这个界面  在响应的图片上面打钩 最后所有都完成之后  提交 然后后台去处理
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String picPath;
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case IDCARD:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath1 = picPath;
                    image_id_card.setImageBitmap(lessenUriImage(mPicPath1));
                    isSetPhoto1 = true;

                    break;
                case DRIVER_PHOTO:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath2 = picPath;
                    image_driver_photo.setImageBitmap(lessenUriImage(mPicPath2));
                    isSetPhoto2 = true;

                    break;
                case CAR_PHOTO:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath3 = picPath;
                    image_car_photo.setImageBitmap(lessenUriImage(mPicPath3));
                    isSetPhoto3 = true;

                    break;
                case DRIVER_LICENSE:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath4 = picPath;
                    image_driver_license.setImageBitmap(lessenUriImage(mPicPath4));
                    isSetPhoto4 = true;

                    break;
                case DRIVING_LICENSE:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath5 = picPath;
                    image_driving_license.setImageBitmap(lessenUriImage(mPicPath5));
                    isSetPhoto5 = true;

                    break;
                case TAXI_LICENSE:
                    picPath = data.getStringExtra(RegisterDetailUploadIDCardActivity.KEY_PHOTO_PATH);
                    mPicPath6 = picPath;
                    image_taxi_license.setImageBitmap(lessenUriImage(mPicPath6));
                    isSetPhoto6 = true;

                    break;
            }
        }
        else if(resultCode == Activity.RESULT_CANCELED)
        {
            toastMgr.builder.display("上传取消", 0);
        }

    }

    /**
     * 选择完车型, 又回来这个界面
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle == null)
        {
            return;
        }
        else
        {
            //表示选择车型成功, 就要上传给服务器
            String carCompany;
            String carBrand;
            String carSeries;
            String produceYear;
            String carCapacity;
            String carHorsePower;
            String carMotivation;
            String userid;
            carCompany = bundle.getString(Configs.carCompany, "");
            carBrand = bundle.getString(Configs.carBrand, "");
            carSeries = bundle.getString(Configs.carSeries, "");
            produceYear = bundle.getString(Configs.produceYear, "");
            carCapacity = bundle.getString(Configs.carCapacity, "");
            carHorsePower = bundle.getString(Configs.carHorsepower, "");
            carMotivation = bundle.getString(Configs.carMotivation, "");
//            userid      = (String) SPUtils.get(mContext,Configs.userid,"");
            userid = Configs.getLoginedInfo(mContext).getUserid();

            String chooseedType = "";
            chooseedType = (String) SPUtils.get(mContext,"chooseedCompany","");
            chooseedType = chooseedType + "-" + SPUtils.get(mContext, "chooseedBrand","");
            chooseedType = chooseedType + "-" + SPUtils.get(mContext, "chooseedChildBrand","");
            //chooseedType = chooseedType + "-" + SPUtils.get(mContext, "chooseedCapacity","");
            //chooseedType = chooseedType + "-" + SPUtils.get(mContext, "chooseedYear","");
            chooseedType = chooseedType + "-" + carHorsePower;
            chooseedType = chooseedType + "-" + carMotivation;

            register_detail_select_car_info.setText(chooseedType);


            //上传
            upLoadCarType(carCompany, carBrand, carSeries, produceYear, carCapacity, carHorsePower, carMotivation, userid);
        }
    }

    /**
     * 上传carType
     * @param _carCompany
     * @param _carBrand
     * @param _carSeries
     * @param _produceYear
     * @param _carCapacity
     */
    private void upLoadCarType( String _carCompany,
                                String _carBrand,
                                String _carSeries,
                                String _produceYear,
                                String _carCapacity,
                                String _carHorsePower,
                                String _carMotivation,
                                String _userid
                                )
    {
        mProgressDialog = mProgressDialog.show(mContext,"正在上传车辆信息", false, null);

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE)
                .addParams("userReq.carCompany",  _carCompany)
                .addParams("userReq.carBrand",    _carBrand)
                .addParams("userReq.carSeries", _carSeries)
                .addParams("userReq.horsepower", _carHorsePower)
                .addParams("userReq.motivation", _carMotivation)
                //.addParams("userReq.produceYear", _produceYear)
                //.addParams("userReq.carCapacity", _carCapacity)
                .addParams("userReq.userid", _userid)
                .build()
                .execute(new UploadCarTypeCallback());
        L.i(TAG, "上传carType url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE + "?"
                        + "userReq.carCompany=" +   _carCompany
                        + "&userReq.carBrand=" +   _carBrand
                        + "&userReq.carSeries=" +    _carSeries
                        + "&userReq.horsepower=" +    _carHorsePower
                        + "&userReq.motivation=" +    _carMotivation
                        //+ "&userReq.produceYear=" +  _produceYear
                        //+ "&userReq.carCapacity=" +  _carCapacity
                        + "&userReq.userid=" +_userid
        );

    }

    class UploadCarTypeCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!", 1);
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "上传汽车信息 json -= " + response);
            mProgressDialog.dismiss();
            Json2Login json2Login = new Json2Login(response);

            Json2LoginBean json2LoginBean = json2Login.getLoginObj();

            if (json2LoginBean == null)
            {
                //解析json出错, 那就是后台更新了json 提示去升级应用
                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setCancelable(true).setTitle("更新")
                        .setMsg("当前版本太低,请更新版本")
                        .setPositiveButton("立即更新", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toastMgr.builder.display("前往应用商城,", 1);
                            }
                        })
                        .show();
                return;
            }
            else
            {
                if (json2LoginBean.getReturnCode() == 0)
                {
                    //登陆成功
                    //将最新的信息保存
                    Configs.putLoginedInfo(mContext, json2LoginBean);
                    L.d(TAG, "上传车辆信息成功, 保存车辆嘻嘻成功");
                }
                else if (json2LoginBean.getReturnCode() == 100)
                {
                    //未登录
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                }
                else if (json2LoginBean.getReturnCode() == -2)
                {
                    //查无此车
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                }
                else if(json2LoginBean.getReturnCode() == -1)
                {
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                }
            }



        }
    }
    private void upLoadCarTNum( String _carNum,
                                String _userid
    )
    {
        mProgressDialog = mProgressDialog.show(mContext,"正在上传车辆牌照...", false, null);

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_NUM)
                .addParams("userReq.carNum",  _carNum)

                .addParams("userReq.userid", _userid)
                .build()
                .execute(new UploadCarNumCallback());
        L.i(TAG, "上传车牌照 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE + "?"
                        + "userReq.carNum=" + _carNum
                        + "&userReq.userid=" + _userid
        );

    }


    class UploadCarNumCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,上传车辆牌照失败,请检查网络!", 1);
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "上传汽车信息 json -= " + response);

            Json2Login json2Login = new Json2Login(response);

            Json2LoginBean json2LoginBean = json2Login.getLoginObj();

            if (json2LoginBean == null)
            {
                //解析json出错, 那就是后台更新了json 提示去升级应用
                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setCancelable(true).setTitle("更新")
                        .setMsg("当前版本太低,请更新版本")
                        .setPositiveButton("立即更新", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toastMgr.builder.display("前往应用商城,", 1);
                            }
                        })
                        .show();
                return;
            }
            else
            {
                if (json2LoginBean.getReturnCode() == 0)
                {
                    //登陆成功
                    //将最新的信息保存
                    //接下来就是上传图片
                    //首先上传身份证
//                    mCurrentUploadingStatus = UploadStatus.ID;
//                    mProgressDialog.setMessage("上传牌照成功");
//                    L.d(TAG, "上传车辆牌照成功, ");
//
//                    //去上传图片
//                    uploadFile(mPicPath1,"0");


                    mProgressDialog.setMessage("恭喜您, 上传成功!");
                    mProgressDialog.dismiss();
                    mCurrentUploadingStatus = UploadStatus.DONE;
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(true).setTitle("成功")
                            .setMsg("恭喜您上传成功,请等待审核")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    toastMgr.builder.display("前往审核界面,", 1);
                                    Intent intent = new Intent();
                                    intent.setClass(mContext,UploadedInfosCheckActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
                else if (json2LoginBean.getReturnCode() == 100)
                {
                    //未登录
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(true).setTitle("未登录")
                            .setMsg("您的登陆信息已过期,请重新登陆")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, LoginActivity.class);
                                    mContext.startActivity(intent);
                                }
                            })
                            .show();
                }
                else if (json2LoginBean.getReturnCode() == -2)
                {
                    //查无此车
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                    AlertDialog alertDialog = new AlertDialog(mContext);
                    alertDialog.builder().setCancelable(true).setTitle("查无此车")
                            .setMsg("您选择的车型暂时还没有,请联系客服!")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ClientServiceActivity.class);
                                    mContext.startActivity(intent);
                                    //联系客服
                                }
                            })
                            .show();
                }
                else if(json2LoginBean.getReturnCode() == -1)
                {
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                }
            }



        }
    }

    public void uploadFile(String path, String fileCode)
    {
        File file = new File(path);

        if (!file.exists())
        {
            toastMgr.builder.display("文件不存在，请修改文件路径", 1);
            return;
        }




        Map<String, String> header = new HashMap<>();
        header.put("enctype", "multipart/form-data");

        String userid = (String) SPUtils.get(mContext, "userid", "");
        userid = (String) SPUtils.getUserInfo(mContext, Configs.userid,"");
        if (userid.equals("") || userid == null)
        {
            toastMgr.builder.display("请重新登录",1);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userReq.fileCode", fileCode);
        params.put("userReq.userid", userid);
        OkHttpUtils.post()//
                .addFile("userReq.photo", file.getName(), file)//
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_FILE)
                .params(params)
                .build()//
                .execute(new MyStringCallback());
    }

    /**
     * 上传照片
     */
    class MyStringCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mProgressDialog.dismiss();
            toastMgr.builder.display("上传失败,网络错误,请重新上传", 1);
        }

        @Override
        public void onResponse(String response) {
            toastMgr.builder.display("Message = " + response, 1);
            L.d("RegisterDetailUploadIDCardActivity", response);

            if (mCurrentUploadingStatus == UploadStatus.ID)
            {
                mProgressDialog.setMessage("正在上传司机身份证...");
                mCurrentUploadingStatus = UploadStatus.DRIVER_PHOTO;
                uploadFile(mPicPath2,"1");
            }
            else if (mCurrentUploadingStatus == UploadStatus.DRIVER_PHOTO)
            {
                mProgressDialog.setMessage("正在上传司机照片...");
                mCurrentUploadingStatus = UploadStatus.CAR_PHOTO;
                uploadFile(mPicPath3,"2");
            }
            else if (mCurrentUploadingStatus == UploadStatus.CAR_PHOTO)
            {
                mProgressDialog.setMessage("正在上传车辆照片...");
                mCurrentUploadingStatus = UploadStatus.DRIVER_LICENSE;
                uploadFile(mPicPath4,"3");
            }
            else if (mCurrentUploadingStatus == UploadStatus.DRIVER_LICENSE)
            {
                mProgressDialog.setMessage("正在上传驾驶证照片...");
                mCurrentUploadingStatus = UploadStatus.DRIVING_LICENSE;
                uploadFile(mPicPath5,"4");
            }
            else if (mCurrentUploadingStatus == UploadStatus.DRIVING_LICENSE)
            {
                mProgressDialog.setMessage("正在上传行驶证照片...");
                mCurrentUploadingStatus = UploadStatus.TAXI_LICENSE;
                uploadFile(mPicPath6,"5");
            }
            else if (mCurrentUploadingStatus == UploadStatus.TAXI_LICENSE)
            {
                mProgressDialog.setMessage("恭喜您, 上传成功!");
                mProgressDialog.dismiss();
                mCurrentUploadingStatus = UploadStatus.DONE;
                AlertDialog alertDialog = new AlertDialog(mContext);
                alertDialog.builder().setCancelable(true).setTitle("成功")
                        .setMsg("恭喜您上传成功,请等待审核")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toastMgr.builder.display("前往审核界面,", 1);
                                Intent intent = new Intent();
                                intent.setClass(mContext,UploadedInfosCheckActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    /**
     * use to lessen pic 50%
     * @param path sd card path
     * @return bitmap
     */
    public final  Bitmap lessenUriImage(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int)(options.outHeight / (float)320);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        bitmap=BitmapFactory.decodeFile(path,options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        System.out.println(w+" "+h); //after zoom
        return bitmap;
    }



    public static final int IDCARD = 0X11;
    public static final int DRIVER_PHOTO = 0X12;
    public static final int CAR_PHOTO = 0X13;
    public static final int DRIVER_LICENSE = 0X14;//驾驶证
    public static final int DRIVING_LICENSE = 0X15;//行驶证
    public static final int TAXI_LICENSE = 0X16;//营运证





}
