package com.hao.jsthor.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    public static NetManager instance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private Long TIMEOUT = 20l;
    private NetService netService;

    private NetManager() {
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Contant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
        netService = retrofit.create(NetService.class);
    }

    private OkHttpClient getClient() {
        okHttpClient = new OkHttpClient.Builder()
                .callTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new UserAgentIntercepter())
                .addInterceptor(new RetrofitLogInterceptor())
                .build();
        return okHttpClient;
    }

    public static NetManager getInstance() {
        if(instance == null){
            synchronized (NetManager.class){
                if(instance == null){
                    instance = new NetManager();
                }
            }
        }
        return instance;
    }

    public NetService api(){
        if(netService == null){
            throw new RuntimeException("retrofit need init");
        }
        return netService;
    }
}
