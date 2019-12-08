package com.hao.jsthor.network;

import com.bumptech.glide.load.HttpException;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<BaseCallModel<T>> {

    public BaseCallback() {
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailed(int code, String msg);

    @Override
    public void onResponse(Call<BaseCallModel<T>> call, Response<BaseCallModel<T>> response) {
        BaseCallModel<T> baseCallModel = response.body();
        if (response.isSuccessful() && baseCallModel != null) {
            if (baseCallModel.code == 200) {
                onSuccess(baseCallModel.data);
            } else if (baseCallModel.code == 303) {
                //比如 做token无效统一处理
                onFailed(baseCallModel.code, baseCallModel.mess);
            } else {
                onFailed(baseCallModel.code, baseCallModel.mess);
            }
        } else {
            onFailed(response.code(), response.message());
        }
    }

    @Override
    public void onFailure(Call<BaseCallModel<T>> call, Throwable t) {
        if (t instanceof ConnectException) {
            onFailed(403, t.getMessage());
        } else if (t instanceof HttpException) {
            HttpException ex = (HttpException) t;
//            onFailed(ex.code(), ex.message());
        } else {
            onFailed(405, t.getMessage());
        }
    }
}