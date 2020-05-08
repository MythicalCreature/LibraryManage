package com.dao;

import com.model.Manager;
import com.utils.JdbcUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zzx
 * 2020/4/29 21:11
 */
public class ManagerDao {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static Manager resManager;
    /**
     * 登录验证
     * @param manager 传进用户
     */
    public static Manager login(Manager manager){
        resManager = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from manager where userName=? and password=? ";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, manager.getUserName());
            ps.setString(2, manager.getPassword());
            //执行sql
            rs = ps.executeQuery();
            if (rs.next()){
                resManager = new Manager();
                resManager.setUserName(rs.getString("userName"));
                resManager.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage());
            JOptionPane.showMessageDialog(null,"数据库连接失败");
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return resManager;
    }
    public static Manager queryUserName(Manager manager){
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from manager where userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, manager.getUserName());
            rs = ps.executeQuery();
            while (rs.next()){
                resManager = new Manager();
                resManager.setUserName(rs.getString("userName"));
                resManager.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return resManager;
    }
    public static int update(Manager manager){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update manager set password=? where userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, manager.getPassword());
            ps.setString(2, manager.getUserName());
            count = ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            try {
                if(conn!=null){
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return count;
    }
}
