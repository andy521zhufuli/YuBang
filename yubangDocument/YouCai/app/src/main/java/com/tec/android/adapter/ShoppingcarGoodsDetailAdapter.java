package com.tec.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sales.vo.LoadGoodsDetailResp;
import com.sales.vo.base.Order;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.ui.ShoppingCarActivity;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.GoodsInShoppingCarBean;
import com.tec.android.utils.bean.ShoppingcarSPBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.AlertDialogModifyNum;

import java.io.File;
import java.util.List;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.FileNameRuleImageUrl;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache;
import cn.trinea.android.common.service.impl.ImageSDCardCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.DownloadManagerPro;

/**
 * 购物车列表item详细信息适配器：用于购物车下拉刷新列表的适配器
 *
 * @author andy
 * @version 1.0
 * @created 2015-07-30
 */
public class ShoppingcarGoodsDetailAdapter extends BaseAdapter
{

    private Context mContext;
    private List<GoodsInShoppingCarBean> adapterList;
    private List<OrderGoods> mOrderGoodsList; //为了知道在购物车里面选中了什么商品 选中商品的数量
    private List<GoodsInShoppingCarBean> tempShoppingcarGoodsForCheckbox;

    /** cache folder path which be used when saving images **/
    public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
            .append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()).append(File.separator)
            .append("LifeShop").append(File.separator)
            .append("shoppingcar").append(File.separator)
            .append("shoppingcar").toString();
    public static final String TAG_CACHE            = "image_sdcard_cache";


    /**
     *
     */
    public ShoppingcarGoodsDetailAdapter()
    {
    }

    /**
     * 带一个参数的构造函数
     * @param context 上下文
     */
    public ShoppingcarGoodsDetailAdapter(Context context)
    {
        this.mContext = context;
    }

    /**
     * 带两个参数的构造函数
     * @param context 上下文
     * @param list 需要适配的列表
     */
    public ShoppingcarGoodsDetailAdapter(Context context, List<GoodsInShoppingCarBean> list, List<OrderGoods> orderGoodsList)
    {
        this.mContext = context;
        this.adapterList = list;
        this.mOrderGoodsList = orderGoodsList;
        IMAGE_CACHE.initData(mContext,TAG_CACHE);
        IMAGE_CACHE.setContext(mContext);
        IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);

        String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
        ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
        tempShoppingcarGoodsForCheckbox = shoppingcarGoods.getGoodsInShoppingCarBeans();


    }

    @Override
    public int getCount() {
        return adapterList.size();
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
            view = View.inflate(mContext, R.layout.item_shoppingcar_goods_detail, null);

            holder.item_goods_detail_checkBox = (CheckBox) view.findViewById(R.id.item_goods_detail_checkBox);
            holder.item_goods_detail_name = (TextView) view.findViewById(R.id.item_goods_detail_name);
            holder.item_goods_detail_price = (TextView) view.findViewById(R.id.item_goods_detail_price);
            holder.item_goods_detail_num = (TextView) view.findViewById(R.id.cart_single_product_et_num);
            holder.item_goods_detail_num_sub = (ImageButton) view.findViewById(R.id.cart_single_product_num_reduce);
            holder.item_goods_detail_num_plus = (ImageButton) view.findViewById(R.id.cart_single_product_num_add);
            holder.item_goods_detail_pic = (ImageView) view.findViewById(R.id.item_goods_detail_pic);
            holder.item_goods_detail_small_sum = (TextView) view.findViewById(R.id.item_goods_detail_small_sum);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) view.getTag();
        }



        holder.item_goods_detail_checkBox.setChecked(adapterList.get(position).isSelected());

        holder.item_goods_detail_name.setText(adapterList.get(position).getLoadGoodsDetailResp().getDetailGoods().getTitle());

        //当前商品的价格
        int itemPrice = adapterList.get(position).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();

        holder.item_goods_detail_price.setText("￥" + adapterList.get(position).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice());

        holder.item_goods_detail_num.setText(adapterList.get(position).getNum() + "");
        //当前商品的小计
        int currentSmallSum = itemPrice * adapterList.get(position).getNum();
        //小计多少钱
        holder.item_goods_detail_small_sum.setText("小计: " + currentSmallSum);


        //选择box的点击
        holder.item_goods_detail_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender = new Intent("shoppingcar.modify_goods");

                //判断是选中还是取消选中
                if(holder.item_goods_detail_checkBox.isChecked() == true){
                    //是选中的
                    sender.putExtra("choosed", true);
                    //取消使能
                    holder.item_goods_detail_checkBox.setEnabled(false);

                    adapterList.get(position).setSelected(true);
                    //修改后的数量   换成json
                    ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                    shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                    String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                    //讲修改后的值  也就是生成的json 保存在sp里面
                    SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                    //重新使能
                    holder.item_goods_detail_checkBox.setEnabled(true);
                    //发送广播
                    mContext.sendBroadcast(sender);

                }
                else{
                    //取消选中
                    sender.putExtra("choosed", false);
                    //取消使能
                    holder.item_goods_detail_checkBox.setEnabled(false);

                    adapterList.get(position).setSelected(false);
                    //修改后的数量   换成json
                    ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                    shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                    String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                    //讲修改后的值  也就是生成的json 保存在sp里面
                    SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                    //重新使能
                    holder.item_goods_detail_checkBox.setEnabled(true);
                    //发送广播
                    mContext.sendBroadcast(sender);
                }
            }
        });

        /**
         * 商品数量减一
         */
        holder.item_goods_detail_num_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("sub");
                //在这个操作未完成前不让用户继续点
                holder.item_goods_detail_num_sub.setEnabled(false);
                //当前购物车物品数量
                int currentNum = adapterList.get(position).getNum();
                //数量减少一个
                currentNum--;
                //判断数量是否为0  如果为了  就提示用户是否删除
                if (currentNum == 0)
                {
                    deleteGoodFromShoppingcar(holder,position);
                }
                else
                {
                    //商品数量不等于0 才会更新UI以及写到SP里面
                    //更新UI
                    holder.item_goods_detail_num.setText(currentNum + "");
                    //更新list里面的商品数量
                    adapterList.get(position).setNum(currentNum);
                    //修改后的数量   换成json
                    ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                    shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                    String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                    //讲修改后的值  也就是生成的json 保存在sp里面
                    SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                    //操作完成  用户可以继续点这个  添加物品
                    holder.item_goods_detail_num_sub.setEnabled(true);
                }


            }
        });
        /**
         * 商品数量加一
         */
        holder.item_goods_detail_num_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("plus");
                //在这个操作未完成前不让用户继续点
                holder.item_goods_detail_num_plus.setEnabled(false);
                //当前购物车物品数量
                int currentNum = adapterList.get(position).getNum();
                //数量加
                currentNum++;
                //更新UI
                holder.item_goods_detail_num.setText(currentNum + "");
                //当前数量写回list中
                adapterList.get(position).setNum(currentNum);
                //修改后的数量   换成json
                ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                //讲修改后的值  也就是生成的json 保存在sp里面
                SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                //操作完成  用户可以继续点这个  添加物品
                holder.item_goods_detail_num_plus.setEnabled(true);

            }
        });

        //修改商品数量   由系统输入法直接输入
        holder.item_goods_detail_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialogModifyNum(mContext, holder.item_goods_detail_num.getText().toString()).builder().setCancelable(false)
                        .setConformButton("确定", new AlertDialogModifyNum.OnClickListener() {
                            @Override
                            public void onClick(View view, String goodnum) {
                                //拿到了最新的饿商品数量
                                toastMgr.builder.display("拿到了最新的饿商品数量 ", 0);
                                //设置显示输入法为数字
                                int num = Integer.valueOf(goodnum);
                                holder.item_goods_detail_num.setText(num + "");
                                //商品数量为0 提示用户是否删除
                                if (num == 0)
                                {
                                    deleteGoodFromShoppingcar(holder, position);
                                }
                                else
                                {
                                    //商品数量大于0
                                    //修改购物车
                                    //当前数量写回list中
                                    holder.item_goods_detail_num_plus.setEnabled(false);
                                    adapterList.get(position).setNum(num);
                                    //修改后的数量   换成json
                                    ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                                    shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                                    String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                                    //讲修改后的值  也就是生成的json 保存在sp里面
                                    SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                                    //操作完成  用户可以继续点这个  添加物品
                                    holder.item_goods_detail_num_plus.setEnabled(true);

                                }

                            }
                        })
                        .setCancelButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //用户取消  不做任何改变
                            }
                        })
                        .show();

            }
        });
        //TODO  想想如何加载图片
        //holder.item_goods_detail_pic
        //IMAGE_SD_CACHE.get(adapterList.get(position).getLoadGoodsDetailResp().getDetailGoods().getImgurl(),holder.item_goods_detail_pic);
        IMAGE_CACHE.get("http://pic.baike.soso.com/p/20110505/bki-20110505023143-2085807902.jpg",holder.item_goods_detail_pic);

        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoodsid(adapterList.get(position).getLoadGoodsDetailResp().getDetailGoods().getGoodsid());
        orderGoods.setNum(Integer.valueOf(holder.item_goods_detail_num.getText().toString()));
        mOrderGoodsList.add(orderGoods);


        return view;
    }


    /**
     * Holder
     */
    static class Holder
    {
        CheckBox item_goods_detail_checkBox;//该商品是否选中
        TextView item_goods_detail_name;//商品名字
        TextView item_goods_detail_price;//商品价格
        TextView item_goods_detail_num;//商品数量
        ImageButton item_goods_detail_num_sub;//商品数量减
        ImageButton item_goods_detail_num_plus;//商品数量加
        ImageView item_goods_detail_pic;//商品图片
        TextView item_goods_detail_small_sum;//小计 多少钱

    }


    /**
     * 从购物车里面删除
     * @param holder
     * @param position
     */
    private void deleteGoodFromShoppingcar(final Holder holder, final int position)
    {
        new AlertDialog(mContext).builder().setTitle("删除当前物品")
                .setMsg("您将要把该商品从购物车中删除，点击取消可以保留。确定删除吗？")
                .setCancelable(false)
                .setPositiveButton("确认删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //将该商品从list里面删除
                        adapterList.remove(position);
                        //修改后的数量   换成json
                        ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                        shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                        String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                        SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                        //操作完成  用户可以继续点这个  添加物品
                        holder.item_goods_detail_num_sub.setEnabled(true);
                        ShoppingCarActivity.notifyActivtyListChanged();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消删除  就继续写入到sp里面
                        //更新UI
                        holder.item_goods_detail_num.setText("1");
                        //更新list里面的商品数量
                        adapterList.get(position).setNum(1);
                        //修改后的数量   换成json
                        ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
                        shoppingcarSPBean.setGoodsInShoppingCarBeans(adapterList);
                        String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
                        //讲修改后的值  也就是生成的json 保存在sp里面
                        SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
                        //操作完成  用户可以继续点这个  添加物品
                        holder.item_goods_detail_num_sub.setEnabled(true);
                    }
                }).show();

    }




    /** image cache **/
    public static final ImageCache IMAGE_CACHE = new ImageCache();

    static {
        ImageMemoryCache.OnImageCallbackListener imageCallBack = new ImageMemoryCache.OnImageCallbackListener() {

            private static final long serialVersionUID = 1L;

            // callback function before get image, run on ui thread
            @Override
            public void onPreGet(String imageUrl, View view) {
                // Log.e(TAG_CACHE, "pre get image");
                toastMgr.builder.display("image cache onPreGet", 0);
            }

            // callback function after get image successfully, run on ui thread
            @Override
            public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view, boolean isInCache) {
                // can be another view child, like textView and so on
                toastMgr.builder.display("image cache onget success", 0);
                if (view != null && loadedImage != null && view instanceof ImageView) {
                    ImageView imageView = (ImageView)view;
                    imageView.setImageBitmap(loadedImage);
                }
            }

            // callback function after get image failed, run on ui thread
            @Override
            public void onGetFailed(String imageUrl, Bitmap loadedImage, View view, FailedReason failedReason) {
                toastMgr.builder.display("image cache onGetFailed", 0);
                L.e(TAG_CACHE, new StringBuilder(128).append("get image ").append(imageUrl).append(" error")
                        .toString());
            }

            @Override
            public void onGetNotInCache(String imageUrl, View view) {

            }
        };
        IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
    }
}
