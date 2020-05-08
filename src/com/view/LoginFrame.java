package com.view;

import com.controller.MouseAction;
import com.dao.ManagerDao;
import com.model.Manager;
import com.utils.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 * @author zzx
 * 2020/4/29 21:06
 */
public class LoginFrame {
    private JFrame jFrame;
    private Container c;
    private TitledBorder tb;
    private JLabel jlTitle;
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel missPsw;
    private JButton login;
    public LoginFrame(){
        setProjectFont();
        initComponents();
    }

    private void setProjectFont() {
        Font font = new Font("Dialog", Font.PLAIN, 12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    private void initComponents(){
        jFrame = new JFrame();
        c = jFrame.getContentPane();
        c.setBackground(Color.white);
        c.setLayout(null);
        jFrame.setSize(420,350);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(c);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MouseAction ma = new MouseAction(jFrame);
        jFrame.addMouseMotionListener(ma);
        jFrame.addMouseListener(ma);
        jlTitle = new JLabel("图书管理系统");
        setJlTitle();
        c.add(jlTitle);
        textField = new JTextField();
        tb = new TitledBorder("账号");
        setTextField();
        c.add(textField);

        missPsw = new JLabel("找回密码");
        setMissPsw();
        c.add(missPsw);

        passwordField = new JPasswordField();
        tb = new TitledBorder("密码");
        setPswField();
        c.add(passwordField);

        login = new JButton("登录");
        setLogin();
        c.add(login);

        c.validate();
        jFrame.setVisible(true);
    }

    private void setJlTitle() {
        URL url = LoginFrame.class.getResource("/images/logo.png");
        jlTitle.setIcon(new ImageIcon(url));
        jlTitle.setFont(new Font("楷体",Font.PLAIN,24));
        jlTitle.setBounds(100,35,300,100);
    }

    private void setPswField() {
        tb.setTitleFont(new Font("楷体",Font.PLAIN,10));
        passwordField.setBorder(tb);
        passwordField.setBounds(100,200,220,38);
    }

    private void setTextField() {
        tb.setTitleFont(new Font("楷体",Font.PLAIN,10));
        textField.setBorder(tb);
        textField.setBounds(100,155,220,38);
    }

    private void setMissPsw() {
        missPsw.setFont(new Font("楷体",Font.PLAIN,14));
        missPsw.setBounds(263,235,100,25);
        missPsw.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChangePasswordFrame();
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

    private void setLogin() {
        login.setFont(new Font("楷体",Font.PLAIN,18));
        login.setBounds(100,265,220,25);
        login.addActionListener(e -> {
            String userName = textField.getText();
            String password = new String(passwordField.getPassword());
            if(StringUtils.isEmpty(userName)){
                JOptionPane.showMessageDialog(c,"用户名不能为空");
            }else if(StringUtils.isEmpty(password)){
                JOptionPane.showMessageDialog(c,"密码不能为空");
            }else {
                Manager manager = new Manager(userName,password);
                Manager curManager = ManagerDao.login(manager);
                if(curManager !=null){
                    JOptionPane.showMessageDialog(c,"登录成功");
                    jFrame.dispose();
                    new MainFrame();
                }else{
                    JOptionPane.showMessageDialog(c,"用户名或密码错误");
                }
            }
        });
    }
}
