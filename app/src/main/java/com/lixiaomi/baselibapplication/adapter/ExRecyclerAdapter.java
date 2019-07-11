package com.lixiaomi.baselibapplication.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.bean.Team;

import java.util.List;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-08-30<br>
 * Time: 13:58<br>
 * UpdateDescription：<br>
 */
public class ExRecyclerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TEAM = 0;
    public static final int TYPE_PLAYER = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExRecyclerAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TEAM, R.layout.item_team);
        addItemType(TYPE_PLAYER, R.layout.item_player);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_TEAM:
                final Team team = (Team) item;
                holder.setText(R.id.item_team_tv, team.getTeamName())
                        .setImageResource(R.id.item_team_img, team.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        LogUtils.loge(pos + "pos");
                        if (team.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });

                break;
            case TYPE_PLAYER:
                final Team.Player player = (Team.Player) item;
                holder.setText(R.id.item_player_tv, "PlayName:" + player.getPlayerName() + " Age:" + player.getPlayerAge());
                break;
            default:
                break;
        }
    }
}
