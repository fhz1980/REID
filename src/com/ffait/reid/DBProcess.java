package com.ffait.reid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ffait.util.ParameterOperate;
public class DBProcess{
     // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = ParameterOperate.extract("db");
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "abc@123";
 
    public static List<PowerWorker> allFeature() {
        Connection conn = null;
        Statement stmt = null;
        List<PowerWorker> workerlist=new ArrayList<PowerWorker>();
        try{
           Class.forName(JDBC_DRIVER);                               // 注册 JDBC 驱动
           System.out.println("连接数据库...");                       // 打开链接
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           System.out.println(" 实例化Statement对象...");       // 执行查询
           stmt = conn.createStatement();
           String sql;
           sql = "SELECT id,name,feature FROM t_power_worker";
           ResultSet rs = stmt.executeQuery(sql);
           
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
            	PowerWorker pw=new PowerWorker();
            	pw.setId(rs.getString("id"));
                pw.setName(rs.getString("name"));
                pw.setFeature(rs.getString("feature"));
                workerlist.add(pw);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
            
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return workerlist;
    }
    
    
}