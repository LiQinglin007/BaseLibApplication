package com.lixiaomi.baselibapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/13
 * 内容：
 * 最后修改：
 */

public class UserBean implements MiListInterface, Parcelable {

    private String UserName;
    private String UserSex;

    @Override
    public String getMiDialigListShowData() {
        return UserName+"我是拼接的内容";
    }

    public UserBean(String userName, String userSex) {
        UserName = userName;
        UserSex = userSex;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserName);
        dest.writeString(this.UserSex);
    }

    protected UserBean(Parcel in) {
        this.UserName = in.readString();
        this.UserSex = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };


}
