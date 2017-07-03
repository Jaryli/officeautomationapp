package com.app.officeautomationapp.dao;

import android.util.Log;
import com.app.officeautomationapp.db.User;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import java.util.List;

/**
 * Created by CS-711701-00027 on 2017/4/28.
 */

public class UserDao extends DaoConfig{
    //创建数据库
    private void createDB(List<User> list) throws DbException {
        //方法不仅可以插入单个对象，还能插入集合
        db.save(list);
    }

    //删除数据库
    private void dropDB() throws DbException {
        db.dropDb();
    }

    //删除表
    private void delTable() throws DbException {
        db.dropTable(User.class);
    }

    private List<User> selelctDB(WhereBuilder b) throws DbException {
        //查询数据库表中第一条数据
        User first = db.findFirst(User.class);
        Log.i("JAVA",first.toString());
        //添加查询条件进行查询
        //第一种写法：
//        WhereBuilder b = WhereBuilder.b();
//        b.and("id",">",2); //构造修改的条件
//        b.and("id","<",4);
        List<User> all = db.selector(User.class).where(b).findAll();//findAll()：查询所有结果
        for(User childInfo :all){
            Log.i("JAVA",childInfo.toString());
        }
        return all;
        //第二种写法：
//        List<User> all = db.selector(User.class).where("id",">",2).and("id","<",4).findAll();
//        for(User childInfo :all){
//            Log.i("JAVA",childInfo.toString());
//        }
    }


    private void updateTable(User user,String... updateColumnNames) throws DbException {
        //第一种写法：
        db.update(user,updateColumnNames); //updateColumnNames：表中的字段名
        //第二种写法：
//        WhereBuilder b = WhereBuilder.b();
//        b.and("id","=",first.getId()); //构造修改的条件
//        KeyValue name = new KeyValue("c_name","zhansan3");
//        db.update(User.class,b,name);
        //第三种写法：
//        first.setcName("zhansan4");
//        db.saveOrUpdate(first);
    }


    private void delTableData(User user) throws DbException {
        db.delete(user);
    }


}
