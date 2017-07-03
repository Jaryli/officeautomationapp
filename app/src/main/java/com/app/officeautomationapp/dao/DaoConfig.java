package com.app.officeautomationapp.dao;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * Created by CS-711701-00027 on 2017/4/28.
 */

public class DaoConfig {

    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            //设置数据库名，默认xutils.db
            .setDbName("oa_db")//设置数据库名
            //设置表创建的监听
            .setTableCreateListener(new DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(DbManager db, TableEntity table){
                    Log.i("oa", "onTableCreated：" + table.getName());
                }
            })
            //设置是否允许事务，默认true
            .setAllowTransaction(true)
            //设置数据库路径，默认安装程序路径下
            //.setDbDir(new File("/mnt/sdcard/"))
            //设置数据库的版本号
            .setDbVersion(1)
            //设置数据库更新的监听
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion,
                                      int newVersion) {
                }
            })
            //设置数据库打开的监听
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    //开启数据库支持多线程操作，提升性能
                    db.getDatabase().enableWriteAheadLogging();
                }
            });
    DbManager db = x.getDb(daoConfig);
}
