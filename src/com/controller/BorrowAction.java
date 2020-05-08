package com.controller;

import com.dao.BorrowDao;
import com.model.Book;
import com.model.Borrow;
import com.utils.DateUtils;

import javax.swing.*;
import java.util.List;

/**
 * @author zzx
 * 2020/5/2 13:18
 */
public class BorrowAction {
    public static int addBorrowInf(String userName, String isbn, String bookName, String borrowDate, String expectDate) {
        Borrow borrow = new Borrow();
        borrow.setUserName(userName);
        borrow.setIsbn(isbn);
        borrow.setBookName(bookName);
        borrow.setBorrowDate(borrowDate);
        borrow.setExpectDate(expectDate);
        return BorrowDao.borrowAdd(borrow);
    }

    public static Object[][] selTable(String[] colNames, String searchText, String selItem) {
        List<Book> list = BorrowDao.bookQuery(searchText, selItem);
        if (list.size() == 0) {
            return null;
        }
        Object[][] results = new Object[list.size()][colNames.length];
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            results[i][0] = book.getIsbn();
            results[i][1] = book.getBookName();
            results[i][2] = book.getBookAuthor();
            results[i][3] = book.getBookPrice();
            if (book.getEnabledBorrow() > 0) {
                results[i][4] = "是";
            } else {
                results[i][4] = "否";
            }
        }
        return results;
    }

    public static Object[][] renewTable(String[] colNames, List<Borrow> list) {
        Object[][] results = new Object[list.size()][colNames.length];
        for (int i = 0; i < list.size(); i++) {
            Borrow borrow = list.get(i);
            results[i][0] = borrow.getIsbn();
            results[i][1] = borrow.getBookName();
            results[i][2] = borrow.getBorrowDate();
            results[i][3] = borrow.getExpectDate();
            if(colNames.length>4){
                if(BorrowDao.countIsbn(borrow.getIsbn())>1){
                    results[i][4] = "否";
                }else{
                    results[i][4] = "是";
                }
            }
        }
        return results;
    }

    public static int addReturnInf(String userName, String isbn, String bookName, String borrowDate, String realDate) {
        Borrow r = new Borrow();
        r.setUserName(userName);
        r.setIsbn(isbn);
        r.setBookName(bookName);
        r.setBorrowDate(borrowDate);
        r.setRealDate(realDate);
        return BorrowDao.returnAdd(r);
    }

    public static void delBorrowInf(String isbn) {
        Borrow b = new Borrow();
        b.setIsbn(isbn);
        BorrowDao.borrowDel(b);
    }

    public static int addRenewInf(Borrow borrow, String curTime) {
        int count = 0;
        String expectTime = borrow.getExpectDate();
        int curYear = DateUtils.subYear(curTime);
        int curMonth = DateUtils.subMonth(curTime);
        int curDay = DateUtils.subDay(curTime);
        int expectYear = DateUtils.subYear(expectTime);
        int expectMonth = DateUtils.subMonth(expectTime);
        int expectDay = DateUtils.subDay(expectTime);
        if(curYear<expectYear){
            if(curMonth==12&&expectMonth==1&&31-curDay+expectDay<15){
                borrow.setBorrowDate(curTime);
                expectTime = DateUtils.expect(curYear,curMonth,curDay);
                borrow.setExpectDate(expectTime);
                count = BorrowDao.borrowAdd(borrow);
                return count;
            }else{
                JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
            }
        }else if(curYear==expectYear){
            if(curMonth>expectMonth){
                JOptionPane.showMessageDialog(null,"已到期，\n请尽快办理还书并缴纳相应费用");
            }else if(curMonth==expectMonth){
                if(expectDay<curDay){
                    JOptionPane.showMessageDialog(null,"已到期，\n请尽快办理还书并缴纳相应费用");
                }if(expectDay-curDay<15){
                    borrow.setBorrowDate(curTime);
                    expectTime = DateUtils.expect(curYear,curMonth,curDay);
                    borrow.setExpectDate(expectTime);
                    count = BorrowDao.borrowAdd(borrow);
                    return count;
                }else {
                    JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                }
            }else if(expectMonth-curMonth==1){
                if(expectDay>=15){
                    JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                } else if(DateUtils.isBigMonth(curMonth)){
                    if(31-curDay+expectDay<15){
                        borrow.setBorrowDate(curTime);
                        expectTime = DateUtils.expect(curYear,curMonth,curDay);
                        borrow.setExpectDate(expectTime);
                        count = BorrowDao.borrowAdd(borrow);
                        return count;
                    }else {
                        JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                    }
                }else if(DateUtils.isSmallMonth(curMonth)){
                    if(30-curDay+expectDay<15){
                        borrow.setBorrowDate(curTime);
                        expectTime = DateUtils.expect(curYear,curMonth,curDay);
                        borrow.setExpectDate(expectTime);
                        count = BorrowDao.borrowAdd(borrow);
                        return count;
                    }else {
                        JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                    }
                }else if(DateUtils.isLeapYear(curYear)){
                    if(29-curDay+expectDay<15){
                        borrow.setBorrowDate(curTime);
                        expectTime = DateUtils.expect(curYear,curMonth,curDay);
                        borrow.setExpectDate(expectTime);
                        count = BorrowDao.borrowAdd(borrow);
                        return count;
                    }else {
                        JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                    }
                }else {
                    if(28-curDay+expectDay<15){
                        borrow.setBorrowDate(curTime);
                        expectTime = DateUtils.expect(curYear,curMonth,curDay);
                        borrow.setExpectDate(expectTime);
                        count = BorrowDao.borrowAdd(borrow);
                        return count;
                    }else {
                        JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,"未到续借时间，\n请在到期前半个月内进行续借");
            }
        }else {
            JOptionPane.showMessageDialog(null,"已到期，\n请尽快办理还书并缴纳相应费用");
        }
        return count;
    }
}
