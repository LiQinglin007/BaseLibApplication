package com.lixiaomi.baselibapplication.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lixiaomi.baselibapplication.adapter.ExRecyclerAdapter;


import java.util.List;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-08-30<br>
 * Time: 13:56<br>
 * UpdateDescription：<br>
 */
public class Team extends AbstractExpandableItem<Team.Player> implements MultiItemEntity {

    String TeamName;
    List<Player> mPlayerList;

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public List<Player> getPlayerList() {
        return mPlayerList;
    }

    public void setPlayerList(List<Player> playerList) {
        mPlayerList = playerList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExRecyclerAdapter.TYPE_TEAM;
    }

    public static class Player implements MultiItemEntity {
        String PlayerName;
        int PlayerAge;

        public String getPlayerName() {
            return PlayerName;
        }

        public void setPlayerName(String playerName) {
            PlayerName = playerName;
        }

        public int getPlayerAge() {
            return PlayerAge;
        }

        public void setPlayerAge(int playerAge) {
            PlayerAge = playerAge;
        }

        @Override
        public int getItemType() {
            return ExRecyclerAdapter.TYPE_PLAYER;
        }
    }

}
