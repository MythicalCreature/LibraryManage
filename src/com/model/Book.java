package com.model;

/**
 * @author zzx
 * 2020/4/29 23:43
 */
public class Book {
    private int id;
    private String isbn;
    private String bookName;
    private String bookPrice;
    private String bookAuthor;
    private String publishedHouse;
    private String bookCategory;
    private int bookStorage;
    private int enabledBorrow;


    public Book(int id, String isbn, String bookName, String bookPrice, String bookAuthor, String publishedHouse, String bookCategory, int bookStorage, int enabledBorrow) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookAuthor = bookAuthor;
        this.publishedHouse = publishedHouse;
        this.bookCategory = bookCategory;
        this.bookStorage = bookStorage;
        this.enabledBorrow = enabledBorrow;
    }

    public Book() {

    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice='" + bookPrice + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", publishedHouse='" + publishedHouse + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", bookStorage=" + bookStorage +
                ", enabledBorrow=" + enabledBorrow +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPublishedHouse() {
        return publishedHouse;
    }

    public void setPublishedHouse(String publishedHouse) {
        this.publishedHouse = publishedHouse;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public int getBookStorage() {
        return bookStorage;
    }

    public void setBookStorage(int bookStorage) {
        this.bookStorage = bookStorage;
    }

    public int getEnabledBorrow() {
        return enabledBorrow;
    }

    public void setEnabledBorrow(int enabledBorrow) {
        this.enabledBorrow = enabledBorrow;
    }
}
