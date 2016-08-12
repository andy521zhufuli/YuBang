package cn.trinea.android.common.dao.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 15/8/23.
 * 请基于自己熟悉的平台技术（android/iOS），实现一个Window-LFU缓存（即置换指定时间内、按LFU规则排序淘汰数据）。要求对外提供以下接口：
 1､     可以指定cache大小
 2､     可以指定时间窗口（即window）
 3､     提供get/put/remove 数据访问方法
 4､     提供缓存命中率hitrate 数据访问方法

 闪退（Crash）是客户端程序在运行时遭遇无法处理的异常或错误时而退出应用程序的表现，请从crash发生的原因分类与解决方法、在出现crash后如何捕捉并分析异常这两个问题给出自己的解决方案。
 出现crash的原因分类:
 1.ANR
 虽然没有闪退,但是弹出让用户Force Close的窗口,
 原因有可能是:(1)主线程阻塞,超时,最长见 (2)系统反应迟钝
 解决方案:尽量异步,不要再主线程中做耗时操作.
 2.直接闪退:
 可能的原因:(1)一般会是空指针异常 (2) 未捕获的异常
 解决方案:调试程序,找出异常所在,修复异常
 3.程序逻辑错误导致闪退:
 可能出现的原因:(1)数组(列表等)越界 (2)堆栈溢出 (3)多线程控制的不好
 解决方案:对于数组越界检查程序看哪里会越界,调试(DEBUG),检查
 对于堆栈溢出,代码中尽量少用递归
 对于多线程,应该分析清楚线程执行的顺序,调度的时机
 4.内存溢出OOM
 可能出现的原因:(1)ListView加载多张图片,导致OOM
 解决方案:使用缓存,讲图片保存到本地
 ListView优化:在item不可见时停止加载
 crash捕获分析:
 这里我给出两种方案:
 1.使用第三方SDK
 因为有些第三方SDK已经做好了搜集异常,crash的接口,只需要集成在自己的app里面,
 一旦发生异常或者crash,便会自动搜集并上报服务器,可以在第三方SDK的后台查看到
 所有的crash,帮助我们定位到错误,修复错误
 2.自定义一个帮助类,比如CrashAndExceptionHelper,实现一个uncaughtException
 当程序发生异常的时候, 就由这个类来处理,手机用户设备信息,异常信息,并且将这些
 信息发送给后台,保存.
 这样就实现了捕获异常,并且分析异常.帮助我们解决crash

 客户端程序相对服务端应用程序来说，
 交互功能的变更通常需要经过应用市场的发布和用户主动下载更新才可以生效。
 请结合自己熟悉移动操作系统（Android/iOS任选其一）给出不依赖发布下载更新方式而完成用户交互界面变更的解决方案。

 我给出得一套解决方案是:使用webview,主要界面放在webview里面,如果需要变更,只需要
 更改后台html界面,客户端便可以不需要通过重新发布就可以使用新的交互界面.
 之前的项目做一个电商客户端--类似京东.商品列表,商品详情,订单,好友都放在webview里面,
 而native APP只需要完成基本的交互,比如button的点击下单以及与js的交互和逻辑.不需要太关注于
 native本身的界面以及更新,这样做的好处是:上架之后,一旦需要更新,只需要在后台界面html(或者jsp)
 中做相应改变,客户端即可在不重新发布的情况下用到新的交互界面.
 但是这样做有一个很致命的缺点, 就是用户交互体验会变差,用户点击没有feedback,体验较差.
 所在采用这个方案之前要权衡,是不是要经常迭代,改变用户交互界面,再来决定是不要用这种方案.
 */
public class Window_LFU
{
    private Context mContext;
    private int mCache_size = 100;//默认缓存是100
    private Map<String, Integer> cache;//保存cache 其中的Integer保存的是最近访问次数

    /**
     * 主要思路:
     * 将缓存放进map里面 map里面存放这个缓存最近被访问的次数 每一次put就将该缓存次数加1
     * 如果缓存已经满了, 就遍历map 看谁的访问频率最小, 就将他删除 调用remove
     */
    public Window_LFU(){}//默认无参构造函数
    public Window_LFU(Context mContext)//构造函数
    {
        this.mContext = mContext;
        cache = new HashMap<String, Integer>();
    }

    //对外提供接口,指定cache大小
    public void setmCache_size(int mCache_size)
    {
        this.mCache_size = mCache_size;
    }

    //取得缓存
    public Map<String, Integer> get()
    {
        return null;
    }
    //将缓存放进map里面
    public void put(Map<String, Integer> cache,String cacheName)
    {
        int cacheSize = cache.size();
        //当前缓存不满,直接加入
        if (cacheSize >=0 && cacheSize < mCache_size)
        {
            //如果当前缓存里面包含这个缓存,就对当前缓存的访问次数加1
            if (cache.containsKey(cacheName))
            {
                int frequence = cache.get(cacheName);
                frequence++;
                cache.put(cacheName, frequence);
            }
            //如果不包含, 那就直接添加  并且设置访问次数为1
            else
            {
                cache.put(cacheName,1);
            }
        }
        //如果当前缓存已经满了  那就遍历当前cache,找到最近不经常访问的那个缓存, 将它删除
        else
        {
            int min = 1000;
            String minName = null;
            for (Map.Entry<String, Integer> item : cache.entrySet())
            {
                int currentValue = item.getValue();
                if (min > currentValue)
                {
                    min = currentValue;
                    minName = item.getKey();//取得当前最小的那个访问变量的name
                }

            }
            remove(minName);

        }



    }
    //删除最近最不经常使用的缓存
    public void remove(String cacheName)
    {
        cache.remove(cacheName);
    }

}
