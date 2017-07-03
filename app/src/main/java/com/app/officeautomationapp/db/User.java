package com.app.officeautomationapp.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by CS-711701-00027 on 2017/4/28.
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name="user",onCreated = "sql")
public class User {
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    //默认的构造方法必须写出，如果没有，这张表是创建不成功的
    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{id="+id+",username="+username+",password="+password+"}";
    }
}
