package com.car.yubangapk.ui.shoppingmallgoodsutil;

import com.car.yubangapk.json.bean.Json2ProductPackageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andy on 16/4/28.
 */
public class GoodsCategoryHelper
{
    public static List<Category> productsToCategoryList(List<Json2ProductPackageBean> goodsList)
    {
        ArrayList<Category> listData = new ArrayList<Category>();

        Map<String, List<Json2ProductPackageBean>> map = new HashMap<>();

        for (Json2ProductPackageBean bean : goodsList)
        {
            if (map.containsKey(bean.getPackageName()))
            {
                map.get(bean.getPackageName()).add(bean);
            }
            else
            {
                List<Json2ProductPackageBean> list = new ArrayList<>();
                list.add(bean);
                map.put(bean.getPackageName(), list);
            }

        }

        for (Map.Entry<String, List<Json2ProductPackageBean>> entry : map.entrySet())
        {
            List<Json2ProductPackageBean> lisrt;
            String key = entry.getKey();
            lisrt = entry.getValue();
            Category category = new Category();
            category.setmCategoryName(key);
            for (Json2ProductPackageBean bean : lisrt)
            {
                category.addItem(bean);
            }
            listData.add(category);
        }
        return listData;
    }


    public static List<Json2ProductPackageBean> categoryListToProducts(List<Category> categories)
    {
        List<Json2ProductPackageBean> list = new ArrayList<>();
        for (Category cate:categories)
        {
            int count = cate.getItemCount();
            for (int i = 0; i < count; i++)
            {
                Json2ProductPackageBean bean = cate.getItem(i);
                list.add(bean);
            }
        }
        return list;
    }

}
