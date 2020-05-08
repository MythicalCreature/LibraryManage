package com.view;

import com.controller.UserAction;
import com.utils.BgImageUtils;
import com.utils.FrameUtils;
import com.utils.MenuUtils;
import com.utils.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/4/29 23:32
 */
public class UserInformationFrame {
    private Container c;

    private JLabel jlTitle;

    private JComboBox<String> comboBox;
    private String selItem;
    private final int INDEX_ZERO = 0;
    private final int INDEX_ONE = 1;
    private JTextField jtfSearch;
    private JButton jbSearch;

    private JTable table;
    private JScrollPane scrollPane;
    private int maxPageNo;
    private int pageNo;
    private int pageSize;

    private JButton jbFirst;
    private JButton jbPre;
    private JButton jbNext;
    private JButton jbLast;

    public UserInformationFrame() {
        JFrame jFrame = new JFrame();
        c = jFrame.getContentPane();
        c.setLayout(null);
        new MenuUtils(jFrame);
        new BgImageUtils(jFrame, c, "bg.jpg");
        initComponents();
        FrameUtils.bigFrame(jFrame);
    }

    private void setContainer() {
        c.add(jlTitle);

        c.add(comboBox);
        c.add(jtfSearch);
        c.add(jbSearch);

        c.add(scrollPane);

        c.add(jbFirst);
        c.add(jbPre);
        c.add(jbNext);
        c.add(jbLast);
    }

    private void initComponents() {
        jlTitle = new JLabel("用户信息管理");
        setTitle();

        comboBox = new JComboBox<>();
        setComboBox();
        jtfSearch = new JTextField();
        jbSearch = new JButton("检索");
        setSearch();

        scrollPane = new JScrollPane();
        pageNo = 1;
        pageSize = 7;
        maxPageNo = UserAction.countUser(pageSize);
        setTable(null, pageNo, pageSize);

        jbFirst = new JButton("首页");
        setButFirst();
        jbPre = new JButton("上一页");
        setButPre();
        jbNext = new JButton("下一页");
        setButNext();
        jbLast = new JButton("末页");
        setButLast();

        setContainer();
    }

    private void setTitle() {
        jlTitle.setBounds(350, 10, 500, 25);
        jlTitle.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 22));
    }

    private void setSearch() {
        jtfSearch.setBounds(220, 50, 350, 25);
        jbSearch.setBounds(590, 50, 60, 25);
        jbSearch.addActionListener(e -> {
            String searchText = jtfSearch.getText();
            if (StringUtils.isEmpty(searchText)) {
                JOptionPane.showMessageDialog(c, "检索内容不能为空");
                maxPageNo = UserAction.countUser(pageSize);
                setTable(null, pageNo, pageSize);
            } else {
                maxPageNo = 1;
                setTable(searchText, pageNo, pageSize);
            }
        });
    }

    private void setComboBox() {
        comboBox.addItem("用户名");
        comboBox.addItem("手机号码");
        comboBox.setBounds(120, 50, 80, 25);
        comboBox.addActionListener(e -> {
            int selIndex = comboBox.getSelectedIndex();
            if (selIndex == INDEX_ZERO) {
                selItem = "userName";
            } else if (selIndex == INDEX_ONE) {
                selItem = "phoneNumber";
            }
        });
    }

    private void setTable(String searchText, int pageNo, int pageSize) {
        String[] colNames = {"用户名","密码","手机号码"};
        Object[][] results = UserAction.selTable(colNames, selItem, searchText, pageNo, pageSize);
        table = new JTable(results, colNames);
        scrollPane.setViewportView(table);
        scrollPane.setBounds(15, 100, 760, 135);
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

}
