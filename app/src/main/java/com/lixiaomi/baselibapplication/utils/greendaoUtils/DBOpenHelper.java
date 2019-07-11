package com.lixiaomi.baselibapplication.utils.greendaoUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lixiaomi.baselibapplication.bean.DaoMaster;
import com.lixiaomi.baselibapplication.bean.NoticeBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * @describe：数据库升级工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2019/1/30<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class DBOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();

    public DBOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory) {
        super(context, dbName, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //数据库版本升级
        //这里不用判断版本，如果不更新不会走onUpgrade方法
        //直接把所有的表都放来就好，不用管那个改了那个没改
        MigrationHelper.getInstance().migrate(db, true, NoticeBeanDao.class);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库版本降级
        //这里有一个bug    高版本---》低版本  高版本里边的表不会被删掉
//        MigrationHelper.getInstance().migrate(wrap(db), false, NoticeBeanDao.class);
    }
}
