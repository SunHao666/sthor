package com.hao.jsthor.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnFileDownloadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.hao.jsthor.R;
import com.mingle.widget.LoadingView;

import java.io.File;

public class FileInfoActivity extends AppCompatActivity {

    private PDFView pdfView;
    private LinearLayout lay_video;
    private RelativeLayout lay_webview;
    private com.dueeeke.videoplayer.player.VideoView mVideoView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();
        openFile();
    }

    private void initView() {
        pdfView = findViewById(R.id.pdfView);
        
        lay_video = findViewById(R.id.lay_video);
        lay_webview = findViewById(R.id.lay_webview);

        mVideoView = findViewById(R.id.videoview);
    }

    private void initVideo() {
        StandardVideoController controller = new StandardVideoController(this);
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(false);

        PrepareView prepareView = new PrepareView(this);//准备播放界面
        ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
        Glide.with(this).load(R.mipmap.shipintu).into(thumb);
        controller.addControlComponent(prepareView);

        controller.addControlComponent(new CompleteView(this));//自动完成播放界面

        controller.addControlComponent(new ErrorView(this));//错误界面

        TitleView titleView = new TitleView(this);//标题栏
        controller.addControlComponent(titleView);

        //根据是否为直播设置不同的底部控制条
        boolean isLive = false;
        if (isLive) {
//                controller.addControlComponent(new LiveControlView(this));//直播控制条
        } else {
            VodControlView vodControlView = new VodControlView(this);//点播控制条
            //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
            controller.addControlComponent(vodControlView);
        }

        GestureView gestureControlView = new GestureView(this);//滑动控制视图
        controller.addControlComponent(gestureControlView);
        //根据是否为直播决定是否需要滑动调节进度
//            controller.setCanChangePosition(!isLive);

        //设置标题
//        titleView.setTitle(title);

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

        //如果你不想要UI，不要设置控制器即可
        mVideoView.setVideoController(controller);

        mVideoView.setUrl(url);

        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());/**/
        //播放状态监听
//            mVideoView.addOnVideoViewStateChangeListener(mOnVideoViewStateChangeListener);
//
        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
        mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());

        mVideoView.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }

    private void initpdf() {
        pdfView.fromUrl(url)
                .enableSwipe(true) // allows to block changing pages using swipe
                .defaultPage(0)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                }) // called after document is loaded and starts to be rendered
//                .onPageChange(this)
                .swipeHorizontal(false)
                .enableAntialiasing(true)
                .onFileDownload(new OnFileDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(File file) {

                    }
                })
                .loadFromUrl();;
    }

    private void openFile() {
        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)){
            finish();
            return;
        }
        
        if(url.endsWith(".pdf")){
            lay_video.setVisibility(View.GONE);
            lay_webview.setVisibility(View.VISIBLE);
            initpdf();
        }else if(url.endsWith(".mp4") || url.endsWith(".avi") || url.endsWith(".wmv")){
            lay_video.setVisibility(View.VISIBLE);
            lay_webview.setVisibility(View.GONE);
            initVideo();
        }

    }
}
