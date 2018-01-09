package com.app.officeautomationapp.db;

import android.util.Log;

import com.app.officeautomationapp.util.DBHelper;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by pc on 2018/1/9.
 */

public class TaiBanDB {
    public static final String DB_NAME = "taiban.db";
    //数据库名
    public static final int VERSION = 1;
    //数据库版本号
    private static TaiBanDB taiBanDB;
    private static DbManager db;
    //接收构造方法初始化的DbManager对象
    private TaiBanDB(){
        DBHelper databaseOpenHelper = new DBHelper(DB_NAME,VERSION);
        db = x.getDb(databaseOpenHelper.getDaoConfig());
    }
    //构造方法私有化,拿到DbManager对象
    public synchronized static TaiBanDB getIntance(){
        if (taiBanDB == null){
            taiBanDB = new TaiBanDB();
        }
        return taiBanDB;
    }
    //获取PersonDB实例

    /****************************************************************************************/
    //写两个测试方法，也就是常用的数据库操作
    public void saveTaiban(Taiban taiban){
        try {
            db.delete(taiban);
            db.save(taiban);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
    //将User实例存进数据库

    public Taiban loadPerson(){
        Taiban taiban = null;
        try {
            taiban = db.selector(Taiban.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return taiban;
    }
    //读取所有Person信息



    public void delTaiban(Taiban taiban){
        try {
            db.delete(taiban);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
}
