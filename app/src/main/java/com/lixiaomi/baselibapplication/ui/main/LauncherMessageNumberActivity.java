package com.lixiaomi.baselibapplication.ui.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.utils.MiRandomUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.utils.NotificationUtil;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-08-08<br>
 * Time: 11:30<br>
 * UpdateDescription：<br>
 */
public class LauncherMessageNumberActivity extends XMBaseActivity implements View.OnClickListener {
    Button mSetNumber;
    Button mRemove;
    Button mClearn;

    @Override
    protected Object setLayout() {
        return R.layout.activity_launcher_number;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mSetNumber = findViewById(R.id.add_number);
        mRemove = findViewById(R.id.remove_number);
        mClearn = findViewById(R.id.clearn_number);

        mSetNumber.setOnClickListener(this);
        mRemove.setOnClickListener(this);
        mClearn.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_number:
                String random = MiRandomUtils.getRandom(5, MiRandomUtils.NUMBER);
                Intent mainIntent = new Intent(LauncherMessageNumberActivity.this, LauncherMessageNumberActivity.class);
                mainIntent.putExtra("notificationId", random);
                PendingIntent pendingIntent = PendingIntent.getActivity(LauncherMessageNumberActivity.this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationUtil.sendNotificationMyView("报警通知", "有新的报警通知" + "条", "报警通知",
                        Integer.parseInt(random), R.mipmap.ic_launcher, pendingIntent, LauncherMessageNumberActivity.this);
//                BadgeUtil2.setBadgeCount(LauncherMessageNumberActivity.this,10,R.drawable.test1);

                break;
            case R.id.remove_number:
                ShortcutBadger.removeCount(LauncherMessageNumberActivity.this); //for 1.1.4+
//                ShortcutBadger.with(getApplicationContext()).remove();  //for 1.1.3

                //                int badgeCount = 1;
//                ShortcutBadger.applyCount(LauncherMessageNumberActivity.this, badgeCount); //for 1.1.4+
//                ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
                break;
            case R.id.clearn_number:
                ShortcutBadger.applyCount(LauncherMessageNumberActivity.this, 0); //for 1.1.4+
//                ShortcutBadger.with(getApplicationContext()).count(0); //for 1.1.3
                break;
            default:
                break;
        }
    }
}
