package com.controller;

import com.dao.BookDao;
import com.model.Book;
import javax.swing.*;
import java.util.List;

/**
 * @author zzx
 * 2020/4/29 23:40
 */
public class BookAction {
    public static int addBookInf(String isbn,String bookName, String bookPrice,String bookAuthor,String publishedHouse,String bookCategory,String tfBookStorage,String tfEnabledBorrow){
        Book book = new Book();
        int bookStorage = Integer.parseInt(tfBookStorage);
        int enabledBorrow = Integer.parseInt(tfEnabledBorrow);
        book.setIsbn(isbn);
        book.setBookName(bookName);
        book.setBookPrice(bookPrice);
        book.setBookAuthor(bookAuthor);
        book.setPublishedHouse(publishedHouse);
        book.setBookCategory(bookCategory);
        book.setBookStorage(bookStorage);
        book.setEnabledBorrow(enabledBorrow);
        return BookDao.bookAdd(book);

    }
    public static int delBookInf(String isbn){
        Book book = new Book();
        book.setIsbn(isbn);
        return BookDao.bookDelete(book);
    }
    public static int modifyBookInf(JTable table, String isbn, String bookName, String bookPrice, String bookAuthor, String publishedHouse, String bookCategory, String tfBookStorage, String tfEnabledBorrow){
        int id  = BookDao.searchId(table.getValueAt(table.getSelectedRow(),0).toString());
        int bookStorage = Integer.parseInt(tfBookStorage);
        int enabledBorrow = Integer.parseInt(tfEnabledBorrow);
        Book book = new Book(id,isbn,bookName,bookPrice,bookAuthor,publishedHouse,bookCategory,bookStorage,enabledBorrow);
        return BookDao.bookModify(book);

    }
    public static int countBook(int pageSize){
        int count = BookDao.bookCount();
        if(count%pageSize!=0){
            return count/pageSize+1;
        }else{
            return count/pageSize;
        }
    }
    public static Object[][] selTable(String[] colName,String selItem,String searchText,int pageNo,int pageSize){
        List<Book> list = BookDao.bookQuery(selItem,searchText,pageNo,pageSize);
        Object[][] results = new Object[list.size()][colName.length];
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            results[i][0] = book.getIsbn();
            results[i][1] = book.getBookName();
            results[i][2] = book.getBookAuthor();
            results[i][3] = book.getBookPrice();
            results[i][4] = book.getPublishedHouse();
            results[i][5] = book.getBookCategory();
            results[i][6] = book.getBookStorage();
            results[i][7] = book.getEnabledBorrow();
        }
        return results;
    }

    public static void delEnabledBorrow(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        int enabledBorrow = BookDao.searchEnabledBorrow(isbn);
        book.setEnabledBorrow(enabledBorrow-1);
        BookDao.modifyEnabledBorrow(book);
    }

    public static void addEnabledBorrow(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        int enabledBorrow = BookDao.searchEnabledBorrow(isbn);
        book.setEnabledBorrow(enabledBorrow+1);
        BookDao.modifyEnabledBorrow(book);
    }
}
