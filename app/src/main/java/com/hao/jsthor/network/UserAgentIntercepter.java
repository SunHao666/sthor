package com.hao.jsthor.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request =  chain.request().newBuilder()
                .addHeader("appId","RVMhou1g")
                .addHeader("appSecret","8hojokSJUUlzbammvI2XT2ckooVmd8X0m2K")
                .addHeader("mac",APPUtils.getNewMac())
                .build();
        return chain.proceed(request);
    }
}

