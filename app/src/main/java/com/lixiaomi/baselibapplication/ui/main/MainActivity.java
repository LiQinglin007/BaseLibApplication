package com.lixiaomi.baselibapplication.ui.main;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lixiaomi.baselib.base.BaseAppManager;
import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.eventmessage.MiEventMessage;
import com.lixiaomi.baselib.ui.bottom.BaseBottomActivity;
import com.lixiaomi.baselib.ui.bottom.BottomTabBean;
import com.lixiaomi.baselib.utils.MiFinalData;
import com.lixiaomi.baselib.utils.PreferenceUtils;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.mDesign.TabLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;


/**
 * MVP架构的例子，如果不使用mvp架构，在MiActivity中可以不传入接口泛型，也不用管creatPersenter()方法
 */
public class MainActivity extends BaseBottomActivity {
    boolean IsDownLoad = false;//是否正在下载更新

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        PreferenceUtils.setBoolean(MiFinalData.IS_OPEN_APP, true);
    }

    @Override
    public LinkedHashMap<BottomTabBean, Fragment> setItems() {
        BottomTabBean HomeBean = new BottomTabBean("首页", R.drawable.tab_index_hover, R.drawable.tab_index);
        BottomTabBean ReleaseBean = new BottomTabBean("", R.drawable.icon_release, R.drawable.icon_release);
        BottomTabBean MybBean = new BottomTabBean("我的", R.drawable.tab_my_hover, R.drawable.tab_my);
        final LinkedHashMap<BottomTabBean, Fragment> items = new LinkedHashMap<>();
        items.put(HomeBean, HomeFragment.getInstance());
        items.put(ReleaseBean, null);
        items.put(MybBean, MineFragment.getInstance());
        return items;
    }

    @Override
    public int setChooseIndex() {
        return 0;//设置选中的哪一个
    }

    @Override
    public int setClickedColor() {
        return 0;//设置选中的颜色
    }

    @Override
    public int setUnClickedColor() {
        return 0;//未选中的颜色
    }

    @Override
    public int setBackGroundColor() {
        return 0;//背景颜色
    }

    @Override
    public OnBottomItemClickListener setOnBottomItemClickListener() {
        return new OnBottomItemClickListener() {
            @Override
            public boolean click(int position) {
                if (IsDownLoad) {
                    T.shortToast(MainActivity.this, "正在更新，请稍等");
                    return false;
                } else {
                    if (position == 1) {
                        startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                        T.shortToast(MainActivity.this, "发布消息");
                        return false;
                    } else {
                        return true;//点击事件,返回true切换，返回false不切换
                    }
                }
            }
        };
    }

    @Override
    protected void initCompletion() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIsDownLoad(MiEventMessage message) {
        if (message.getMessageType() == MiEventMessage.CHECK_APP) {
            IsDownLoad = message.isMessageFlag();
        } else if (message.getMessageType() == MiEventMessage.EXIT_APP) {
            //去处理退出软件的功能
            BaseAppManager.getInstance().clear();
            finish();
//            setSwitchIndex(1);
        } else if (message.getMessageType() == MiEventMessage.SWITCH_FRAGMENT) {
            setSwitchIndex(message.getMessageInt());
        }
    }


    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
