package com.tec.android.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sales.vo.base.AddressInfo;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.ui.ReceivingAddressModifyActivity;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 15/8/6.
 */
public class ReceivingAddressListViewItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddressInfo> mList;
    private List<CheckBox> radioButtonStateList;
    private boolean isChooseAddress;

    public ReceivingAddressListViewItemAdapter()
    {

    }

    public ReceivingAddressListViewItemAdapter(Context context)
    {
        this.mContext = context;
    }

    public ReceivingAddressListViewItemAdapter(Context context, List<AddressInfo> list, boolean chooseAddress)
    {
        this.mContext = context;
        this.mList = list;
        radioButtonStateList =  new ArrayList<>();
        this.isChooseAddress = chooseAddress;
    }


    @Override
    public int getCount() {
        return mList.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        final Holder holder;
        if (view == null)
        {
            holder = new Holder();
            view = View.inflate(mContext, R.layout.item_receiving_address, null);
            holder.item_receiving_address_id = (TextView) view.findViewById(R.id.item_receiving_address_id);
            holder.textview_new_easy_buy_address_list_item_name = (TextView) view.findViewById(R.id.textview_new_easy_buy_address_list_item_name);
            holder.textview_new_easy_buy_address_list_item_phone = (TextView) view.findViewById(R.id.textview_new_easy_buy_address_list_item_phone);
            holder.textview_new_easy_buy_address_list_item_address = (TextView) view.findViewById(R.id.textview_new_easy_buy_address_list_item_address);
            holder.radiobutton_new_easy_buy_address_default = (CheckBox) view.findViewById(R.id.radiobutton_new_easy_buy_address_default);
            holder.layout_edit_address_list_item = (RelativeLayout) view.findViewById(R.id.layout_edit_address_list_item);
            holder.textview_new_easy_buy_address_delete = (TextView) view.findViewById(R.id.textview_new_easy_buy_address_delete);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) view.getTag();
        }

        holder.item_receiving_address_id.setText(mList.get(position).getAddressid());



        if (mList.get(position).getSelected() == 1)
        {
            holder.radiobutton_new_easy_buy_address_default.setChecked(true);
        }
        else
            holder.radiobutton_new_easy_buy_address_default.setChecked(false);

        radioButtonStateList.add(holder.radiobutton_new_easy_buy_address_default);
        //
        holder.radiobutton_new_easy_buy_address_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 我要判断出之前哪个checkbox是选中的   设置成不选中   当前点中的设置为选中 更新sp
                int length = mList.size();



                for (int i = 0; i < length; i++)
                {
                   mList.get(i).setSelected(0);//都设置成未选择
                   // 还要联网, 去往后台发, 告诉后台  这个是默认地址
                }
                mList.get(position).setSelected(1);
                //写入本地
                String writeJson = new Gson().toJson(mList);
                SPUtils.putAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, writeJson);

                //一个标志位  表示是选择一个地址  而不是从其他界面来的普通的修改地址
                //我在ReceivingAddressListActivity的接收器里面已经判断了  这里就不用判断了
                if (isChooseAddress == true || isChooseAddress == false)
                {
                    Intent intent = new Intent("ReceivingAddressListActivity");
                    String addressJson = new Gson().toJson(mList.get(position));
                    intent.putExtra("addressItem", addressJson);
                    mContext.sendBroadcast(intent);
                }

            }
        });

        holder.textview_new_easy_buy_address_list_item_name.setText(mList.get(position).getAddressname());
        holder.textview_new_easy_buy_address_list_item_address.setText(mList.get(position).getAddress());
        holder.textview_new_easy_buy_address_list_item_phone.setText(mList.get(position).getAddressphone());

        //编辑按钮的监听事件
        holder.layout_edit_address_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMgr.builder.display("编辑", 0);
                addressItemEdit(position, mList.get(position));

            }
        });
        //删除按钮的监听事件
        holder.textview_new_easy_buy_address_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressItemDelete(position);
            }
        });

        return view;

    }

    /**
     * 编辑地址 修改地址
     * @param position
     * @param addressInfo
     */
    private void addressItemEdit(int position, AddressInfo addressInfo) {
        Intent intent =  new Intent();
        //要修改原有地址
        intent.putExtra("modifyaddress",1);
        Bundle bundle = new Bundle();
        String addressJson = new Gson().toJson(addressInfo);
        bundle.putString("addressinfo",addressJson);
        intent.putExtras(bundle);
        intent.setClass(mContext, ReceivingAddressModifyActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 地址项删除地址操作
     * @param position 第几个地址被删除
     */
    private void addressItemDelete(final int position)
    {
        toastMgr.builder.display("第 " + position + "个地址删除", 0);
        //提示用户是否真的要删除
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("真的要删除吗");
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //确认删除
                toastMgr.builder.display("addressItemDelete 没有删除功能", 0);
                //删除这条地址记录
//                String addressId = mList.get(position).getAddressid();
//                mList.remove(position);
//                //从本地删除  并且从网络删除
//                AddressInfoBean addressInfoBean = new AddressInfoBean();
//                addressInfoBean.setAddressInfoList(mList);
//                String addressJson = new Gson().toJson(addressInfoBean);
//                //从本地删除
//                SPUtils.putAddress(mContext, Configs.ADDRESS_INFO_LIST_IN_SP, addressJson);
//                //再从网络删除


            }
        });
        builder.setNegativeButton(android.R.string.no, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不删除
                toastMgr.builder.display("addressItemDelete 不删除", 0);
            }
        });
        builder.create();
        builder.show();

    }


    /**
     * Holder
     */
    static class Holder
    {
        TextView item_receiving_address_id;                         //地址id
        TextView textview_new_easy_buy_address_list_item_name;      //地址 人名
        TextView textview_new_easy_buy_address_list_item_phone;     //电话号码
        TextView textview_new_easy_buy_address_list_item_address;   //地址
        CheckBox radiobutton_new_easy_buy_address_default;          //设置为默认地址
        RelativeLayout layout_edit_address_list_item;                //编辑地址
        TextView textview_new_easy_buy_address_delete;              //删除地址  没有了
    }
}
