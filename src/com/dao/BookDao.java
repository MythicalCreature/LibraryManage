package com.dao;

import com.utils.JdbcUtils;
import com.utils.StringUtils;
import com.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * 2020/4/29 23:41
 */
public class BookDao {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    public static int bookAdd(Book book){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into books(ISBN, bookName, bookPrice, bookAuthor, publishedHouse,bookCategory,bookStorage,EnabledBorrow) values(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getBookName());
            ps.setString(3, book.getBookPrice());
            ps.setString(4, book.getBookAuthor());
            ps.setString(5, book.getPublishedHouse());
            ps.setString(6, book.getBookCategory());
            ps.setInt(7,book.getBookStorage());
            ps.setInt(8,book.getEnabledBorrow());
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

    public static int bookDelete(Book book){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "delete from books where ISBN = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,book.getIsbn());
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

    public static int bookModify(Book book){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update books set ISBN=?,bookName=?,bookPrice=?, bookAuthor=?, publishedHouse=?,bookCategory=?, bookStorage=?, enabledBorrow=? where id=?";
            ps = conn.prepareStatement(sql);
            // 先对应SQL语句，给SQL语句传递参数
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getBookName());
            ps.setString(3, book.getBookPrice());
            ps.setString(4, book.getBookAuthor());
            ps.setString(5, book.getPublishedHouse());
            ps.setString(6, book.getBookCategory());
            ps.setInt(7,book.getBookStorage());
            ps.setInt(8,book.getEnabledBorrow());
            ps.setInt(9,book.getId());

            count = ps.executeUpdate();
            conn.commit();
        }catch (Exception e) {
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

    public static int bookCount(){
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select count(ID) from books";
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

    public static List<Book> bookQuery(String selItem, String searchText, int pageNo, int pageSize){
        rs = null;
        List<Book> bookList = new ArrayList<>();
        Book book;
        try {
            conn = JdbcUtils.getConnection();
            StringBuilder sql = new StringBuilder("select ID,ISBN,bookName,bookPrice,bookAuthor,publishedHouse,bookCategory,bookStorage,enabledBorrow from books");
            if(!StringUtils.isEmpty(searchText)){
                sql.append(" where ");
                sql.append(selItem);
                sql.append(" like ?");
            }
            sql.append(" limit ? , ?");
            ps = conn.prepareStatement(sql.toString());
            if(!StringUtils.isEmpty(searchText)){
                ps.setString(1,"%"+searchText+"%");
                ps.setInt(2,(pageNo-1)*pageSize);
                ps.setInt(3,pageSize);
            }else{
                ps.setInt(1,(pageNo-1)*pageSize);
                ps.setInt(2,pageSize);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                book = new Book();
                book.setId(rs.getInt("ID"));
                book.setIsbn(rs.getString("ISBN"));
                book.setBookName(rs.getString("bookName"));
                book.setBookAuthor(rs.getString("bookAuthor"));
                book.setBookPrice(rs.getString("bookPrice"));
                book.setPublishedHouse(rs.getString("publishedHouse"));
                book.setBookCategory(rs.getString("bookCategory"));
                book.setBookStorage(rs.getInt("bookStorage"));
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

    public static int searchId(String isbn){
        int id = 0;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select ID from books where ISBN=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,isbn);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return id;
    }

    public static void modifyEnabledBorrow(Book book) {
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update books set enabledBorrow=? where ISBN=?";
            ps = conn.prepareStatement(sql);
            // 先对应SQL语句，给SQL语句传递参数
            ps.setInt(1,book.getEnabledBorrow());
            ps.setString(2,book.getIsbn());
            ps.executeUpdate();
            conn.commit();
        }catch (Exception e) {
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
            JdbcUtils.close(rs, ps, conn);
        }
    }

    public static int searchEnabledBorrow(String isbn) {
        int num = 0;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select enabledBorrow from books where ISBN=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,isbn);
            rs = ps.executeQuery();
            if(rs.next()){
                num = rs.getInt("enabledBorrow");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(rs,ps,conn);
        }
        return num;
    }
}
