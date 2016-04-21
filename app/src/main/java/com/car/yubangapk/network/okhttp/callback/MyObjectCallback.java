package com.car.yubangapk.network.okhttp.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class MyObjectCallback<T>
{
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request)
    {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter()
    {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress)
    {

    }
    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Object object, int position,Exception e);

    public abstract void onResponse(T response, Object object, int position);


    public static MyObjectCallback CALLBACK_DEFAULT = new MyObjectCallback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return null;
        }

        @Override
        public void onError(Call call, Object object, int position, Exception e)
        {

        }

        @Override
        public void onResponse(Object response, Object object, int position)
        {

        }
    };

}