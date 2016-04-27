package com.car.yubangapk.adapter.category;

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
    private List<String> mCategoryItem = new ArrayList<String>();

    public Category(String mCategroyName) {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(String pItemName) {
        mCategoryItem.add(pItemName);
    }

    /**
     *  获取Item内容
     *
     * @param pPosition
     * @return
     */
    public String getItem(int pPosition) {
        // Category排在第一位
        if (pPosition == 0) {
            return mCategoryName;
        } else {
            return mCategoryItem.get(pPosition - 1);
        }
    }

    /**
     * 当前类别Item总数。Category也需要占用一个Item
     * @return
     */
    public int getItemCount() {
        return mCategoryItem.size() + 1;
    }

}