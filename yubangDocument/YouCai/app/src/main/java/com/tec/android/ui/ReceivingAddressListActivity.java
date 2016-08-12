package com.tec.android.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sales.vo.GetAddressListResp;
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.adapter.ReceivingAddressListViewItemAdapter;
import com.tec.android.configs.Configs;
import com.tec.android.network.GetAddressListHttp;
import com.tec.android.network.ModifyAddressReqHttp;
import com.tec.android.network.NetWorkHelper;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.AddressInfoBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.R;
import com.tec.android.view.CustomProgressDialog;
import com.tec.android.view.PullListView;
import com.tec.android.view.PullListView1;

import java.util.List;

/**
 * ReceivingAddressListActivity: 收货地址列表界面
 *
 * @author andy
 * @version 1.0
 * @created 2015-07-31
 */
public class ReceivingAddressListActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private ImageView title_back;//返回
    private Button button_new_easy_buy_address_create;//新建地址按钮

    //异常情况 就是用户没有地址信息
    private LinearLayout receiving_address_no_data;//没有地址信息
    private Button receiving_address_no_address_button;//点击新建地址

    //下拉反弹的列表
    private PullListView1 pull_list_view;//地址列表


    private static final int NO_ADDRESS_REQUEST_CODE = 0X100;
    private static final int ALREADY_HAVE_ADDRESS_REQUEST_CODE = 0X101;


    private boolean isChooseAddress = false;//这个标志位说明是不是从确认订单里面过来的选择地址


    private ReceivingAddressListViewItemAdapter adapter;//地址item的adapter

    private CustomProgressDialog mCustomProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_receiving_address_list);

        mContext = this;

        findViews();
        //这个标志位说明是不是从确认订单里面过来的选择地址
        isChooseAddress = getIntent().getBooleanExtra("choose", false);


        IntentFilter intentFilter = new IntentFilter("ReceivingAddressListActivity");
        registerReceiver(addressBroadcastReceiver, intentFilter);


        /**
         * 1.首先从网络获取地址信息
         *  有网-->网络获取-->成功拿到-->更新本地-->从本地读取  列表展示
         *               |
         *               |-->网络拿到失败-->从本地读取-->列表展示
         *  没网-->本地获取-->本地有-->读取本地-->展示列表
         *               |
         *               |->本地没有-->显示没有地址-->提示用户新建
         *
          */
        getAddressToDisplay();





    }

    /**
     * 地址item的adapter里面修改了默认地址   然后我要请求网络
     */
    private BroadcastReceiver addressBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String addressItem = intent.getStringExtra("addressItem");
            //转换成地址信息
            AddressInfo addressInfo = new Gson().fromJson(addressItem, AddressInfo.class);

            mCustomProgressDialog = mCustomProgressDialog.show(mContext, "", false, null);

            //请求网络  把地址修改为选择
            ModifyAddressReqHttp modifyAddressReqHttp = new ModifyAddressReqHttp(mContext);
            modifyAddressReqHttp.sendAndModifyAddress(new ModifyAddressReqHttp.DoingSuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    //修改地址成功,
                    adapter.notifyDataSetChanged();
                    if (isChooseAddress == true) {
                        Intent sender = new Intent("change.address.reload.html");
                        mContext.sendBroadcast(sender);
                        mCustomProgressDialog.dismiss();
                        ReceivingAddressListActivity.this.finish();
                    } else {
                        mCustomProgressDialog.dismiss();
                    }

                }
            }, new ModifyAddressReqHttp.DoingFailedCallback() {
                @Override
                public void onFail(String resultMsg) {
                    //网络连接失败

                    if (isChooseAddress == true) {

                        toastMgr.builder.display("修改失败, 请检查网络", 0);
                        mCustomProgressDialog.dismiss();
                    } else {
                        mCustomProgressDialog.dismiss();
                    }
                }
            }, "modify", addressInfo);

        }
    };


    /**
     * 获取地址并且显示
     * 1.有网络, 从网络获取
     * 2.没网络, 从本地获取
     */
    private void getAddressToDisplay() {
        mCustomProgressDialog = new CustomProgressDialog(mContext);

        //判断网络是否可用
        boolean isConnected = checkNetworkAvailable();
        if (isConnected == true)
        {
            //网络可用  就从网络获取
            getAddressFromServer();
        }
        else
        {
            //网络不可用, 就从本地拿
            getAddressFromLocalSharedPreference();
        }
    }

    /**
     * 从网络  服务器获取用户的信息
     */
    private void getAddressFromServer() {
        mCustomProgressDialog = mCustomProgressDialog.show(mContext,"加载中...", false,null);
        GetAddressListHttp getAddressListHttp = new GetAddressListHttp(mContext);
        getAddressListHttp.sendAndGetAddressListJson(
                new GetAddressListHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        getAddressHandler.sendMessage(msg);
                    }
                },
                new GetAddressListHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        Message msg = new Message();
                        msg.what = 100;
                        msg.obj = resultMsg;
                        getAddressHandler.sendMessage(msg);
                    }
                }
        );
    }

    /**
     * 从本地获取用户地址信息
     */
    private void getAddressFromLocalSharedPreference()
    {
        mCustomProgressDialog = mCustomProgressDialog.show(mContext,"加载中...", false,null);
        List<AddressInfo> addressInfoList;
        AddressInfoBean addressInfoBean;
        String addressInfoJson = SPUtils.getAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, null);
        //
        if (addressInfoJson != null && !addressInfoJson.equals("[]"))
        {
            //String asa = "[{\"address\":\"888\",\"addressid\":\"8\",\"addressname\":\"888\",\"addressphone\":\"88888888888\",\"selected\":0},{\"address\":\"77777\",\"addressid\":\"7\",\"addressname\":\"777\",\"addressphone\":\"77777777777\",\"selected\":0},{\"address\":\"666\",\"addressid\":\"6\",\"addressname\":\"666\",\"addressphone\":\"66666666666\",\"selected\":0},{\"address\":\"555\",\"addressid\":\"5\",\"addressname\":\"555\",\"addressphone\":\"55555555555\",\"selected\":0},{\"address\":\"4444\",\"addressid\":\"4\",\"addressname\":\"444\",\"addressphone\":\"44444444444\",\"selected\":0},{\"address\":\"333\",\"addressid\":\"3\",\"addressname\":\"333\",\"addressphone\":\"33333333333\",\"selected\":0},{\"address\":\"22222\",\"addressid\":\"2\",\"addressname\":\"2222\",\"addressphone\":\"222222\",\"selected\":1},{\"address\":\"111111\",\"addressid\":\"1\",\"addressname\":\"1111\",\"addressphone\":\"1111111\",\"selected\":0}]";
            addressInfoBean = new Gson().fromJson(addressInfoJson, AddressInfoBean.class);
            addressInfoList = addressInfoBean.getAddressInfoList();
            Message msg = new Message();
            msg.what = 2;
            msg.obj = addressInfoList;

            getAddressHandler.sendMessage(msg);
        }
        else
        {
            //本地没有地址数据
            receiving_address_no_data.setVisibility(View.VISIBLE);
            button_new_easy_buy_address_create.setVisibility(View.GONE);
        }

    }
    /**
     * 检查网络是否可用
     */
    private boolean checkNetworkAvailable() {
        boolean isConnected = NetWorkHelper.isNetStateConnected(mContext);
        return isConnected;

    }

    /**
     * 去布局初始化空间
     */
    private void findViews() {
        //正常情况下
        title_back = (ImageView) findViewById(R.id.title_back);
        button_new_easy_buy_address_create = (Button) findViewById(R.id.button_new_easy_buy_address_create);
        pull_list_view = (PullListView1) findViewById(R.id.pull_list_view);

        //异常情况下
        receiving_address_no_data = (LinearLayout) findViewById(R.id.receiving_address_no_data);
        receiving_address_no_address_button = (Button) findViewById(R.id.receiving_address_no_address_button);


        //监听器
        title_back.setOnClickListener(this);
        button_new_easy_buy_address_create.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(addressBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title_back:
                toastMgr.builder.display("返回",0);
                finish();
                break;
            case R.id.button_new_easy_buy_address_create:
                toastMgr.builder.display("新建地址", 0);
                Intent intent = new Intent();
                intent.putExtra("modifyaddress", 0);
                intent.setClass(mContext, ReceivingAddressModifyActivity.class);
                startActivityForResult(intent, ALREADY_HAVE_ADDRESS_REQUEST_CODE);
                break;
            //当前还没有地址  提示新建一个
            case R.id.receiving_address_no_address_button:
                Intent intent1 =  new Intent();
                intent1.setClass(mContext, ReceivingAddressModifyActivity.class);
                intent1.putExtra("modifyaddress", 0);
                startActivityForResult(intent1, NO_ADDRESS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NO_ADDRESS_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                //用户完成新建  并且保存了地址
                //就把没有地址的这个消息取消掉  展示地址列表
                receiving_address_no_data.setVisibility(View.GONE);
                receiving_address_no_address_button.setVisibility(View.GONE);
                //红色新建按钮显示出来
                button_new_easy_buy_address_create.setVisibility(View.VISIBLE);

            }
            else
            {
                //用户取消了

            }
        }
        //已经有地址, 再添加地址
        else if (requestCode == ALREADY_HAVE_ADDRESS_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)//并且是成功添加的返回
            {
                getAddressFromLocalSharedPreference();
            }
            else if (resultCode == 404)//添加地址失败
            {
                //
                toastMgr.builder.display("添加地址失败, 请检查网络", 0);
            }
        }
    }

    /**
     * 用来消息处理以及界面交互
     */
    private Handler getAddressHandler =  new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                mCustomProgressDialog.dismiss();
                //从服务器拿到用户地址信息成功
                String json = (String) msg.obj;
                GetAddressListResp resp = (GetAddressListResp) SalesMsgUtils.fromJson(json, MSG_TYPES.MSG_GET_ADDRESS_LIST, false);
                List<AddressInfo> addressInfoList = resp.getAddresslist();
                ///addressInfoList不为空  并且至少存在一个项目
                if (addressInfoList != null && addressInfoList.size() >= 1)
                {
                    //用户已经存了至少一个地址信息
                    adapter = new ReceivingAddressListViewItemAdapter(mContext, addressInfoList, isChooseAddress);


                    //把地址转换成json 存到本地
                    AddressInfoBean addressInfoBean = new AddressInfoBean();
                    addressInfoBean.setAddressInfoList(addressInfoList);
                    String addressJson = new Gson().toJson(addressInfoBean);
                    //因为这个写是覆盖的 所以不用删除
                    SPUtils.putAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, addressJson);
                    pull_list_view.setAdapter(adapter);
                }
                else
                {
                    //用户没有地址信息  提示用户新建地址信息
                    receiving_address_no_data.setVisibility(View.VISIBLE);
                    receiving_address_no_address_button.setVisibility(View.VISIBLE);
                    button_new_easy_buy_address_create.setVisibility(View.GONE);
                }



            }
            else if (msg.what == 2)
            {
                mCustomProgressDialog.dismiss();
                //从本地获取地址信息成功
                List<AddressInfo> addressInfoList = (List<AddressInfo>) msg.obj;
                //用户已经存了至少一个地址信息
                adapter = new ReceivingAddressListViewItemAdapter(mContext, addressInfoList, isChooseAddress);
                pull_list_view.setAdapter(adapter);

            }
            else if (msg.what == 100)
            {
                mCustomProgressDialog.dismiss();
                //不能从网上拿到用户信息  就从本地读取
                //网络不可用, 就从本地拿
                getAddressFromLocalSharedPreference();

            }
        }
    };



}
