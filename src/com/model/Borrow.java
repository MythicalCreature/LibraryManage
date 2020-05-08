package com.model;

/**
 * @author zzx
 * 2020/5/2 16:07
 */
public class Borrow {
    private String userName;
    private String isbn;
    private String bookName;
    private String borrowDate;
    private String expectDate;
    private String realDate;
    public Borrow() {
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "userName='" + userName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", borrowDate='" + borrowDate + '\'' +
                ", expectDate='" + expectDate + '\'' +
                ", realDate='" + realDate + '\'' +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }
}
