package com.hao.jsthor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hao.jsthor.R;
import com.hao.jsthor.bean.WebContentBean;
import com.hao.jsthor.network.NetManager;
import com.mingle.widget.LoadingView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebFragment extends androidx.fragment.app.Fragment {

    private WebView webView;
    private LoadingView progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, null);
        webView = view.findViewById(R.id.webView);
        progressBar = view.findViewById(R.id.probar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWebview();
        request();
    }

    private void request() {
        Map<String,Object> map = new HashMap<>();
        map.put("pageNo",1);
        map.put("pageSize",20);
        NetManager.getInstance().api().webContent(map)
                .enqueue(new Callback<WebContentBean>() {
                    @Override
                    public void onResponse(Call<WebContentBean> call, Response<WebContentBean> response) {
                        WebContentBean body = response.body();
                        int code = body.getCode();
                        if(code == 200){
                            try {
                                webView.loadUrl(body.getData().getList().get(0).getLink_url());
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getActivity(),body.getMess(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WebContentBean> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initWebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);

            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
