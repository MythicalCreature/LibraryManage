package com.dao;

import com.model.Manager;
import com.model.User;
import com.utils.JdbcUtils;
import com.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zzx
 * 2020/4/29 23:35
 */
public class UserDao {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    public static List<User> userQuery(String selItem, String searchText, int pageNo, int pageSize){
        rs = null;
        List<User> userList = new ArrayList<>();
        User user;
        try {
            conn = JdbcUtils.getConnection();
            StringBuilder sql = new StringBuilder("select userName,password,phoneNumber from user");
            if(!StringUtils.isEmpty(searchText)){
                sql.append(" where ");
                sql.append(selItem);
                sql.append(" like ?");
            }
            sql.append(" limit ? , ?");
            ps = conn.prepareStatement(sql.toString());
            if(StringUtils.isEmpty(searchText)){
                ps.setInt(1,(pageNo-1)*pageSize);
                ps.setInt(2,pageSize);
            }else{
                ps.setString(1,"%"+searchText+"%");
                ps.setInt(2,(pageNo-1)*pageSize);
                ps.setInt(3,pageSize);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return userList;
    }

    public static int userCount(){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select count(ID) from user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return count;
    }

    public static int userDelete(User user){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "delete from user where userName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUserName());
            count = ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            //回滚事务
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
    public static User queryUserName(User user){
        rs = null;
        User resUser=null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select userName,password from user where userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            rs = ps.executeQuery();
            while (rs.next()){
                resUser = new User();
                resUser.setUserName(rs.getString("userName"));
                resUser.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return resUser;
    }
}
