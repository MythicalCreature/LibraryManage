package com.utils;

import com.view.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author zzx
 * 2020/4/29 21:22
 */
public class MenuUtils {
    private static JMenuItem jMIBookInf;
    private static JMenu jMBorrowManage;
    private static JMenuItem jMIBorrowBook;
    private static JMenuItem jMIRenewBook;
    private static JMenuItem jMIReturnBook;
    private static JMenuItem jMIUserInf;
    private static JMenu jMSetting;
    private static JMenuItem jMIModifyPassword;
    private static JMenuItem jMIChangeManager;
    private static JMenuItem jMIExit;
    private static JMenuItem jMIHelp;
    private static Container c;
    public MenuUtils(JFrame jFrame){
        c = jFrame.getContentPane();
        JMenuBar jMenuBar = new JMenuBar();
        jMIBookInf = new JMenuItem("图书信息");
        jMBorrowManage = new JMenu("借阅管理");
        jMIBorrowBook = new JMenuItem("借书");
        jMIRenewBook = new JMenuItem("续借");
        jMIReturnBook = new JMenuItem("还书");
        jMIUserInf = new JMenuItem("用户信息");
        jMSetting = new JMenu("设置");
        jMIModifyPassword = new JMenuItem("更改密码");
        jMIChangeManager = new JMenuItem("切换账号");
        jMIExit = new JMenuItem("退出系统");
        jMIHelp = new JMenuItem("帮助");
        jMenuBar.add(jMIBookInf);
        setJmiBookInf(jFrame);
        jMBorrowManage.add(jMIBorrowBook);
        jMBorrowManage.add(jMIRenewBook);
        jMBorrowManage.add(jMIReturnBook);
        jMenuBar.add(jMBorrowManage);
        setJmiBorrowManage();
        jMenuBar.add(jMIUserInf);
        setJmiUserInf(jFrame);
        jMSetting.add(jMIHelp);
        jMSetting.add(jMIModifyPassword);
        jMSetting.add(jMIChangeManager);
        //退出系统
        jMSetting.add(jMIExit);
        jMenuBar.add(jMSetting);
        setJmSetting(jFrame);
        jFrame.setJMenuBar(jMenuBar);
    }

    private void setJmSetting(JFrame jFrame) {
        jMSetting.setPreferredSize(new Dimension(140,10));
        setJmiHelp();
        setJmiChangeManager(jFrame);
        setJmiExit(jFrame);
        setJmiModifyPassword();

    }

    private void setJmiUserInf(JFrame jFrame) {
        jMIUserInf.addActionListener(e -> {
            jFrame.dispose();
            new UserInformationFrame();
        });
    }

    private void setJmiChangeManager(JFrame jFrame) {
        jMIChangeManager.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(c, "是否切换账号");
            if (result == 0) {
                jFrame.dispose();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                new LoginFrame();
            }
        });

    }

    private void setJmiBorrowManage() {
        jMBorrowManage.setPreferredSize(new Dimension(140,10));
        setJmiBorrow();
        setJmiRenew();
        setJmiReturn();
    }

    private void setJmiReturn() {
        jMIReturnBook.addActionListener(e -> new ReturnBookFrame());
    }

    private void setJmiRenew() {
        jMIRenewBook.addActionListener(e -> new RenewBookFrame());
    }

    private void setJmiBorrow() {
        jMIBorrowBook.addActionListener(e -> new BorrowBookFrame());
    }

    private void setJmiModifyPassword() {
        jMIModifyPassword.addActionListener(e -> new ChangePasswordFrame());
    }

    private static void setJmiHelp() {
        jMIHelp.addActionListener(e -> {
            JOptionPane.showMessageDialog(c,
    "项目：图书管理系统\n开始日期：Date 2020-04-14\n版本号：Version 1.0\n" + "目前已完善图书信息的增删查改所有功能\n以及退出系统、切换账号、更改密码功能\n当前日期：Date 2020-04-21");
            Runtime run = Runtime.getRuntime() ;
            try {
                run.exec("explorer http://120.79.209.3");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setJmiBookInf(JFrame jFrame) {
        jMIBookInf.addActionListener(e -> {
            jFrame.dispose();
            new BookInformationFrame();
        });
    }

    private static void setJmiExit(JFrame jFrame){
        jMIExit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(c, "是否退出系统");
            if (result == 0) {
                jFrame.dispose();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
