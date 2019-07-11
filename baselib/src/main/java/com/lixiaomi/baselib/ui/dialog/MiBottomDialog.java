package com.lixiaomi.baselib.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lixiaomi.baselib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe：底部弹出选择<br>
 * @author：Xiaomi<br>
 * @createTime：2017/9/28<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 * @remarks：添加单例模式<br>
 * @changeTime:2019/2/14<br>
 */
public class MiBottomDialog {
    private Context mContext;
    private Dialog mDialog;
    private Display mDisplay;

    private AppCompatTextView mTitleTv;
    private AppCompatTextView mCancelTv;
    private LinearLayoutCompat mContentLayout;
    private ScrollView mContentScroll;
    private boolean mShowTitle = false;
    private List<SheetItem> mSheetItemList;

    public MiBottomDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public MiBottomDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.view_actionsheet, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(mDisplay.getWidth());
        // 获取自定义Dialog布局中的控件
        mContentScroll = (ScrollView) view.findViewById(R.id.sLayout_content);
        mContentLayout = (LinearLayoutCompat) view
                .findViewById(R.id.lLayout_content);
        mTitleTv = (AppCompatTextView) view.findViewById(R.id.txt_title);
        mCancelTv = (AppCompatTextView) view.findViewById(R.id.txt_cancel);
        mCancelTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // 定义Dialog布局和参数
        mDialog = new Dialog(mContext, R.style.LoadingDialog);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public MiBottomDialog setTitle(String title) {
        mShowTitle = true;
        mTitleTv.setVisibility(View.VISIBLE);
        mTitleTv.setText(title);
        return this;
    }

    public MiBottomDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public MiBottomDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param strItem  条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public MiBottomDialog addSheetItem(String strItem, SheetItemColor color,
                                       OnSheetItemClickListener listener) {
        if (mSheetItemList == null) {
            mSheetItemList = new ArrayList<SheetItem>();
        }
        mSheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (mSheetItemList == null || mSheetItemList.size() <= 0) {
            return;
        }
        int size = mSheetItemList.size();
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LayoutParams params = (LayoutParams) mContentScroll
                    .getLayoutParams();
            params.height = mDisplay.getHeight() / 2;
            mContentScroll.setLayoutParams(params);
        }
        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = mSheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            // 背景图片
            if (size == 1) {
                if (mShowTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (mShowTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    mDialog.dismiss();
                }
            });

            mContentLayout.addView(textView);
        }
    }

    public void show() {
        setSheetItems();
        mDialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color == null ? SheetItemColor.Blue : color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E"), Black("#333333");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
