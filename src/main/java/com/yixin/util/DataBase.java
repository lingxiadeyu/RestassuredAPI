package com.yixin.util;

import org.apache.commons.lang.ObjectUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    //加载驱动程序
//    String driver = "com.mysql.cj.jdbc.Driver";
    //驱动下载到这个目录下了
    String driver = "com.mysql.jdbc.Driver";
    //url指向要访问的数据库名
    String url = "jdbc:mysql://10.2.101.6:3306?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    // MySQL配置时的用户名
    String user = "baoxian_admin";
    // MySQL配置时的密码
    String password = "baoxian_admin";

    public List readDB(String sqlString, String queryName) throws SQLException{
        //定义一个list用来存储sql结果内容
        List content=new ArrayList();
        try{
            //加载驱动程序
            Class.forName(driver);
            //连接数据库
            Connection conn = DriverManager.getConnection(url, user, password);
            //判断下连接未关闭
            if(!conn.isClosed()){
                System.out.println("Successed connecting to the Database!"+"\n");
                //为连接初始化Statement
                Statement statement = conn.createStatement();
                //初始化要执行的sql语句
                String sql = sqlString+";";
                //打印sql语句
                System.out.println(sql);
                //执行查询并赋值给结果集
                ResultSet rs = statement.executeQuery(sql);


                while (rs.next()){
                    //根据数据表字段名获取字段值
                    String querys=rs.getString(queryName);
                    //把字段值存储在list中
                    content.add(querys);
                }
                rs.close();
                conn.close();
            }
        }catch (ClassNotFoundException e){
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }catch (Exception  e){
            e.printStackTrace();
            return null;
        }
        //返回list结果
        return content;
    }
}
