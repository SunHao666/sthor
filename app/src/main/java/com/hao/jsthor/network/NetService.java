package com.hao.jsthor.network;


import com.hao.jsthor.bean.FileBean;
import com.hao.jsthor.bean.WebContentBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NetService {
    //报损查询
    @GET("data/process")
    Call<FileBean> listBS(@QueryMap Map<String, Object> map);
    //退货查询
//    @GET("data/returnSupplier")
//    Call<BaseCallModel<THBean>> listTH(@QueryMap Map<String, Object> map);

    //
    @GET("data/webContent")
    Call<WebContentBean> webContent(@QueryMap Map<String, Object> map);
}
