package com.lixiaomi.baselibapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;


import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */
@Entity
public class NoticeBean implements MiListInterface, Parcelable {


    @Id
    Long NoticeId;//公告id
    String NoticeTitle;//公告标题
    String NoticeContent;//公告内容

    @Generated(hash = 1968056286)
    public NoticeBean(Long NoticeId, String NoticeTitle, String NoticeContent) {
        this.NoticeId = NoticeId;
        this.NoticeTitle = NoticeTitle;
        this.NoticeContent = NoticeContent;
    }

    @Generated(hash = 303198189)
    public NoticeBean() {
    }



    public Long getNoticeId() {
        return this.NoticeId;
    }

    public void setNoticeId(Long NoticeId) {
        this.NoticeId = NoticeId;
    }

    public String getNoticeTitle() {
        return this.NoticeTitle;
    }

    public void setNoticeTitle(String NoticeTitle) {
        this.NoticeTitle = NoticeTitle;
    }

    public String getNoticeContent() {
        return this.NoticeContent;
    }

    public void setNoticeContent(String NoticeContent) {
        this.NoticeContent = NoticeContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.NoticeId);
        dest.writeString(this.NoticeTitle);
        dest.writeString(this.NoticeContent);
    }

    protected NoticeBean(Parcel in) {
        this.NoticeId = (Long) in.readValue(Long.class.getClassLoader());
        this.NoticeTitle = in.readString();
        this.NoticeContent = in.readString();
    }

    public static final Creator<NoticeBean> CREATOR = new Creator<NoticeBean>() {
        @Override
        public NoticeBean createFromParcel(Parcel source) {
            return new NoticeBean(source);
        }

        @Override
        public NoticeBean[] newArray(int size) {
            return new NoticeBean[size];
        }
    };

    @Override
    public String getMiDialigListShowData() {
        return NoticeTitle;
    }
}
