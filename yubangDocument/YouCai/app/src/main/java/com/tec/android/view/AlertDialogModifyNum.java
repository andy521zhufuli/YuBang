package com.tec.android.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.android.R;
import com.tec.android.utils.toastMgr;

/**
 * Created by andy on 15/8/18.
 */
public class AlertDialogModifyNum
{

    private final Context context;
    private final Display display;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Button view_modify_shoppingcar_goods_num_cancel_button;//取消按钮
    private Button view_modify_shoppingcar_goods_num_conform_button;//确定按钮

    private String mGoodsNum;//该商品数量


    private EditText    cart_single_product_et_num;//数量
    private ImageButton cart_single_product_num_reduce;//减号
    private ImageButton cart_single_product_num_add;//加号

    public AlertDialogModifyNum(Context context, String goodsNum) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        this.mGoodsNum = goodsNum;

        //获得屏幕尺寸大小
        display = windowManager.getDefaultDisplay();
    }


    public AlertDialogModifyNum builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_modify_shopppingcar_goods_num, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.view_modify_shoppingcar_goods_num_layout);
        view_modify_shoppingcar_goods_num_cancel_button = (Button) view.findViewById(R.id.view_modify_shoppingcar_goods_num_cancel_button);
        view_modify_shoppingcar_goods_num_conform_button = (Button) view.findViewById(R.id.view_modify_shoppingcar_goods_num_conform_button);

        cart_single_product_et_num = (EditText) view.findViewById(R.id.cart_single_product_et_num);
        //设置商品数量
        cart_single_product_et_num.setText(mGoodsNum);

        cart_single_product_num_reduce = (ImageButton) view.findViewById(R.id.cart_single_product_num_reduce);
        cart_single_product_num_add = (ImageButton) view.findViewById(R.id.cart_single_product_num_add);

        //商品数量减少按钮
        cart_single_product_num_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMgr.builder.display("减少", 0);
                int num = Integer.valueOf(mGoodsNum);
                if (num <= 1)
                {
                    num = 0;
                }
                else
                    num = num - 1;
                mGoodsNum = num + "";
                cart_single_product_et_num.setText(num + "");
            }
        });
        //商品数量添加anniu
        cart_single_product_num_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(mGoodsNum);
                num = num + 1;
                mGoodsNum = num + "";
                cart_single_product_et_num.setText(num + "");
            }
        });


        cart_single_product_et_num.addTextChangedListener(textWatcher);


        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ModifyNumAlertDialogStyle);
        dialog.setContentView(view);

        /// 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    /**
     * 设置是否去取消  点击外部 是否dismiss
     * @param cancel 是否
     * @return
     */
    public AlertDialogModifyNum setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }
    /**
     * 设置标题
     * @param title 标题
     * @return
     */
    public AlertDialogModifyNum setTitle(String title) {

        return this;
    }

    /**
     * 设置内容
     * @param msg 内容
     * @return
     */
    public AlertDialogModifyNum setMsg(String msg) {

        return this;
    }

    /**
     * 取消按钮
     * @param text 内容
     * @param listener    监听器
     * @return
     */
    public AlertDialogModifyNum setCancelButton(String text,
                                         final View.OnClickListener listener) {
        if ("".equals(text)) {
            view_modify_shoppingcar_goods_num_cancel_button.setText("取消");
        } else {
            view_modify_shoppingcar_goods_num_cancel_button.setText(text);
        }
        view_modify_shoppingcar_goods_num_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 确定按钮
     * @param text
     * @param listener
     * @return
     */
    public AlertDialogModifyNum setConformButton(String text,
                                         final OnClickListener listener) {
        if ("".equals(text)) {
            view_modify_shoppingcar_goods_num_conform_button.setText("确定");
        } else {
            view_modify_shoppingcar_goods_num_conform_button.setText(text);
        }
        view_modify_shoppingcar_goods_num_conform_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, mGoodsNum);
                dialog.dismiss();
            }
        });
        return this;
    }


    /**
     * 设置布局   那些显示 哪些不显示
     */
    private void setLayout() {



        //确定和取消都定义了 就都显示 并且设置点击时候的背景
        view_modify_shoppingcar_goods_num_cancel_button.setVisibility(View.VISIBLE);
        view_modify_shoppingcar_goods_num_conform_button.setVisibility(View.VISIBLE);

    }



    public void show() {
        setLayout();
        cart_single_product_et_num.setInputType(InputType.TYPE_CLASS_PHONE);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }


    public interface OnClickListener
    {
        void onClick(View view, String goodnum);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            mGoodsNum = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
