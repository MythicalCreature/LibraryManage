package com.view;

import com.controller.BookAction;
import com.controller.BorrowAction;
import com.dao.BorrowDao;
import com.model.Borrow;
import com.utils.DateUtils;
import com.utils.FrameUtils;
import com.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * @author zzx
 * 2020/5/3 11:37
 */
public class ReturnBookFrame {
    private JTextField userNameText;
    private JButton ensure;

    private JScrollPane scrollPane;
    private JTable table;

    private JLabel jlBookName;
    private JLabel jlBorrowTime;
    private JLabel jlExceptTime;
    private JTextField jtfBookName;
    private JTextField jtfBorrowTime;
    private JTextField jtfExceptTime;
    private JButton jbReturn;

    private Panel panButton;
    private Panel panLabel;
    public ReturnBookFrame(){
        JFrame frame = new JFrame("还书窗口");
        Container c = frame.getContentPane();
        JLabel mes = new JLabel("请输入还书的用户名");
        mes.setBounds(40,15,250,20);
        userNameText = new JTextField();
        userNameText.setBounds(40,40,140,23);
        ensure = new JButton("确认");
        setEnsure(frame);
        c.add(mes);
        c.add(userNameText);
        c.add(ensure);
        FrameUtils.smallFrame(frame);
    }

    private void setFrame(String userName, List<Borrow> curBorrow) {
        JFrame frame = new JFrame("填写还书信息");
        jlBookName = new JLabel("图书名称：");
        jlBorrowTime = new JLabel("借书日期：");
        jlExceptTime = new JLabel("应归还日期：");
        jtfBookName = new JTextField();
        jtfBorrowTime = new JTextField();
        jtfExceptTime = new JTextField();
        scrollPane = new JScrollPane();
        setTable(curBorrow);
        panButton = new Panel();
        panLabel = new Panel();
        jlBookName.setBounds(10,0,100,100);
        jlBookName.setHorizontalAlignment(JLabel.RIGHT);
        jtfBookName.setBounds(110,35,150,30);
        jlBorrowTime.setBounds(280,0,100,100);
        jlBorrowTime.setHorizontalAlignment(JLabel.RIGHT);
        jtfBorrowTime.setBounds(380,35,150,30);
        jlExceptTime.setBounds(10,60,100,100);
        jlExceptTime.setHorizontalAlignment(JLabel.RIGHT);
        jtfExceptTime.setBounds(110,95,150,30);
        jbReturn = new JButton("确认");
        setContainer(frame);
        setReturn(userName);
        FrameUtils.middleFrame(frame);
    }

    private void setReturn(String userName) {
        jbReturn.addActionListener(e -> {
            String isbn = table.getValueAt(table.getSelectedRow(),0).toString();
            String bookName = jtfBookName.getText();
            String borrowDate = jtfBorrowTime.getText();
            String realDate = DateUtils.getTime();
            int count = BorrowAction.addReturnInf(userName,isbn,bookName,borrowDate,realDate);
            if(count>0){
                JOptionPane.showMessageDialog(null,"还书成功");
                BookAction.addEnabledBorrow(isbn);
                BorrowAction.delBorrowInf(isbn);
                Borrow borrow = new Borrow();
                borrow.setUserName(userName);
                List<Borrow> curBorrow = BorrowDao.searchBorrowBook(borrow);
                setTable(curBorrow);
            }else{
                JOptionPane.showMessageDialog(null,"还书失败");
            }
        });
    }

    private void setContainer(JFrame frame) {
        panButton.add(jbReturn);
        // 设置中间的panel布局为空
        panLabel.setLayout(null);

        panLabel.add(jlBookName);
        panLabel.add(jlBorrowTime);
        panLabel.add(jlExceptTime);
        panLabel.add(jtfBookName);
        panLabel.add(jtfBorrowTime);
        panLabel.add(jtfExceptTime);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(panLabel, BorderLayout.CENTER);
        frame.add(panButton, BorderLayout.SOUTH);

    }

    private void setTable(List<Borrow> curBorrow) {
        String[] colNames = {"ISBN","图书名称", "借书时间","应归还时间"};
        Object[][] results = BorrowAction.renewTable(colNames,curBorrow);
        table = new JTable(results, colNames);
        // 设置可见视图的接口
        scrollPane.setViewportView(table);
        // 定义表格 宽600，高度200
        scrollPane.setPreferredSize(new Dimension(600, 200));
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = table.getSelectedRow();
                jtfBookName.setText(table.getValueAt(selectRow, 1).toString());
                jtfBorrowTime.setText(table.getValueAt(selectRow,2).toString());
                jtfExceptTime.setText(table.getValueAt(selectRow,3).toString());
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

        // 使书号的文本框不可编辑
        jtfBookName.setEditable(false);
        jtfBorrowTime.setEditable(false);
        jtfExceptTime.setEditable(false);
    }

    private void setEnsure(JFrame f) {
        ensure.setBounds(200,40,60,25);
        ensure.addActionListener(e -> {
            if(StringUtils.isEmpty(userNameText.getText())){
                JOptionPane.showMessageDialog(null,"用户名不能为空");
            }else {
                Borrow borrow = new Borrow();
                String userName = userNameText.getText();
                borrow.setUserName(userName);
                List<Borrow> curBorrow = BorrowDao.searchBorrowBook(borrow);
                if(curBorrow !=null){
                    JOptionPane.showMessageDialog(null,"请选择要还的书");
                    f.dispose();
                    setFrame(userName,curBorrow);
                }else{
                    JOptionPane.showMessageDialog(null,"该用户无借书信息");
                }
            }
        });
    }
}
