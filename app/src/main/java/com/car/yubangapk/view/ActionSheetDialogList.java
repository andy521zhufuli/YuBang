package com.car.yubangapk.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.andy.android.yubang.R;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;

import java.util.List;

public class ActionSheetDialogList {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;

	private LinearLayout lLayout_content;
	private ListView	 sheet_listview;

	private boolean showTitle = false;
	private Display display;

	private ReceivePeopleListViewAdapter mAdapter;



	public ActionSheetDialogList(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ActionSheetDialogList builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
                R.layout.view_actionsheet1, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件

		lLayout_content = (LinearLayout) view
				.findViewById(R.id.lLayout_content);
		txt_title = (TextView) view.findViewById(R.id.txt_title);

		sheet_listview = (ListView) view.findViewById(R.id.sheet_listview);


		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);

		return this;
	}

	public ActionSheetDialogList setTitle(String title) {
		showTitle = true;
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

	public ActionSheetDialogList setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public ActionSheetDialogList setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}


	public ActionSheetDialogList setSheetItemList(List<Json2FirstPageShopBean> list)
	{
		int size = list.size();

		// 添加条目过多的时候控制高度
		if (size >= 7)
		{
			LayoutParams params = (LayoutParams) lLayout_content
					.getLayoutParams();
			params.height = display.getHeight() / 2;
            lLayout_content.setLayoutParams(params);
		}

		mAdapter = new ReceivePeopleListViewAdapter(list);
		sheet_listview.setAdapter(mAdapter);

		return this;
	}



	class ReceivePeopleListViewAdapter extends BaseAdapter
	{

		private List<Json2FirstPageShopBean> shopList;

		public ReceivePeopleListViewAdapter(List<Json2FirstPageShopBean> list)
		{
			this.shopList = list;
		}

		public void refresh(List<Json2FirstPageShopBean> list)
		{
			this.shopList = list;
			notifyDataSetChanged();
		}


		@Override
		public int getCount() {
			return shopList.size();
		}

		@Override
		public Object getItem(int position) {
			return shopList.get(position);
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
				view = View.inflate(context, R.layout.item_marker_shop_clicked_new, null);
                holder.item_clicked_shop_num = (TextView) view.findViewById(R.id.item_clicked_shop_num);//店铺排序
                holder.item_clicked_shop_photo = (ImageView) view.findViewById(R.id.item_clicked_shop_photo);//店铺照片
                holder.item_clicked_shop_name = (TextView) view.findViewById(R.id.item_clicked_shop_name);//店铺名字

                holder.item_clicked_shop_pingfen = (TextView) view.findViewById(R.id.item_clicked_shop_pingfen);//评分

                holder.item_clicked_shop_call_phone = (ImageView) view.findViewById(R.id.item_clicked_shop_photo);//电话



                holder.item_clicked_shop_dan_num = (TextView) view.findViewById(R.id.item_clicked_shop_dan_num);//接单数

                holder.clicked_shop_distance = (TextView) view.findViewById(R.id.clicked_shop_distance);//距离

                holder.clicked_shop = (RelativeLayout) view.findViewById(R.id.clicked_shop);//
                holder.make_phone = (LinearLayout) view.findViewById(R.id.make_phone);//
				view.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) view.getTag();
			}

            holder.item_clicked_shop_num.setText(shopList.get(position).getOrder() + "");
            //加载图片
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + shopList.get(position).getPathCode()+ "&fileReq.fileName=" + shopList.get(position).getShopPhoto();
            ImageLoaderTools.getInstance(context).displayImage(url, holder.item_clicked_shop_photo);

            holder.item_clicked_shop_name.setText(shopList.get(position).getShopName());
            holder.item_clicked_shop_pingfen.setText(shopList.get(position).getStar() + "级");

            //打电话
            holder.item_clicked_shop_call_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSheetItemClick != null) {
                        mSheetItemClick.onItemClick(shopList.get(position), SHOP_MAKE_CALL, position);
                    }
                }
            });
            //接单数
            holder.item_clicked_shop_dan_num.setText(shopList.get(position).getOrderNum()+ "单");

            holder.clicked_shop_distance.setText(shopList.get(position).getDistance() + "米");

            //店铺点击
            holder.item_clicked_shop_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSheetItemClick != null)
                    {
                        mSheetItemClick.onItemClick(shopList.get(position), SHOP_PHOTO_CLICK, position);
                    }

                }
            });
            holder.clicked_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSheetItemClick != null)
                    {
                        mSheetItemClick.onItemClick(shopList.get(position), SHOP_PHOTO_CLICK, position);
                    }
                }
            });
            holder.make_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSheetItemClick != null) {
                        mSheetItemClick.onItemClick(shopList.get(position), SHOP_MAKE_CALL, position);
                    }
                }
            });
            return view;
		}

		class ViewHolder
		{
			TextView        item_clicked_shop_num;//店铺排序
			ImageView       item_clicked_shop_photo;//店铺照片
			TextView        item_clicked_shop_name;//店铺名字
            TextView        item_clicked_shop_pingfen;//评分
            ImageView       item_clicked_shop_call_phone;//电话

            TextView        item_clicked_shop_dan_num;//接单数
            TextView        clicked_shop_distance;//距离
            LinearLayout    make_phone;//打电话
            RelativeLayout clicked_shop;//item

		}
	}

    int SHOP_PHOTO_CLICK = 1;
    int SHOP_MAKE_CALL   = 2;


	public void show() {
		dialog.show();
	}

    public void dismiss()
    {
        dialog.dismiss();
    }

	public interface OnSheetItemClickListener
    {
		void onItemClick(Json2FirstPageShopBean shop, int which, int position);
	}

    private OnSheetItemClickListener mSheetItemClick;

    public ActionSheetDialogList setOnItemClickListener(OnSheetItemClickListener listener)
    {
        this.mSheetItemClick = listener;
        return this;
    }

	public enum SheetItemColor {
		Blue("#037BFF"), Red("#FD4A2E");

		private String name;

		private SheetItemColor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
