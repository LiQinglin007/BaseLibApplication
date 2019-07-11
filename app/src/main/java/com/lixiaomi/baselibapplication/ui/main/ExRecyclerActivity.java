package com.lixiaomi.baselibapplication.ui.main;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.ExRecyclerAdapter;
import com.lixiaomi.baselibapplication.bean.Team;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.util.ArrayList;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-08-30<br>
 * Time: 13:51<br>
 * UpdateDescription：<br>
 */
public class ExRecyclerActivity extends XMBaseActivity {
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    ExRecyclerAdapter mExRecyclerAdapter;
    ArrayList<MultiItemEntity> mArrayList = new ArrayList<>();

    @Override
    protected Object setLayout() {
        return R.layout.activity_exrecycler;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            Team team = new Team();
            team.setTeamName("湖人" + i);

            ArrayList<Team.Player> mPlayer = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Team.Player player = new Team.Player();
                player.setPlayerAge(j);
                player.setPlayerName("小米" + j);
                mPlayer.add(player);
                team.addSubItem(player);
            }
            team.setPlayerList(mPlayer);
            mArrayList.add(team);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.re_toolbar);
        mRecyclerView = findViewById(R.id.re_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mExRecyclerAdapter = new ExRecyclerAdapter(mArrayList);
        mRecyclerView.setAdapter(mExRecyclerAdapter);
        mExRecyclerAdapter.setNewData(mArrayList);
        mExRecyclerAdapter.expandAll();
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }
}
