package com.app.officeautomationapp.db;

import android.util.Log;

import com.app.officeautomationapp.util.DBHelper;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by pc on 2018/1/9.
 */

public class YonggongDB {
    public static final String DB_NAME = "yonggong.db";
    //数据库名
    public static final int VERSION = 1;
    //数据库版本号
    private static YonggongDB yonggongDB;
    private static DbManager db;
    //接收构造方法初始化的DbManager对象
    private YonggongDB(){
        DBHelper databaseOpenHelper = new DBHelper(DB_NAME,VERSION);
        db = x.getDb(databaseOpenHelper.getDaoConfig());
    }
    //构造方法私有化,拿到DbManager对象
    public synchronized static YonggongDB getIntance(){
        if (yonggongDB == null){
            yonggongDB = new YonggongDB();
        }
        return yonggongDB;
    }
    //获取PersonDB实例

    /****************************************************************************************/
    //写两个测试方法，也就是常用的数据库操作
    public void saveYonggong(Yonggong yonggong){
        try {
            if(loadYonggong()!=null) {
                db.delete(loadYonggong());
            }
            db.save(yonggong);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
    //将User实例存进数据库

    public Yonggong loadYonggong(){
        Yonggong yonggong = null;
        try {
            yonggong = db.selector(Yonggong.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return yonggong;
    }
    //读取所有Person信息



    public void delYonggong(Yonggong yonggong){
        try {
            db.delete(yonggong);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
}
