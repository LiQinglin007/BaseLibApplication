package com.lixiaomi.baselibapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaomi.baselib.utils.recycler.OnItemClickListener;
import com.lixiaomi.baselibapplication.R;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/16
 * 内容：
 * 最后修改：
 */

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Context mContext;

    public TestRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    ArrayList<String> mList = new ArrayList<>();

    public void setList(ArrayList<String> mDataList) {
        mList.clear();
        mList.addAll(mDataList);
        notifyDataSetChanged();
    }


    private OnItemClickListener mClick = null;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        mClick = mOnItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (mClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClick.onItemClick(v, position);
                }
            });
        }
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_test_adapter_tv);
        }
    }
}
