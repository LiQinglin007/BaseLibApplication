package com.lixiaomi.baselib.ui.dialog.dialoglist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.utils.recycler.OnItemClickListener;

import java.util.ArrayList;

/**
 * @describe：单项选择器的adapter<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/13<br>
 * @remarks：修改参数类型，可以只传递ArrayList<String>进来,单独的String集合不用再封装Bean了<br>
 * @changeTime:2018/4/29<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class DialogListAdapter<T> extends RecyclerView.Adapter<DialogListAdapter.DialogListViewHolder> {

    private final Context mContext;
    private ArrayList<T> mList = new ArrayList<>();
    private int mType = MiDialogList.MILIST_RETURN_SINGLE;
    private OnItemClickListener mClick;
    public DialogListAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据源
     *
     * @param mDataList 允许参数类型  String类型/实现了MiListInterface接口的bean对象
     */
    public void setList(ArrayList<T> mDataList, int returnType) {
        mList.clear();
        mList.addAll(mDataList);
        mType = returnType;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        mClick = mOnItemClickListener;
    }

    @Override
    public DialogListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DialogListAdapter.DialogListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final DialogListAdapter.DialogListViewHolder holder, final int position) {
        if (mList.get(position) instanceof MiListInterface) {
            holder.contentTv.setText(((MiListInterface) mList.get(position)).getMiDialigListShowData() + "");
        } else if (mList.get(position) instanceof String) {
            holder.contentTv.setText((String) mList.get(position) + "");
        } else {
            new RuntimeException("listDate must String or implements MiListInterface !");
        }
        //多选；文字在左边，上下居中   单选；文字居中
        holder.contentTv.setGravity(mType == MiDialogList.MILIST_RETURN_MULTIPLE ? Gravity.CENTER_VERTICAL : Gravity.CENTER);
        holder.flagImg.setVisibility(mType == MiDialogList.MILIST_RETURN_MULTIPLE ? View.INVISIBLE : View.GONE);

        if (mClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClick.onItemClick(v, position);
                    if (mType == MiDialogList.MILIST_RETURN_MULTIPLE) {
                        holder.flagImg.setVisibility(holder.flagImg.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DialogListViewHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        ImageView flagImg;

        public DialogListViewHolder(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.item_dialoglist_text);
            flagImg = (ImageView) itemView.findViewById(R.id.item_item_dialoglist_img);
        }
    }
}
