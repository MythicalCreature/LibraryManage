package com.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author zzx
 * 2020/4/29 21:14
 */
public class JdbcUtils {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties pro = new Properties();
            //加载文件
            ClassLoader classLoader = JdbcUtils.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream("jdbc.properties");
            pro.load(in);
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            String driver = pro.getProperty("driver");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接
     * @return 连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    /**
     * 释放资源
     * @param rs ResultSet
     * @param stmt Statement
     * @param conn Connection
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
