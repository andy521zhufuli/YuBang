package com.car.yubangapk.ui.shoppingmallgoodsutil;

import com.car.yubangapk.json.bean.Json2ProductPackageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/27.
 *
 * 用户产品包界面的listview分类显示
 *
 */
public class Category {

    private String mCategoryName;
    private List<Json2ProductPackageBean> mCategoryItem = new ArrayList<>();

    public Category() {

    }

    public void setmCategoryName(String mCategroyName)
    {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(Json2ProductPackageBean pItemName) {
        mCategoryItem.add(pItemName);
    }

    /**
     *  获取Item内容
     *
     * @param pPosition
     * @return
     */
    public Json2ProductPackageBean getItem(int pPosition) {
        // Category排在第一位
        if (pPosition == 0) {
            return mCategoryItem.get(0);
        } else {
            return mCategoryItem.get(pPosition - 1);
        }
    }

    /**
     * 返回当前categoryName对应的所有内容
     * @return
     */
    public List<Json2ProductPackageBean> getItemList()
    {
        return mCategoryItem;
    }
    /**
     * 当前类别Item总数。
     * @return
     */
    public int getItemCount() {
        return mCategoryItem.size();
    }

}