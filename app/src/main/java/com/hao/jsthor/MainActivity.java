package com.hao.jsthor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.jsthor.event.WebEvent;
import com.hao.jsthor.fragment.FileFragment;
import com.hao.jsthor.fragment.WebFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fragmentManager;

    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();

    private int currentIndex = 0;
    private LinearLayout layBottom;
    private TextView btn_left;
    private TextView btn_right;
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private ImageView top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initView(Bundle savedInstanceState) {
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        layBottom = findViewById(R.id.lay_bottom);
        top = findViewById(R.id.top);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用

            fragments.add(new FileFragment());
            fragments.add(new WebFragment());

            showFragment();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_left:
                layBottom.setBackgroundResource(R.mipmap.bottom_nav_left);
                currentIndex = 0;
                layBottom.setVisibility(View.GONE);
                top.setVisibility(View.GONE);
                break;
            case R.id.btn_right:
                layBottom.setBackgroundResource(R.mipmap.bottom_nav_right);
                currentIndex = 1;
                layBottom.setVisibility(View.GONE);
                top.setVisibility(View.GONE);
                break;

        }

        showFragment();

    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.content,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag

        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }

        currentFragment = fragments.get(currentIndex);

        transaction.commit();

    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){

        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {

            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }
        }

        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(WebEvent message) {

        layBottom.setVisibility(View.GONE);
        top.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if(layBottom.getVisibility() == View.GONE){
            layBottom.setVisibility(View.VISIBLE);
            top.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }
}
