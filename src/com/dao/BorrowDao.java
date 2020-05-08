package com.dao;

import com.model.Book;
import com.model.Borrow;
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
 * 2020/5/2 0:48
 */
public class BorrowDao {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    public static List<Book> bookQuery(String searchText,String selItem) {
        rs = null;
        List<Book> bookList = new ArrayList<>();
        Book book;
        try {
            conn = JdbcUtils.getConnection();
            StringBuilder sql = new StringBuilder("select ISBN,bookName,bookAuthor,bookPrice,enabledBorrow from books");
            if(!StringUtils.isEmpty(searchText)){
                sql.append(" where ");
                sql.append(selItem);
                sql.append(" like ?");
            }
            ps = conn.prepareStatement(sql.toString());
            if(!StringUtils.isEmpty(searchText)) {
                ps.setString(1, "%" + searchText + "%");
            }
            rs = ps.executeQuery();
            while (rs.next()){

                book = new Book();
                book.setIsbn(rs.getString("ISBN"));
                book.setBookName(rs.getString("bookName"));
                book.setBookAuthor(rs.getString("bookAuthor"));
                book.setBookPrice(rs.getString("bookPrice"));
                book.setEnabledBorrow(rs.getInt("enabledBorrow"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }

        return bookList;
    }

    public static int borrowAdd(Borrow borrow) {
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into borrow(userName,ISBN,bookName,borrowDate,expectDate) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, borrow.getUserName());
            ps.setString(2,borrow.getIsbn());
            ps.setString(3, borrow.getBookName());
            ps.setString(4, borrow.getBorrowDate());
            ps.setString(5, borrow.getExpectDate());
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

    public static List<Borrow> searchBorrowBook(Borrow borrow) {
        rs = null;
        List<Borrow> borrowList = new ArrayList<>();
        Borrow resUser;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from borrow where userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, borrow.getUserName());
            rs = ps.executeQuery();
            boolean first = rs.first();
            if(first){
                while (rs.next()){
                    resUser= new Borrow();
                    resUser.setIsbn(rs.getString("ISBN"));
                    resUser.setUserName(rs.getString("userName"));
                    resUser.setBookName(rs.getString("bookName"));
                    resUser.setBorrowDate(rs.getString("borrowDate"));
                    resUser.setExpectDate(rs.getString("expectDate"));
                    borrowList.add(resUser);
                }
            }else{
                borrowList=null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return borrowList;
    }

    public static String searchUserName(String bookName) {
        String userName = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select userName from borrow where bookName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,bookName);
            rs = ps.executeQuery();
            if(rs.next()){
                userName = rs.getString("userName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return userName;
    }

    public static int returnAdd(Borrow r) {
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into `return`(userName,ISBN,bookName,borrowDate,realDate) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, r.getUserName());
            ps.setString(2,r.getIsbn());
            ps.setString(3, r.getBookName());
            ps.setString(4, r.getBorrowDate());
            ps.setString(5, r.getRealDate());
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

    public static void borrowDel(Borrow b) {
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from borrow where ISBN=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,b.getIsbn());
            ps.executeUpdate();
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
    }

    public static int countIsbn(String isbn) {
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select count(ISBN) from borrow where ISBN=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,isbn);
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
}
