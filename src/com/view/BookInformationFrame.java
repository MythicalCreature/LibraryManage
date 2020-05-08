package com.view;

import com.controller.BookAction;
import com.utils.BgImageUtils;
import com.utils.FrameUtils;
import com.utils.MenuUtils;
import com.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zzx
 * 2020/4/29 23:39
 */
public class BookInformationFrame {
    private Container c;

    private JLabel jlTitle;

    private JComboBox<String> comboBox;
    private String selItem;
    private final int INDEX_ZERO = 0;
    private final int INDEX_ONE = 1;
    private final int INDEX_TWO = 2;
    private final int INDEX_THREE = 3;
    private final int INDEX_FOUR = 4;
    private JTextField jtfSearch;
    private JButton jbSearch;

    private JTable table;
    private JScrollPane scrollPane;
    private int maxPageNo;
    private int pageNo;
    private int pageSize;

    private JLabel jlIsbn;
    private JLabel jlBookName;
    private JLabel jlBookPrice;
    private JLabel jlBookAuthor;
    private JLabel jlPublishedHouse;
    private JLabel jlBookCategory;
    private JLabel jlBookStorage;
    private JLabel jlEnabledBorrow;

    private JTextField jtfIsbn;
    private JTextField jtfBookName;
    private JTextField jtfBookPrice;
    private JTextField jtfBookAuthor;
    private JTextField jtfPublishedHouse;
    private JTextField jtfBookCategory;
    private JTextField jtfBookStorage;
    private JTextField jtfEnabledBorrow;

    private JButton jbFirst;
    private JButton jbPre;
    private JButton jbNext;
    private JButton jbLast;
    private JButton jbAdd;
    private JButton jbDelete;
    private JButton jbModify;
    private JButton jbReset;

    public BookInformationFrame() {
        JFrame frame = new JFrame("图书信息管理");
        c = frame.getContentPane();
        c.setLayout(null);
        new BgImageUtils(frame,c,"bg.jpg");
        new MenuUtils(frame);
        initComponents();
        FrameUtils.bigFrame(frame);
    }

    private void initComponents() {
        jlTitle = new JLabel("图书信息管理");
        setTitle();

        comboBox = new JComboBox<>();
        setComboBox();
        jtfSearch = new JTextField();
        jbSearch = new JButton("检索");
        setSearch();

        scrollPane = new JScrollPane();
        pageNo = 1;
        pageSize = 7;
        maxPageNo = BookAction.countBook(pageSize);
        setTable(null,pageNo,pageSize);

        jlIsbn = new JLabel("ISBN");
        jtfIsbn = new JTextField();
        setIsbn();
        jlBookName = new JLabel("书名");
        jtfBookName = new JTextField();
        setBookName();
        jlBookPrice = new JLabel("价格");
        jtfBookPrice = new JTextField();
        setBookPrice();
        jlBookAuthor = new JLabel("作者");
        jtfBookAuthor = new JTextField();
        setBookAuthor();
        jlPublishedHouse = new JLabel("出版社");
        jtfPublishedHouse = new JTextField();
        setPublishedHouse();
        jlBookCategory = new JLabel("分类号");
        jtfBookCategory = new JTextField();
        setBookCategory();
        jlBookStorage = new JLabel("馆藏数");
        jtfBookStorage = new JTextField();
        setBookStorage();
        jlEnabledBorrow = new JLabel("在馆数");
        jtfEnabledBorrow = new JTextField();
        setEnabledBorrow();

        jbFirst = new JButton("首页");
        setButFirst();
        jbPre = new JButton("上一页");
        setButPre();
        jbNext = new JButton("下一页");
        setButNext();
        jbLast = new JButton("末页");
        setButLast();
        jbAdd = new JButton("添加");
        setButAdd();
        jbDelete = new JButton("删除");
        setButDelete();
        jbModify = new JButton("修改");
        setButModify();
        jbReset = new JButton("重置");
        setButReset();

        setContainer();
    }

    private void setContainer() {
        c.add(jlTitle);

        c.add(comboBox);
        c.add(jtfSearch);
        c.add(jbSearch);

        c.add(scrollPane);

        c.add(jtfIsbn);
        c.add(jtfBookName);
        c.add(jtfBookPrice);
        c.add(jtfBookAuthor);
        c.add(jtfPublishedHouse);
        c.add(jtfBookCategory);
        c.add(jtfBookStorage);
        c.add(jtfEnabledBorrow);

        c.add(jbFirst);
        c.add(jbPre);
        c.add(jbNext);
        c.add(jbLast);
        c.add(jbAdd);
        c.add(jbDelete);
        c.add(jbModify);
        c.add(jbReset);

        c.add(jlIsbn);
        c.add(jlBookName);
        c.add(jlBookPrice);
        c.add(jlBookAuthor);
        c.add(jlPublishedHouse);
        c.add(jlBookCategory);
        c.add(jlBookStorage);
        c.add(jlEnabledBorrow);
    }

    private void setTitle() {
        jlTitle.setBounds(350,10,500,25);
        jlTitle.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setSearch() {
        jtfSearch.setBounds(220, 50, 350, 25);
        jbSearch.setBounds(590, 50, 60, 25);
        jbSearch.addActionListener(e -> {
            String searchText = jtfSearch.getText();
            if (StringUtils.isEmpty(searchText)) {
                JOptionPane.showMessageDialog(c, "检索内容不能为空");
                maxPageNo = BookAction.countBook(pageSize);
                setTable(null, pageNo, pageSize);
            } else {
                maxPageNo = 1;
                setTable(searchText, pageNo, pageSize);
            }
        });
    }

    private void setComboBox() {
        comboBox.addItem("书名");
        comboBox.addItem("ISBN");
        comboBox.addItem("作者");
        comboBox.addItem("分类号");
        comboBox.addItem("出版社");
        comboBox.setBounds(120, 50, 80, 25);
        comboBox.addActionListener(e -> {
            int selIndex = comboBox.getSelectedIndex();
            if (selIndex == INDEX_ZERO) {
                selItem = "bookName";
            } else if (selIndex == INDEX_ONE) {
                selItem = "ISBN";
            } else if (selIndex == INDEX_TWO) {
                selItem = "bookAuthor";
            } else if (selIndex == INDEX_THREE) {
                selItem = "bookCategory";
            } else if (selIndex == INDEX_FOUR) {
                selItem = "publishedHouse";
            }
        });
    }

    private void setTable(String searchText, int pageNo, int pageSize) {
        String[] colNames = {"ISBN", "图书名称", "图书作者", "图书价格(￥)", "出版社", "分类号","馆藏数","在馆数"};
        Object[][] results = BookAction.selTable(colNames, selItem, searchText, pageNo, pageSize);
        table = new JTable(results, colNames);
        scrollPane.setViewportView(table);
        scrollPane.setBounds(15, 100, 760, 135);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = table.getSelectedRow();
                jtfIsbn.setText(table.getValueAt(selectRow, 0).toString());
                jtfBookName.setText(table.getValueAt(selectRow, 1).toString());
                jtfBookAuthor.setText(table.getValueAt(selectRow, 2).toString());
                jtfBookPrice.setText(table.getValueAt(selectRow, 3).toString());
                jtfPublishedHouse.setText(table.getValueAt(selectRow, 4).toString());
                jtfBookCategory.setText(table.getValueAt(selectRow, 5).toString());
                jtfBookStorage.setText(table.getValueAt(selectRow, 6).toString());
                jtfEnabledBorrow.setText(table.getValueAt(selectRow, 7).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void setButFirst() {
        jbFirst.setBounds(120, 250, 80, 25);
        jbFirst.addActionListener(e -> {
            if (pageNo == 1) {
                JOptionPane.showMessageDialog(c, "已经是首页了");
            } else {
                pageNo = 1;
                setTable(null, pageNo, pageSize);
            }
        });
    }

    private void setButPre() {
        jbPre.setBounds(270, 250, 80, 25);
        jbPre.addActionListener(e -> {
            if (pageNo == 1) {
                JOptionPane.showMessageDialog(c, "这是首页没有上一页了");
            } else {
                pageNo--;
                setTable(null, pageNo, pageSize);
            }
        });
    }

    private void setButNext() {
        jbNext.setBounds(420, 250, 80, 25);
        jbNext.addActionListener(e -> {
            if (pageNo == maxPageNo) {
                JOptionPane.showMessageDialog(c, "这是末页没有下一页了");
            } else {
                pageNo++;
                setTable(null, pageNo, pageSize);
            }
        });
    }

    private void setButLast() {
        jbLast.setBounds(570, 250, 80, 25);
        jbLast.addActionListener(e -> {
            if (pageNo == maxPageNo) {
                JOptionPane.showMessageDialog(c, "已经是末页了");
            } else {
                pageNo = maxPageNo;
                setTable(null, pageNo, pageSize);
            }
        });
    }

    private void setButReset() {
        jbReset.setBounds(120, 400, 60, 25);
        jbReset.addActionListener(e -> {
            jtfIsbn.setText("");
            jtfBookAuthor.setText("");
            jtfBookPrice.setText("");
            jtfBookName.setText("");
            jtfBookCategory.setText("");
            jtfPublishedHouse.setText("");
            jtfBookStorage.setText("");
            jtfEnabledBorrow.setText("");
        });
    }

    private void setButAdd() {
        jbAdd.setBounds(275, 400, 60, 25);
        jbAdd.addActionListener(e -> {
            String isbn = jtfIsbn.getText();
            String bookName = jtfBookName.getText();
            String bookPrice = jtfBookPrice.getText();
            String bookAuthor = jtfBookAuthor.getText();
            String publishedHouse = jtfPublishedHouse.getText();
            String bookCategory = jtfBookCategory.getText();
            String tfBookStorage = jtfBookStorage.getText();
            String tfEnabledBorrow = jtfEnabledBorrow.getText();
            if (StringUtils.isEmpty(bookAuthor) || StringUtils.isEmpty(bookCategory)
                    || StringUtils.isEmpty(bookName) || StringUtils.isEmpty(publishedHouse)
                    || StringUtils.isEmpty(isbn) || StringUtils.isEmpty(bookPrice)
                    ||StringUtils.isEmpty(tfBookStorage)||StringUtils.isEmpty(tfEnabledBorrow)) {
                JOptionPane.showMessageDialog(c, "请完善该书籍相关信息！");
            } else {
                int count  = BookAction.addBookInf(isbn, bookName, bookPrice, bookAuthor, publishedHouse, bookCategory,tfBookStorage,tfEnabledBorrow);
                if(count>0){
                    JOptionPane.showMessageDialog(c,"添加成功");
                    setTable(null,pageNo,pageSize);
                }else{
                    JOptionPane.showMessageDialog(c,"添加失败");
                }
            }
        });
    }

    private void setButDelete() {
        jbDelete.setBounds(440, 400, 60, 25);
        jbDelete.addActionListener(e -> {
            int selectRow = table.getSelectedRow();
            String isbn = table.getValueAt(selectRow, 0).toString();
            int count = BookAction.delBookInf(isbn);
            if(count>0){
                JOptionPane.showMessageDialog(c,"删除成功");
                setTable(null,pageNo,pageSize);
            }else{
                JOptionPane.showMessageDialog(c,"删除失败");
            }
        });
    }

    private void setButModify() {
        jbModify.setBounds(590, 400, 60, 25);
        jbModify.addActionListener(e -> {
            String isbn = jtfIsbn.getText();
            String bookName = jtfBookName.getText();
            String bookPrice = jtfBookPrice.getText();
            String bookAuthor = jtfBookAuthor.getText();
            String publishedHouse = jtfPublishedHouse.getText();
            String bookCategory = jtfBookCategory.getText();
            String tfBookStorage = jtfBookStorage.getText();
            String tfEnabledBorrow = jtfEnabledBorrow.getText();
            if (StringUtils.isEmpty(bookAuthor) || StringUtils.isEmpty(bookCategory)
                    || StringUtils.isEmpty(bookName) || StringUtils.isEmpty(publishedHouse)
                    || StringUtils.isEmpty(isbn) || StringUtils.isEmpty(bookPrice)
                    ||StringUtils.isEmpty(tfBookStorage)||StringUtils.isEmpty(tfEnabledBorrow)) {
                JOptionPane.showMessageDialog(c, "请完善该书籍相关信息！");
            } else {
                int count = BookAction.modifyBookInf(table,isbn, bookName, bookPrice, bookAuthor, publishedHouse, bookCategory,tfBookStorage,tfEnabledBorrow);
                if(count>0){
                    JOptionPane.showMessageDialog(c,"修改成功");
                    setTable(null,pageNo,pageSize);
                }else{
                    JOptionPane.showMessageDialog(c,"修改失败");
                }
            }
        });
    }

    private void setEnabledBorrow() {
        jtfEnabledBorrow.setBounds(582, 320, 140, 23);
        jlEnabledBorrow.setBounds(500, 320, 100, 25);
        jlEnabledBorrow.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setBookStorage() {
        jtfBookStorage.setBounds(348, 320, 140, 23);
        jlBookStorage.setBounds(270, 320, 100, 25);
        jlBookStorage.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setBookCategory() {
        jtfBookCategory.setBounds(582, 350, 140, 23);
        jlBookCategory.setBounds(500, 350, 100, 25);
        jlBookCategory.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setPublishedHouse() {
        jtfPublishedHouse.setBounds(348, 350, 140, 23);
        jlPublishedHouse.setBounds(270, 350, 100, 25);
        jlPublishedHouse.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setBookAuthor() {
        jtfBookAuthor.setBounds(582, 290, 140, 23);
        jlBookAuthor.setBounds(522, 290, 100, 25);
        jlBookAuthor.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setBookPrice() {
        jtfBookPrice.setBounds(120, 350, 140, 23);
        jlBookPrice.setBounds(60, 350, 100, 25);
        jlBookPrice.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setBookName() {
        jtfBookName.setBounds(348, 290, 140, 23);
        jlBookName.setBounds(288, 290, 100, 25);
        jlBookName.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setIsbn() {
        jtfIsbn.setBounds(120, 290, 140, 23);
        jlIsbn.setBounds(60, 290, 100, 25);
        jlIsbn.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

}
