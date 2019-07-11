package com.lixiaomi.baselib.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.ScreenUtils;

/**
 * @describe：dialog<br>
 * @author：Xiaomi<br>
 * @createTime：2017/9/28<br>
 * @remarks：统一修改变量命名规则，添加EDIT_TYPE状态下加密显示功能<br>
 * @changeTime:2019/2/11<br>
 * @remarks：添加单例模式<br>
 * @changeTime:2019/2/14<br>
 */
public class MiDialog {
    private Context mContext;
    private Display mDisplay;
    AlertDialog mDialog;
    AlertDialog.Builder mBuilder;
    /**
     * 整个布局
     */
    private LinearLayoutCompat mLlayoutBg;
    private AppCompatTextView mTitleTv;
    private AppCompatTextView mMsgTv;
    private AppCompatEditText mMsgEdit;
    private AppCompatTextView mOkBtn;
    private AppCompatTextView mCannleBtn;
    private AppCompatTextView mImgLine;
    private LinearLayoutCompat mLineLy;

    /**
     * 编辑框
     */
    public static final int EDIT_TYPE = 0x1;
    /**
     * 显示消息框
     */
    public static final int MESSAGE_TYPE = 0x2;

    private int mType;

    private boolean mShowTitle = false;
    private boolean mShowMsg = false;
    private boolean mShowOkBtn = false;
    private boolean mShowCannleBtn = false;
    /**
     * 是否密文显示
     */
    private boolean mPassword = false;


    /**
     * @param context 上下文
     * @param type    显示类型  EDIT_TYPE：输入框  MESSAGE_TYPE：显示框
     */
    public MiDialog(Context context, int type) {
        this.mContext = context;
        this.mType = type;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    /**
     * 构建MiDilog
     *
     * @return
     */
    public MiDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_mi, null);

        // 获取自定义Dialog布局中的控件
        mLlayoutBg = (LinearLayoutCompat) view.findViewById(R.id.lLayout_bg);
        mTitleTv = (AppCompatTextView) view.findViewById(R.id.txt_title);
        mTitleTv.setVisibility(View.GONE);
        mMsgTv = (AppCompatTextView) view.findViewById(R.id.message_tv);
        mMsgTv.setVisibility(View.GONE);
        mMsgEdit = (AppCompatEditText) view.findViewById(R.id.dialog_edit);
        mMsgEdit.setVisibility(View.GONE);

        mOkBtn = (AppCompatTextView) view.findViewById(R.id.dialog_ok);
        mOkBtn.setVisibility(View.GONE);

        mCannleBtn = (AppCompatTextView) view.findViewById(R.id.dialog_cannle);
        mCannleBtn.setVisibility(View.GONE);

        mImgLine = (AppCompatTextView) view.findViewById(R.id.img_line);
        mImgLine.setVisibility(View.GONE);

        mLineLy = (LinearLayoutCompat) view.findViewById(R.id.line_ly);
        mLineLy.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setView(view);
        mDialog = mBuilder.show();

        return this;
    }


    /**
     * 设置标题
     *
     * @param title 标题文字，默认为“标题”
     * @return
     */
    public MiDialog setTitle(String title) {
        mShowTitle = true;
        if ("".equals(title)) {
            mTitleTv.setText("标题");
        } else {
            mTitleTv.setText(title);
        }
        return this;
    }

    /**
     * 设置中间显示的内容
     *
     * @param msg
     * @return
     */
    public MiDialog setMsg(String msg) {
        mShowMsg = true;
        if (mType == EDIT_TYPE) {
            mMsgEdit.setVisibility(View.VISIBLE);
            mMsgTv.setVisibility(View.GONE);
            mMsgEdit.setText(msg);
        } else {
            mMsgTv.setVisibility(View.VISIBLE);
            mMsgEdit.setVisibility(View.GONE);
            mMsgTv.setText(msg);
        }
        return this;
    }

    /**
     * 设置hint
     *
     * @param hintMsg
     * @return
     */
    public MiDialog setHint(String hintMsg) {
        mMsgEdit.setHint(hintMsg);
        return this;
    }

    /**
     * @param password
     * @return
     */
    public MiDialog setPassword(boolean password) {
        mPassword = password;
        return this;
    }

    /**
     * 设置确定按钮
     *
     * @param msg      文字
     * @param listener 监听
     * @return
     */
    public MiDialog setOkButton(String msg, final DialogCallBack listener) {
        mShowOkBtn = true;
        if ("".equals(msg)) {
            mOkBtn.setText("确定");
        } else {
            mOkBtn.setText(msg);
        }
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == EDIT_TYPE) {
                    listener.dialogCallBack(mMsgEdit.getText().toString());
                } else {
                    listener.dialogCallBack(null);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置关闭按钮
     *
     * @param msg      按钮文字
     * @param listener 监听
     * @return
     */
    public MiDialog setCannleButton(String msg, final View.OnClickListener listener) {
        mShowCannleBtn = true;
        if ("".equals(msg)) {
            mCannleBtn.setText("取消");
        } else {
            mCannleBtn.setText(msg);
        }
        mCannleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!mShowTitle && !mShowMsg) {
            mTitleTv.setText("提示");
            mTitleTv.setVisibility(View.VISIBLE);
        }

        if (mShowTitle) {
            mTitleTv.setVisibility(View.VISIBLE);
        }

        if (mShowMsg) {
            mMsgTv.setVisibility(View.VISIBLE);
        }

        if (mShowOkBtn && mShowCannleBtn) {
            mOkBtn.setVisibility(View.VISIBLE);
            mCannleBtn.setVisibility(View.VISIBLE);
            mImgLine.setVisibility(View.VISIBLE);
            mLineLy.setVisibility(View.VISIBLE);
        }

        if (mShowOkBtn && !mShowCannleBtn) {
            mOkBtn.setVisibility(View.VISIBLE);
            mLineLy.setVisibility(View.VISIBLE);
        }

        if (!mShowOkBtn && mShowCannleBtn) {
            mCannleBtn.setVisibility(View.VISIBLE);
            mLineLy.setVisibility(View.VISIBLE);
        }

        if (mType == EDIT_TYPE) {
            mMsgEdit.setVisibility(View.VISIBLE);
            mMsgTv.setVisibility(View.GONE);
            if (mPassword) {
                //加密显示
                mMsgEdit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                mMsgEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mMsgEdit.setSelection(mMsgEdit.getText().toString().length());
        } else {
            mMsgEdit.setVisibility(View.GONE);
            mMsgTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示
     */
    public void show() {
        setLayout();
        mDialog.show();
    }


    public interface DialogCallBack {
        /**
         * mDialog 消失后的回调
         */
        void dialogCallBack(String connect);
    }
}
