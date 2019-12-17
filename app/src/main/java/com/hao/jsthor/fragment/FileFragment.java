package com.hao.jsthor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnFileDownloadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.hao.jsthor.R;
import com.hao.jsthor.activity.FileInfoActivity;
import com.hao.jsthor.adapter.FileAdapter;
import com.hao.jsthor.bean.FileBean;
import com.hao.jsthor.event.WebEvent;
import com.hao.jsthor.network.NetManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 文件Fragment
 */
public class FileFragment extends androidx.fragment.app.Fragment {

    private VideoView mVideoView;
    private String title;
    private String url;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<FileBean.DataBean.ListBean> data = new ArrayList<>();
    private FileAdapter adapter;
    private int page = 1;
    private LinearLayout lay_video,lay_rec;
    private FileBean body;
    private int refreshStatus = 1;
    private LinearLayout layPDF;
    private PDFView pdfView;
    private LinearLayout layImage;
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, null);
        mVideoView = view.findViewById(R.id.videoview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        lay_video = view.findViewById(R.id.lay_video);
        lay_rec = view.findViewById(R.id.lay_rec);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FileAdapter(getActivity(),data);
        recyclerView.setAdapter(adapter);

        layPDF = view.findViewById(R.id.lay_pdf);
        pdfView = view.findViewById(R.id.pdfView);

        layImage = view.findViewById(R.id.lay_Image);
        image = view.findViewById(R.id.image);


        initListener();
        refreshStatus = 1;
        request();
        return view;
    }

    private void initListener() {
        adapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), FileInfoActivity.class);
                intent.putExtra("url", body.getResUrl()+data.get(position).getFile_path());
                startActivity(intent);
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshStatus = 1;
                request();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshStatus = 2;
                request();
                refreshlayout.finishLoadMore();
            }
        });
    }

    //请求接口
    private void request() {
        if(refreshStatus == 1){
            page = 1;
            data.clear();
            adapter.notifyDataSetChanged();
        }else{

        }
        Map<String,Object> map = new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",20);
        NetManager.getInstance().api().listBS(map).enqueue(new Callback<FileBean>() {
            @Override
            public void onResponse(Call<FileBean> call, Response<FileBean> response) {
                if(response.isSuccessful()){
                    body = response.body();
                    if(body.getCode() == 200){
                        int row = body.getData().getTotalRow();
                        if(row == 1){
                            initRowOne();
                        }else{
                            initRowMore();
                        }
                    }else {
                        Toast.makeText(getContext(), body.getMess(),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<FileBean> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRowMore() {
        lay_rec.setVisibility(View.VISIBLE);
        lay_video.setVisibility(View.GONE);
        data.addAll(body.getData().getList());
        adapter.notifyDataSetChanged();
        if(body.getData().getList().size() < 20){
            refreshLayout.setEnableLoadMore(false);
        }else{
            page++;
            refreshLayout.setEnableLoadMore(true);
        }
    }
    /**
    * @description 一条数据
    * @date: 2019/12/16 16:41
    * @author: sunhao
    */
    private void initRowOne() {

        String file_path = body.getData().getList().get(0).getFile_path();
        url = body.getResUrl()+ body.getData().getList().get(0).getFile_path();
        if(file_path.endsWith(".mp4") || file_path.endsWith(".avi")|| file_path.endsWith(".wmv")){
            lay_rec.setVisibility(View.GONE);
            lay_video.setVisibility(View.VISIBLE);
            layPDF.setVisibility(View.GONE);
            layImage.setVisibility(View.GONE);
            EventBus.getDefault().post(new WebEvent());
            Log.e("video",url);
            initVideo();
        }else if(file_path.endsWith(".pdf")){
            lay_rec.setVisibility(View.GONE);
            lay_video.setVisibility(View.GONE);
            layPDF.setVisibility(View.VISIBLE);
            layImage.setVisibility(View.GONE);
            initpdf();
        }else if(file_path.endsWith(".jpg") || file_path.endsWith(".png")){
            lay_rec.setVisibility(View.GONE);
            lay_video.setVisibility(View.GONE);
            layPDF.setVisibility(View.GONE);
            layImage.setVisibility(View.VISIBLE);
            initImage();
        }
    }

    private void initImage() {
        Glide.with(getActivity()).load(url).into(image);
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
                .loadFromUrl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initVideo() {
        StandardVideoController controller = new StandardVideoController(getActivity());
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(false);

        PrepareView prepareView = new PrepareView(getActivity());//准备播放界面
        ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
        Glide.with(this).load(R.mipmap.shipintu).into(thumb);
        controller.addControlComponent(prepareView);

        controller.addControlComponent(new CompleteView(getActivity()));//自动完成播放界面

        controller.addControlComponent(new ErrorView(getActivity()));//错误界面

        TitleView titleView = new TitleView(getActivity());//标题栏
        controller.addControlComponent(titleView);

        //根据是否为直播设置不同的底部控制条
            boolean isLive = false;
            if (isLive) {
//                controller.addControlComponent(new LiveControlView(this));//直播控制条
            } else {
                VodControlView vodControlView = new VodControlView(getActivity());//点播控制条
                //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
                controller.addControlComponent(vodControlView);
            }

        GestureView gestureControlView = new GestureView(getActivity());//滑动控制视图
        controller.addControlComponent(gestureControlView);
        //根据是否为直播决定是否需要滑动调节进度
//            controller.setCanChangePosition(!isLive);

        //设置标题
        titleView.setTitle(title);

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
            Log.e("tag","video resume");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
            Log.e("tag","video pause");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
            Log.e("tag","video release");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("taa","visible"+hidden);
        if(hidden){
            if (mVideoView != null) {
                mVideoView.pause();
                Log.e("tag","video pause");
            }
        }else{
            if (mVideoView != null) {
                mVideoView.resume();
                Log.e("tag","video resume");
            }

        }
    }
}
