package com.lixiaomi.baselibapplication.utils.greendaoUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lixiaomi.baselibapplication.bean.DaoMaster;
import com.lixiaomi.baselibapplication.bean.DaoSession;


/**
 * @describe：数据库管理<br>
 * @author：Xiaomi<br>
 * @createTime：2019/1/30<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();

    private static DBManager instance;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * 是否初始化过
     */
    private boolean mInited = false;
    /**
     * 上下文对象
     */
    private Context mContext = null;
    /**
     * 数据库操作
     */
    private DaoMaster.OpenHelper mOpenHelper = null;
    private SQLiteDatabase mDatabase = null;
    private DaoMaster mDaoMaster = null;
    private DaoSession mDaoSession = null;

    /**
     * 初始化数据库
     *
     * @param context
     */
    public void init(Context context, String dbName) {
        if (!mInited || mContext == null) {
            this.mContext = context;
            DaoMaster.OpenHelper mOpenHelper = new DBOpenHelper(mContext, dbName, null);
            mDatabase = mOpenHelper.getWritableDatabase();
            mDaoSession = new DaoMaster(mDatabase).newSession();
        }
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
