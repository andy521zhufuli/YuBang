package com.car.yubangapk.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择支付方式
 */
public class ActionSheetChoosePayMethodDialog {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;
	private TextView txt_cancel;
	private TextView txt_conform;

	private LinearLayout my_order_choose_online;//在线支付
	private LinearLayout my_order_choose_offline;//到店支付

	private ImageView	my_order_online_pay_imageview;
	private ImageView	my_order_offline_pay_imageview;

	private LinearLayout lLayout_content;
	private ScrollView sLayout_content;
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private Display display;

	public ActionSheetChoosePayMethodDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ActionSheetChoosePayMethodDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_actionsheet_choose_pay_method, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());
		initView(view);

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


	private static int CURRENT_PAY_METHOD = 0;
	public static int ONLINE_PAY = 1;
	public static int OFFLINE_PAY = 2;
	private void initView(View view)
	{
		// 获取自定义Dialog布局中的控件
		sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);

		lLayout_content = (LinearLayout) view
				.findViewById(R.id.lLayout_content);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		txt_conform = (TextView) view.findViewById(R.id.txt_conform);
		txt_conform.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				if (mConformClickListener!=null)
				{
					mConformClickListener.onConformClick(CURRENT_PAY_METHOD);
				}
			}
		});

		my_order_choose_online = (LinearLayout) view.findViewById(R.id.my_order_choose_online);
		my_order_choose_online.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//CURRENT_PAY_METHOD = ONLINE_PAY;
				toastMgr.builder.display("当前值支持到店支付, 请选择到店支付", 1);
				//显示当前的支付方式
				//setPayMethodType();
			}
		});
		my_order_choose_offline = (LinearLayout) view.findViewById(R.id.my_order_choose_offline);
		my_order_choose_offline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				CURRENT_PAY_METHOD = OFFLINE_PAY;
				//显示当前的支付方式
				setPayMethodType();
			}
		});
		my_order_online_pay_imageview = (ImageView) view.findViewById(R.id.my_order_online_pay_imageview);
		my_order_offline_pay_imageview = (ImageView) view.findViewById(R.id.my_order_offline_pay_imageview);
	}


	/**
	 * 设置当前支付方式, 如果设置了, 就打对号
	 */
	private void setPayMethodType()
	{
		if (CURRENT_PAY_METHOD == ONLINE_PAY)
		{
			my_order_online_pay_imageview.setImageDrawable(context.getResources().getDrawable(R.mipmap.button_l_01));
			my_order_offline_pay_imageview.setImageDrawable(context.getResources().getDrawable(R.mipmap.button_l_02));
		}
		else
		{
			my_order_online_pay_imageview.setImageDrawable(context.getResources().getDrawable(R.mipmap.button_l_02));
			my_order_offline_pay_imageview.setImageDrawable(context.getResources().getDrawable(R.mipmap.button_l_01));
		}
	}

	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	public ActionSheetChoosePayMethodDialog setTitle(String title) {
		showTitle = true;
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

	/**
	 * 是否可取消
	 * @param cancel
	 * @return
	 */
	public ActionSheetChoosePayMethodDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	/**
	 * 外部点击取消
	 * @param cancel
	 * @return
	 */
	public ActionSheetChoosePayMethodDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	/**
	 *
	 * @param strItem
	 *            条目名称
	 * @param color
	 *            条目字体颜色，设置null则默认蓝色
	 * @param listener
	 * @return
	 */
	private ActionSheetChoosePayMethodDialog addSheetItem(String strItem, SheetItemColor color,
										  OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}

	private OnConformClickListener mConformClickListener;

	/**
	 * 设置确认按钮点击的监听器
	 * @param conformClickListener
	 * @return
	 */
	public ActionSheetChoosePayMethodDialog setConformClickListener(OnConformClickListener conformClickListener)
	{
		this.mConformClickListener = conformClickListener;
		return this;
	}
	/** 设置条目布局 */
	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();

		// TODO 高度控制，非最佳解决办法
		// 添加条目过多的时候控制高度
		if (size >= 7) {
			LayoutParams params = (LayoutParams) sLayout_content
					.getLayoutParams();
			params.height = display.getHeight() / 2;
			sLayout_content.setLayoutParams(params);
		}

		// 循环添加条目
		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			String strItem = sheetItem.name;
			SheetItemColor color = sheetItem.color;
			final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

			TextView textView = new TextView(context);
			textView.setText(strItem);
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER);

			// 背景图片
			if (size == 1) {
				if (showTitle) {
					textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
				} else {
					textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
				}
			} else {
				if (showTitle) {
					if (i >= 1 && i < size) {
						textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				} else {
					if (i == 1) {
						textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
					} else if (i < size) {
						textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				}
			}

			// 字体颜色
			if (color == null) {
				textView.setTextColor(Color.parseColor(SheetItemColor.Blue
						.getName()));
			} else {
				textView.setTextColor(Color.parseColor(color.getName()));
			}

			// 高度
			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, height));

			// 点击事件
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onClick(index);
					dialog.dismiss();
				}
			});

			lLayout_content.addView(textView);
		}
	}

	public void show() {
		setSheetItems();
		dialog.show();
	}

	public interface OnSheetItemClickListener {
		void onClick(int which);
	}


	public interface OnConformClickListener
	{
		void onConformClick(int payType);
	}




	public class SheetItem {
		String name;
		OnSheetItemClickListener itemClickListener;
		SheetItemColor color;

		public SheetItem(String name, SheetItemColor color,
						 OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.itemClickListener = itemClickListener;
		}
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
