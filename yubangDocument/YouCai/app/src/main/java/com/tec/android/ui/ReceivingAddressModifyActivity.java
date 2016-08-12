package com.tec.android.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sales.vo.GetAddressListResp;
import com.sales.vo.base.AddressInfo;
import com.tec.android.configs.Configs;
import com.tec.android.network.ModifyAddressReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.AddressInfoBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.R;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.CustomProgressDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * ReceivingAddressModifyActivity: 修改收件地址  新建收件地址 都是这个 也就是2个入口
 *
 * @author andy
 * @version 1.0
 * @created 2015-07-31
 */
public class ReceivingAddressModifyActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener{

    private Context mContext;
    private EditText receiving_address_name_modify;//名字
    private EditText receiving_address_phone_modify;//联系电话
    private EditText receiving_address_modify;      //地址
    private Button   receiving_address_save_and_use_button;//保存并使用
    private ImageView title_back;//返回


    private GetAddressListResp newAddressList;//
    private AddressInfo addressInfo;

    private int fromWhichActiviry;//从哪个界面过来的 1.update 0.add

    //进度对话框
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_receiving_address_modify);

        mContext = this;


        findViews();
        fromWhichActiviry = getIntent().getIntExtra("modifyaddress", 1);
        if (fromWhichActiviry == 1)
        {
            //要修改原有地址
            String addressJson = getIntent().getExtras().getString("addressinfo");
            //给当前的address info
            addressInfo = new Gson().fromJson(addressJson, AddressInfo.class);

            receiving_address_name_modify.setText(addressInfo.getAddressname());
            receiving_address_phone_modify.setText(addressInfo.getAddressphone());
            receiving_address_modify.setText(addressInfo.getAddress());

        }
        else if (fromWhichActiviry == 0)
        {
            //新建地址  就不需要改变
        }

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        receiving_address_name_modify = (EditText) findViewById(R.id.receiving_address_name_modify);
        receiving_address_phone_modify = (EditText) findViewById(R.id.receiving_address_phone_modify);
        receiving_address_phone_modify.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        receiving_address_modify = (EditText) findViewById(R.id.receiving_address_modify);
        receiving_address_save_and_use_button = (Button) findViewById(R.id.receiving_address_save_and_use_button);

        title_back = (ImageView) findViewById(R.id.title_back);
        //监听器
        receiving_address_save_and_use_button.setOnClickListener(this);
        title_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //保存并且使用地址
            case R.id.receiving_address_save_and_use_button:
                receivingAddressAndUse();

                break;
            //返回
            case R.id.title_back:
                new AlertDialog(mContext).builder()
                        .setCancelable(false)
                        .setTitle("提示")
                        .setMsg("您还没保存地址信息,是否保存!")
                        .setPositiveButton("去保存", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setNegativeButton("不保存", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setResult(Activity.RESULT_CANCELED);
                                ReceivingAddressModifyActivity.this.finish();
                            }
                        }).show();

                break;
        }
    }

    /**
     * 保存并且使用地址
     */
    private void receivingAddressAndUse() {
        //拿到姓名 电话 地址
        String name = receiving_address_name_modify.getText().toString().trim();
        String address = receiving_address_modify.getText().toString().trim();
        String phone = receiving_address_phone_modify.getText().toString().trim();

        if (phone.length() != 11)
        {
            toastMgr.builder.display("您输入的电话号码不对,请重新输入!", 1);
            receiving_address_phone_modify.requestFocus();
            receiving_address_phone_modify.setText("");
            return;
        }

        String address1 = null;
        String name1 = null;
        try {
            name1 = new String(name.getBytes("UTF-8"));
            address1 = new String(address.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //显示进度
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "保存中...", false, null);


        ModifyAddressReqHttp modifyAddressReqHttp = new ModifyAddressReqHttp(mContext);
        //从更新地址跳过来
        if (fromWhichActiviry == 1)
        {
            //update
            addressInfo.setAddress(address1);
            addressInfo.setAddressname(name1);
            addressInfo.setAddressphone(phone);
            modifyAddressReqHttp.sendAndModifyAddress(new ModifyAddressReqHttp.DoingSuccessCallback() {
                                                          @Override
                                                          public void onSuccess(String result) {
                                                              L.i("ModifyAddress update  result = " , result);
                                                              //在网络上成功保存了地址
                                                              //那么在本地也要保存
                                                              //读取本地地址, 然后保存
                                                              Message msg = new Message();
                                                              msg.what = 1;//成功
                                                              msg.obj = result;
                                                              addressHelperHandler.sendMessage(msg);

                                                          }
                                                      },
                    new ModifyAddressReqHttp.DoingFailedCallback() {
                        @Override
                        public void onFail(String resultMsg) {
                            L.i("ModifyAddress update  resultMsg = " , resultMsg);
                            Message msg = new Message();
                            msg.what = 2;//失败
                            msg.obj = resultMsg;
                            addressHelperHandler.sendMessage(msg);
                        }
                    },
                    "update", addressInfo
            );
        }
        else
        {
            //从新建地址跳过来
            AddressInfo addAddressInfo = new AddressInfo();
            addAddressInfo.setAddressname(name);
            addAddressInfo.setAddress(address);
            addAddressInfo.setAddressphone(phone);
            addAddressInfo.setSelected(1);
            //全局变量赋值
            addressInfo = addAddressInfo;
            modifyAddressReqHttp.sendAndModifyAddress(new ModifyAddressReqHttp.DoingSuccessCallback() {
                                                          @Override
                                                          public void onSuccess(String result) {
                                                              L.i("ModifyAddress add  result = " , result);

                                                              //这里应该是从result里面的地址 写到sp里面 因为result里面带有id
                                                              Message msg = new Message();
                                                              msg.what = 1;//失败
                                                              msg.obj = result;
                                                              addressHelperHandler.sendMessage(msg);
                                                          }
                                                      },
                    new ModifyAddressReqHttp.DoingFailedCallback() {
                        @Override
                        public void onFail(String resultMsg) {
                            L.i("ModifyAddress add resultMsg = " , resultMsg);
                            Message msg = new Message();
                            msg.what = 2;//失败
                            msg.obj = resultMsg;
                            addressHelperHandler.sendMessage(msg);
                        }
                    },
                    "add",addAddressInfo
            );
        }





    }

    /**
     * 地址保存操作的handler
     */
    private Handler addressHelperHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //保存地址成功了
                case 1:
                    String addressString = SPUtils.getAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, null);
                    if (addressString == null || addressString.equals("[]"))
                    {
                        AddressInfoBean addressInfoBean = new AddressInfoBean();
                        List<AddressInfo> list = new ArrayList<>();
                        list.add(addressInfo);
                        addressInfoBean.setAddressInfoList(list);
                        String addressJson = new Gson().toJson(addressInfoBean);
                        //写进sp里卖弄
                        SPUtils.putAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, addressJson);
                    }
                    else
                    {
                        AddressInfoBean addressInfoBean = new Gson().fromJson(addressString, AddressInfoBean.class);
                        List<AddressInfo> addressInfoList = addressInfoBean.getAddressInfoList();
                        addressInfoList.add(addressInfo);
                        String addressStringToWrite = new Gson().toJson(addressInfoBean);
                        //写到sp里面
                        SPUtils.putAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, addressStringToWrite);
                    }
                    mProgressDialog.dismiss();
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
                //保存地址失败了
                case 2:
                    //地址在后台保存失败了  那么就提示用户保存失败  稍后再试
                    mProgressDialog.dismiss();
                    setResult(404);
                    finish();
                    toastMgr.builder.display("地址保存失败, 请稍后再试...", 0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId())
        {
            case R.id.receiving_address_name_modify:
                toastMgr.builder.display("name " , 0);
                break;
            case R.id.receiving_address_phone_modify:
                toastMgr.builder.display("receiving_address_phone_modify " , 0);
                break;
            case R.id.receiving_address_modify:
                toastMgr.builder.display("receiving_address_modify " , 0);
                break;
        }
    }
}
