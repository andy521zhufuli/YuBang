package com.car.yubangapk.network.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.car.yubangapk.network.okhttp.builder.GetBuilder;
import com.car.yubangapk.network.okhttp.builder.HeadBuilder;
import com.car.yubangapk.network.okhttp.builder.OtherRequestBuilder;
import com.car.yubangapk.network.okhttp.builder.PostFileBuilder;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.builder.PostStringBuilder;
import com.car.yubangapk.network.okhttp.callback.Callback;
import com.car.yubangapk.network.okhttp.callback.MyCallback;
import com.car.yubangapk.network.okhttp.callback.MyObjectCallback;
import com.car.yubangapk.network.okhttp.callback.MyProductPkgCallback;
import com.car.yubangapk.network.okhttp.cookie.CookieJarImpl;
import com.car.yubangapk.network.okhttp.cookie.store.CookieStore;
import com.car.yubangapk.network.okhttp.cookie.store.HasCookieStore;
import com.car.yubangapk.network.okhttp.cookie.store.MemoryCookieStore;
import com.car.yubangapk.network.okhttp.cookie.store.PersistentCookieStore;
import com.car.yubangapk.network.okhttp.https.HttpsUtils;
import com.car.yubangapk.network.okhttp.log.LoggerInterceptor;
import com.car.yubangapk.network.okhttp.request.RequestCall;
import com.car.yubangapk.network.okhttp.utils.Exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils
{

    public static final long DEFAULT_MILLISECONDS = 10000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            //cookie enabled
            okHttpClientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            });

            mOkHttpClient = okHttpClientBuilder.build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

        init();
    }


    public OkHttpUtils(OkHttpClient okHttpClient, Context context)
    {
        if (okHttpClient == null)
        {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            //cookie enabled
            okHttpClientBuilder.cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            });

            mOkHttpClient = okHttpClientBuilder.build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

        init();
    }

    private void init()
    {
        mDelivery = new Handler(Looper.getMainLooper());
    }


    public OkHttpUtils debug(String tag)
    {
        mOkHttpClient = getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, false)).build();
        return this;
    }

    /**
     * showResponse may cause error, but you can try .
     *
     * @param tag
     * @param showResponse
     * @return
     */
    public OkHttpUtils debug(String tag, boolean showResponse)
    {
        mOkHttpClient = getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, showResponse)).build();
        return this;
    }

    public static OkHttpUtils getInstance(OkHttpClient okHttpClient)
    {

        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);

                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(null);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(null, context);
                }
            }
        }
        return mInstance;
    }


    public Handler getDelivery()
    {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }

    public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post()
    {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put()
    {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head()
    {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete()
    {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch()
    {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                sendFailResultCallback(call, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response)
            {
                if (response.code() >= 400 && response.code() <= 599)
                {
                    try
                    {
                        sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return;
                }

                try
                {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalCallback);
                }

            }
        });
    }


    public void executePP(final RequestCall requestCall, MyProductPkgCallback callback, final String pkgName)
    {
        if (callback == null)
            callback = MyProductPkgCallback.CALLBACK_DEFAULT;
        final MyProductPkgCallback finalCallback = callback;


        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendPPFailResultCallback(call, pkgName, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendPPFailResultCallback(call, pkgName,new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendMyPPSuccessResultCallback(o, pkgName, finalCallback);
                } catch (Exception e) {
                    sendPPFailResultCallback(call, pkgName,e, finalCallback);
                }

            }
        });
    }

    public void executeObj(final RequestCall requestCall, MyObjectCallback callback, final Object pkgName, final int position)
    {
        if (callback == null)
            callback = MyObjectCallback.CALLBACK_DEFAULT;
        final MyObjectCallback finalCallback = callback;


        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendObjFailResultCallback(call, pkgName, position, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendObjFailResultCallback(call, pkgName,position, new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendMyObjSuccessResultCallback(o, pkgName,position, finalCallback);
                } catch (Exception e) {
                    sendObjFailResultCallback(call, pkgName, position, e, finalCallback);
                }

            }
        });
    }

    public void executeParam(final RequestCall requestCall, MyCallback callback, final int pos)
    {
        if (callback == null)
            callback = MyCallback.CALLBACK_DEFAULT;
        final MyCallback finalCallback = callback;


        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendMyFailResultCallback(call, pos, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendMyFailResultCallback(call, pos,new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendMySuccessResultCallback(o, pos, finalCallback);
                } catch (Exception e) {
                    sendMyFailResultCallback(call, pos,e, finalCallback);
                }

            }
        });
    }

    public CookieStore getCookieStore()
    {
        final CookieJar cookieJar = mOkHttpClient.cookieJar();
        if (cookieJar == null)
        {
            Exceptions.illegalArgument("you should invoked okHttpClientBuilder.cookieJar() to set a cookieJar.");
        }
        if (cookieJar instanceof HasCookieStore)
        {
            return ((HasCookieStore) cookieJar).getCookieStore();
        } else
        {
            return null;
        }
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendPPFailResultCallback(final Call call, final String pkgName, final Exception e, final MyProductPkgCallback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, pkgName ,e);
                callback.onAfter();
            }
        });
    }
    public void sendObjFailResultCallback(final Call call, final Object pkgName, final int position, final Exception e, final MyObjectCallback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, pkgName , position,e);
                callback.onAfter();
            }
        });
    }

    public void sendMyObjSuccessResultCallback(final Object object, final Object pkgName, final int position, final MyObjectCallback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object,pkgName, position);
                callback.onAfter();
            }
        });
    }

    public void sendMyFailResultCallback(final Call call, final int pos, final Exception e, final MyCallback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, pos ,e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void sendMyPPSuccessResultCallback(final Object object, final String pkgName,final MyProductPkgCallback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object,pkgName);
                callback.onAfter();
            }
        });
    }

    public void sendMySuccessResultCallback(final Object object, final int pos,final MyCallback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object,pos);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }


    /**
     * for https-way authentication
     *
     * @param certificates
     */
    public void setCertificates(InputStream... certificates)
    {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates, null, null);

        OkHttpClient.Builder builder = getOkHttpClient().newBuilder();
        builder = builder.sslSocketFactory(sslSocketFactory);
        mOkHttpClient = builder.build();


    }

    /**
     * for https mutual authentication
     *
     * @param certificates
     * @param bksFile
     * @param password
     */
    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, bksFile, password))
                .build();
    }

    public void setHostNameVerifier(HostnameVerifier hostNameVerifier)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .hostnameVerifier(hostNameVerifier)
                .build();
    }

    public void setConnectTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }

    public void setReadTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .readTimeout(timeout, units)
                .build();
    }

    public void setWriteTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .writeTimeout(timeout, units)
                .build();
    }


    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

