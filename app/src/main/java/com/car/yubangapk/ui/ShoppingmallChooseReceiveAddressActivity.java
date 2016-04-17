package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.AddressBean;

import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.network.myHttp.HttpReqAddress;
import com.car.yubangapk.network.myHttp.httpReqInterface;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;


import java.util.List;

/**
 * 选择收货人列表
 */
public class ShoppingmallChooseReceiveAddressActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private static final String TAG = ShoppingmallChooseReceiveAddressActivity.class.getSimpleName();
    private Context mContext;

    private ImageView       img_back;//返回
    private ListView        myaddresnew_listview;//联系人列表
    private LinearLayout    ll_no_result;//没有联系人
    private Button          btn_add_new_receive_people;//新建联系人

    private ReceivePeopleListViewAdapter mAdapter;
    private CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_choose_receive_address);

        mContext = this;

        findViews();

        mProgress = new CustomProgressDialog(mContext);

        getAllAddress();

    }

    /**
     * 获取全部的地址列表
     */
    private void getAllAddress()
    {
        mProgress = mProgress.show(mContext,"正在加载中...", false, null);
        Json2LoginBean bean = Configs.getLoginedInfo(mContext);
        String userid = bean.getUserid();
        HttpReqAddress reqGetAddressConformOrder = new HttpReqAddress(userid,"3", null, null, null);
        reqGetAddressConformOrder.setCallback(new httpReqInterface() {
            @Override
            public void onGetAddressSucces(Json2AddressBean addressBean) {
                mProgress.dismiss();
                Json2AddressBean json2AddressBean = addressBean;
                List<AddressBean> list = json2AddressBean.getAddresses();


                mAdapter = new ReceivePeopleListViewAdapter(list);
                myaddresnew_listview.setAdapter(mAdapter);
            }

            @Override
            public void onGetAddressFail(int errorCode) {
                mProgress.dismiss();
                if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                {
                    UpdateApp.gotoUpdateApp(mContext);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
                {
                    toastMgr.builder.display("网络错误" ,1);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NO_ADDRESS)
                {
                    toastMgr.builder.display("没有收货信息信息" ,1);
                    myaddresnew_listview.setVisibility(View.GONE);
                    ll_no_result.setVisibility(View.VISIBLE);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                {
                    toastMgr.builder.display("没有登录" ,1);
                    NotLogin.gotoLogin(ShoppingmallChooseReceiveAddressActivity.this);

                }
                else if (errorCode == ErrorCodes.ERROR_CODE_SERVER)
                {
                    toastMgr.builder.display("服务器错误" ,1);
                }
            }
        });
        reqGetAddressConformOrder.getAddressPeopleInfo();
    }


    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回

        myaddresnew_listview = (ListView) findViewById(R.id.myaddresnew_listview);//联系人列表

        ll_no_result = (LinearLayout) findViewById(R.id.ll_no_result);//没有联系人

        btn_add_new_receive_people = (Button) findViewById(R.id.btn_add_new_receive_people);//新建联系人

        btn_add_new_receive_people.setOnClickListener(this);
        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
            case R.id.btn_add_new_receive_people:
                //跳转
                Intent intent = new Intent();
                intent.setClass(mContext, ShoppingmallAddReceivePeopleInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(JUMP_METHOD, "add");
                intent.putExtras(bundle);
                startActivityForResult(intent, ADD_REQUEST_CODE);

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        toastMgr.builder.display("position" + position + "click", 1);



    }


    class ReceivePeopleListViewAdapter extends BaseAdapter
    {

        private List<AddressBean> peopleList;

        public ReceivePeopleListViewAdapter(List<AddressBean> list)
        {
            this.peopleList = list;
        }

        public void refresh(List<AddressBean> list)
        {
            this.peopleList = list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return peopleList.size();
        }

        @Override
        public Object getItem(int position) {
            return peopleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

             /*
             * 1.手工创建对象 2.加载xml文件
             */
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_address_item, null);
                holder.tv_address_item_name = (TextView) view.findViewById(R.id.tv_address_item_name);
                holder.tv_address_item_phone = (TextView) view.findViewById(R.id.tv_address_item_phone);
                holder.tv_address_item_default = (TextView) view.findViewById(R.id.tv_address_item_default);
                holder.modify_address_layout = (LinearLayout) view.findViewById(R.id.modify_address_layout);
                holder.relatalayout_address_item = (RelativeLayout) view.findViewById(R.id.relatalayout_address_item);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }


            holder.tv_address_item_name.setText(peopleList.get(position).getName());
            holder.tv_address_item_phone.setText(peopleList.get(position).getPhone());
            if (peopleList.get(position).getIsDefaule() == 1)
            {
                holder.tv_address_item_default.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.tv_address_item_default.setVisibility(View.INVISIBLE);
            }

            holder.modify_address_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddressBean address = peopleList.get(position);
                    ModifyAddress(address);
                }
            });

            holder.relatalayout_address_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddressBean bean = peopleList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", bean);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });

            return view;
        }

        class ViewHolder
        {
            TextView        tv_address_item_name;//名字
            TextView        tv_address_item_phone;//电话
            TextView        tv_address_item_default;//是否默认
            RelativeLayout  relatalayout_address_item;//item
            LinearLayout    modify_address_layout;//编辑地址
        }
    }

    /**
     * 修改联系人信息
     * @param address
     */
    private void ModifyAddress(AddressBean address)
    {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallAddReceivePeopleInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("address", address);
        bundle.putString(JUMP_METHOD, "modify");
        intent.putExtras(bundle);
        startActivityForResult(intent, MODIFY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == MODIFY_REQUEST_CODE)
            {
                //修改返回, 请求重新加载地址列表
                getAllAddress();
            }
            else if (requestCode == ADD_REQUEST_CODE)
            {
                //新增, 同样重新加载
                getAllAddress();
            }
        }
        else
        {
            //没有修改
            toastMgr.builder.display("您没有修改", 1);
        }
    }

    private int MODIFY_REQUEST_CODE = 111;
    private int ADD_REQUEST_CODE    = 222;

    public static final String JUMP_METHOD = "method";//跳转到修改 新增联系人 界面的方式
}
