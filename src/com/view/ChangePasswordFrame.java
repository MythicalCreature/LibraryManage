package com.view;

import com.utils.FrameUtils;
import com.utils.StringUtils;
import com.dao.ManagerDao;
import com.model.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/4/29 21:08
 */
public class ChangePasswordFrame {
    private JLabel mes;
    private JLabel jl1;
    private JLabel jl2;
    private JTextField jt1;
    private JTextField jt2;
    private JTextField userName;
    private JButton ensure;
    private JButton ensurePsw;
    public ChangePasswordFrame(){
        JFrame frame = new JFrame("修改密码");
        Container c = frame.getContentPane();
        mes = new JLabel("请输入您的账号");
        setMes();
        userName = new JTextField();
        setUserName();
        ensure = new JButton("确认");
        setEnsure(frame);
        ensurePsw = new JButton("确认");
        c.add(mes);
        c.add(userName);
        c.add(ensure);
        FrameUtils.smallFrame(frame);

    }

    private void setPsw() {
        JFrame frame = new JFrame("修改密码");
        Container c = frame.getContentPane();
        jl1 = new JLabel("请设置新密码：");
        jt1 = new JTextField();
        jl2 = new JLabel("重新输入密码：");
        jt2 = new JTextField();
        setPassword();
        c.add(jl1);
        c.add(jl2);
        c.add(jt1);
        c.add(jt2);
        c.add(ensurePsw);
        FrameUtils.smallFrame(frame);
    }

    private void setPassword() {
        jl2.setBounds(15,100,150,25);
        jt2.setBounds(100,100,150,25);
        jl1.setBounds(15,50,150,25);
        jt1.setBounds(100,50,150,25);
        ensurePsw.setBounds(265,100,60,25);
        ensurePsw.addActionListener(e -> {
            String psw = jt1.getText();
            String pswAgain = jt2.getText();
            if(StringUtils.isEmpty(psw)||StringUtils.isEmpty(pswAgain)){
                JOptionPane.showMessageDialog(null,"密码不能为空");
            }else if(!(psw.equals(pswAgain))){
                JOptionPane.showMessageDialog(null,"两次输入的密码不一致，请重新输入");
            }else {
                Manager manager = new Manager(userName.getText(),psw);
                Manager curManager = ManagerDao.login(manager);
                if(curManager !=null){
                    JOptionPane.showMessageDialog(null,"密码与原密码一致");
                }else{
                    int count = ManagerDao.update(manager);
                    if(count>0){
                        JOptionPane.showMessageDialog(null,"密码修改成功");
                    }else{
                        JOptionPane.showMessageDialog(null,"密码修改失败");
                    }
                }
            }
        });
    }

    private void setMes() {
        mes.setBounds(40,15,250,20);
    }

    private void setUserName() {
        userName.setBounds(40,40,140,23);
    }

    private void setEnsure(JFrame f) {
        ensure.setBounds(200,40,60,25);
        ensure.addActionListener(e -> {
            Manager manager = new Manager();
            manager.setUserName(userName.getText());
            if(StringUtils.isEmpty(userName.getText())){
                JOptionPane.showMessageDialog(null,"用户名不能为空");
            }else {
                Manager curManager = ManagerDao.queryUserName(manager);
                if(curManager !=null){
                    JOptionPane.showMessageDialog(null,"请设置新密码");
                    f.dispose();
                    setPsw();
                }else{
                    JOptionPane.showMessageDialog(null,"用户名不存在");
                }
            }
        });
    }
}
