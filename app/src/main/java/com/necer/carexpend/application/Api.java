package com.necer.carexpend.application;


import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * des:retorfit api
 * Created by xsf
 * on 2016.06.15:47
 */
public class Api {
    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 10;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 10;
    public Retrofit retrofit;
    public ApiService carService;


    /**
     * 设缓存有效期为两天
     */
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;


    private Api() {


        //缓存
        File cacheFile = new File(CarApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //10Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        carService = retrofit.create(ApiService.class);
    }


    private static Api defaultApi;

    public static ApiService getDefaultService() {
        if (defaultApi == null) {
            defaultApi = new Api();
        }
        return defaultApi.carService;
    }



    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            String cache = TextUtils.isEmpty(cacheControl) ? " max-age = 10" : cacheControl;//没有配置headers，则缓存10秒
            return originalResponse.newBuilder()
                    .header("Cache-Control", cache)
                    .removeHeader("Pragma")
                    .build();
        }
    };


}