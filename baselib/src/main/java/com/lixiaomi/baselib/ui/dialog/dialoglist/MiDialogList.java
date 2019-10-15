package com.lixiaomi.baselib.ui.dialog.dialoglist;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.utils.ScreenUtils;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselib.utils.recycler.OnItemClickListener;

import java.util.ArrayList;

/**
 * @describe：一个选择控件(单选和多选)<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/13<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 * @remarks：添加单例模式<br>
 * @changeTime:2019/2/14<br>
 */
public class MiDialogList<T> {

    /**
     * 在底部显示
     */
    public static final int MILIST_DIALOG_BOTTOM = Gravity.BOTTOM;
    /**
     * 在中间显示
     */
    public static final int MILIST_DIALOG_CENTER = Gravity.CENTER;
    /**
     * 一条返回结果
     */
    public static final int MILIST_RETURN_SINGLE = 0x1;
    /**
     * 多条返回结果
     */
    public static final int MILIST_RETURN_MULTIPLE = 0x2;

    private static Dialog mDialog;
    private static TextView mTitleTv;
    private static RecyclerView mRecyclerView;
    private static LinearLayout mBottomLy;
    private static TextView mOkTv;
    private static TextView mCannleTv;
    private static TextView mOkTopTv;
    private static TextView mCannleTopTv;

    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * 选择类型  多选或者单选
     */
    private static int mReturnType = MILIST_RETURN_SINGLE;
    /**
     * 显示位置
     */
    private static int mGravity = MILIST_DIALOG_BOTTOM;
    /**
     * 数据源
     */
    private ArrayList<T> mDataList = new ArrayList<>();
    /**
     * 回调
     */
    private static OnDialogListCallback mOnDialogListCallback;

    /**
     * 是否显示标题
     */
    private static boolean mShowTitle = false;
    /**
     * 返回值
     */
    private static ArrayList<Integer> mReturnData = new ArrayList<>();


    /**
     * @param context 上下文
     */
    public MiDialogList(Context context) {
        mContext = context;
    }

    public MiDialogList builder() {
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_choose_list, null);

        mTitleTv = (TextView) dialogView.findViewById(R.id.dialog_list_title);
        mRecyclerView = (RecyclerView) dialogView.findViewById(R.id.dialog_list_recycler);
        mBottomLy = (LinearLayout) dialogView.findViewById(R.id.dialog_list_ly);
        mOkTv = (TextView) dialogView.findViewById(R.id.dialog_list_ok);
        mCannleTv = (TextView) dialogView.findViewById(R.id.dialog_list_cannle);
        mOkTopTv = (TextView) dialogView.findViewById(R.id.dialog_list_top_ok);
        mCannleTopTv = (TextView) dialogView.findViewById(R.id.dialog_list_top_cannle);

        mDialog = new Dialog(mContext, R.style.LoadingDialog);
        mDialog.setContentView(dialogView);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = ScreenUtils.getScreenWidth(mContext);
        //设置对话框的高度
        lp.height = mDataList.size() < 7 ? LinearLayout.LayoutParams.WRAP_CONTENT : (ScreenUtils.getScreenHeight(mContext) / 2);
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 设置数据
     *
     * @param dataList 数据源：允许参数类型  String类型/实现了MiListInterface接口的bean对象
     * @return
     */
    public MiDialogList setData(ArrayList<T> dataList) {
        mDataList.clear();
        mReturnData.clear();
        mDataList.addAll(dataList);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题，默认为"请选择"
     * @return
     */
    public MiDialogList setTitle(String title) {
        mShowTitle = true;
        if ("".equals(title)) {
            mTitleTv.setText("请选择");
        } else {
            mTitleTv.setText(title);
        }
        return this;
    }

    /**
     * 设置返回值类型
     *
     * @param returntype MILIST_RETURN_SINGLE：返回一个  MILIST_RETURN_MULTIPLE：返回多个
     * @return
     */
    public MiDialogList setReturnType(int returntype) {
        mReturnType = returntype;
        return this;
    }

    /**
     * 设置显示的位置
     *
     * @param gravity 默认底部显示
     * @return
     */
    public MiDialogList setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    /**
     * 设置回调
     *
     * @param onDialogListCallback
     * @return
     */
    public MiDialogList setCallBack(OnDialogListCallback onDialogListCallback) {
        mOnDialogListCallback = onDialogListCallback;
        return this;
    }

    public void show() {
        setLayout();
        mDialog.show();
    }

    private void setLayout() {
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = mGravity == Gravity.BOTTOM ? ScreenUtils.getScreenWidth(mContext) : ScreenUtils.getScreenWidth(mContext) / 5 * 4;
        //设置对话框的高度
        lp.height = mDataList.size() < 7 ? LinearLayout.LayoutParams.WRAP_CONTENT : (ScreenUtils.getScreenHeight(mContext) / 2);
        window.setGravity(mGravity);
        window.setAttributes(lp);

        mTitleTv.setVisibility(mShowTitle ? View.VISIBLE : View.GONE);
        if (mReturnType == MiDialogList.MILIST_RETURN_MULTIPLE && mGravity == MILIST_DIALOG_CENTER) {
            mBottomLy.setVisibility(View.VISIBLE);
        } else {
            mBottomLy.setVisibility(View.GONE);
        }

        if (mReturnType == MiDialogList.MILIST_RETURN_MULTIPLE && mGravity == MILIST_DIALOG_BOTTOM) {
            mOkTopTv.setVisibility(View.VISIBLE);
            mCannleTopTv.setVisibility(View.VISIBLE);
        } else {
            mOkTopTv.setVisibility(View.GONE);
            mCannleTopTv.setVisibility(View.GONE);
        }


        //设置数据
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        DialogListAdapter dialogListAdapter = new DialogListAdapter(mContext);
        dialogListAdapter.setList(mDataList, mReturnType);
        mRecyclerView.setAdapter(dialogListAdapter);
        dialogListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mReturnType == MiDialogList.MILIST_RETURN_SINGLE) {
                    mReturnData.clear();
                    mReturnData.add(position);
                    if (mOnDialogListCallback != null) {
                        mOnDialogListCallback.onListCallback(mReturnData);
                    }
                    mDialog.dismiss();
                } else {
                    mReturnData.add(position);
                }
            }
        });

        mOkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogListCallback != null) {
                    mOnDialogListCallback.onListCallback(mReturnData);
                }
                mDialog.dismiss();
            }
        });

        mCannleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mOkTopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogListCallback != null) {
                    mOnDialogListCallback.onListCallback(mReturnData);
                }
                mDialog.dismiss();
            }
        });

        mCannleTopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }


    public interface OnDialogListCallback {
        /**
         * 回调
         */
        void onListCallback(ArrayList<Integer> dataList);
    }
}
