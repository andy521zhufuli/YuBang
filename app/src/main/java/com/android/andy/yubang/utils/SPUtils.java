package com.android.andy.yubang.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * SharedPreferences类:
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class SPUtils
{
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";
    public static final String FILE_NAME_ADDRESS = "address_data";
    public static final String FILE_USER_INFO = "user_info";
    public static final String FILE_SHOPPINGCAR_GOODS = "shoppingcar_goods";//购物车里面的商品


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context 上下文
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    /**
     * 把地址信息放在Sp里面
     * @param context 上下文信息
     * @param keyID 对应服务器的地址id
     * @param addressJson 地址信息 json格式
     */
    public static void putAddress(Context context, String keyID, String addressJson)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_ADDRESS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyID, addressJson);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取地址
     * @param context
     * @param keyID
     * @param defaultString
     * @return
     */
    public static String getAddress(Context context, String keyID, String defaultString)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_ADDRESS,context.MODE_PRIVATE);
        return sp.getString(keyID, defaultString);
    }

    /**
     * 删除key对应的地址
     * @param context 上下文
     * @param keyId 地址的id
     */
    public static void deleteAddress(Context context, String keyId)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_ADDRESS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(keyId);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清楚所有地址信息
     * @param context 上下文
     */
    public static void clearAllAddress(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_ADDRESS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }



    /**
     * 把用户信息放在Sp里面
     * @param context 上下文信息
     * @param keyID 对应服务器的地址id
     * @param addressJson 地址信息 json格式
     */
    public static void putUserInfo(Context context, String keyID, String addressJson)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyID, addressJson);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 拿到用户信息  判断 用户是否登陆可以用
     * @param context
     * @param keyID
     * @param defaultString
     * @return
     */
    public static String getUserInfo(Context context, String keyID, String defaultString)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER_INFO,context.MODE_PRIVATE);
        return sp.getString(keyID, defaultString);
    }
    /**
     * 删除用户信息   退出登陆的时候  用
     * @param context 上下文
     * @param keyId 地址的id
     */
    public static void deleteUserInfo(Context context, String keyId)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER_INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(keyId);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 用户点击加入购物车    把商品放在SP里面  购物车从这里读出信息
     * @param context 上下文信息
     * @param keyID 对应服务器的地址id
     * @param addressJson 地址信息 json格式
     */
    public static void putShoppingcarToSp(Context context, String keyID, String shoppingcar)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_SHOPPINGCAR_GOODS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyID, shoppingcar);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 从SP里面读出购物车里面应该有的信息
     * @param context
     * @param KeyID
     * @param defaultString
     * @return
     */
    public static String getShoppingcarGoodsFromSP(Context context, String KeyID, String defaultString)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_SHOPPINGCAR_GOODS,context.MODE_PRIVATE);
        return sp.getString(KeyID, defaultString);
    }

    /**
     * 移除某个key值已经对应的值
     * @param context 上下文
     * @param key key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context 上下文
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }

}