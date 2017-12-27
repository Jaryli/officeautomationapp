package com.app.officeautomationapp.db;

import android.util.Log;

import com.app.officeautomationapp.util.DBHelper;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by pc on 2017/12/27.
 */

public class UserDB {
    public static final String DB_NAME = "user.db";
    //数据库名
    public static final int VERSION = 1;
    //数据库版本号
    private static UserDB personDB;
    private static DbManager db;
    //接收构造方法初始化的DbManager对象
    private UserDB(){
        DBHelper databaseOpenHelper = new DBHelper(DB_NAME,VERSION);
        db = x.getDb(databaseOpenHelper.getDaoConfig());
    }
    //构造方法私有化,拿到DbManager对象
    public synchronized static UserDB getIntance(){
        if (personDB == null){
            personDB = new UserDB();
        }
        return personDB;
    }
    //获取PersonDB实例

    /****************************************************************************************/
    //写两个测试方法，也就是常用的数据库操作
    public void saveUser(User person){
        try {
            db.delete(person);
            db.save(person);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
    //将User实例存进数据库

    public User loadPerson(){
        User user = null;
        try {
            user = db.selector(User.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
    }
    //读取所有Person信息
}
