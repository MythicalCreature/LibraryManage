package com.view;

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
 * 2020/5/2 17:53
 */
public class RenewBookFrame {
    private JTextField userNameText;
    private JButton ensure;

    private JScrollPane scrollPane;
    private JTable table;

    private JLabel jlBookName;
    private JLabel jlBorrowTime;
    private JLabel jlExpectTime;
    private JTextField jtfBookName;
    private JTextField jtfBorrowTime;
    private JTextField jtfExpectTime;
    private JTextField timeText;
    private ButtonGroup bg;
    private JLabel jlTime;
    private JRadioButton jrbOneMonth;
    private JButton jbBorrow;

    private Panel panButton;
    private Panel panLabel;
    private String curTime;
    public RenewBookFrame() {
        JFrame frame = new JFrame("续借窗口");
        Container c = frame.getContentPane();
        JLabel mes = new JLabel("请输入需要续借的用户名");
        mes.setBounds(40, 15, 250, 20);
        userNameText = new JTextField();
        userNameText.setBounds(40, 40, 140, 23);
        ensure = new JButton("确认");
        setEnsure(frame);
        c.add(mes);
        c.add(userNameText);
        c.add(ensure);
        FrameUtils.smallFrame(frame);
    }

    private void setFrame(List<Borrow> curBorrow) {
        JFrame frame = new JFrame("修改续借信息");
        jlBookName = new JLabel("图书名称：");
        jlBorrowTime = new JLabel("借书日期：");
        jlExpectTime = new JLabel("应归还日期：");
        jtfBookName = new JTextField();
        jtfBorrowTime = new JTextField();
        jtfExpectTime = new JTextField();
        bg = new ButtonGroup();
        jlTime = new JLabel("续借期限：");
        jrbOneMonth = new JRadioButton("一个月");
        scrollPane = new JScrollPane();
        setTable(frame, curBorrow);
        panButton = new Panel();
        panLabel = new Panel();
        jlBookName.setBounds(10, 0, 100, 100);
        jlBookName.setHorizontalAlignment(JLabel.RIGHT);
        jtfBookName.setBounds(110, 35, 150, 30);
        jlBorrowTime.setBounds(280, 0, 100, 100);
        jlBorrowTime.setHorizontalAlignment(JLabel.RIGHT);
        jtfBorrowTime.setBounds(380, 35, 150, 30);
        jlExpectTime.setBounds(10, 60, 100, 100);
        jlExpectTime.setHorizontalAlignment(JLabel.RIGHT);
        jtfExpectTime.setBounds(110, 95, 150, 30);
        jrbOneMonth.setSelected(true);
        jrbOneMonth.setBounds(380, 95, 100, 30);
        jlTime.setBounds(280, 95, 100, 30);
        jlTime.setHorizontalAlignment(JLabel.RIGHT);
        jbBorrow = new JButton("确认");
        setNowTime(frame);
        setContainer(frame);
        FrameUtils.middleFrame(frame);
    }

    private void setNowTime(JFrame frame) {
        jbBorrow.addActionListener(e -> {
            int dialog = JOptionPane.showConfirmDialog(null, "测试阶段\n可自行设置当前时间以验证该功能的正确性\n选择“是”可设置当前时间\n其余选项将自动获取当前时间");
            curTime = DateUtils.getTime();
            if (dialog == 0) {
                JFrame f = new JFrame("设置时间");
                Container container = f.getContentPane();
                f.setLayout(null);
                f.setSize(350, 200);
                f.setLocationRelativeTo(null);
                f.setResizable(false);
                JLabel message = new JLabel("请输入当前时间yyyy-mm-dd");
                message.setBounds(40, 15, 250, 20);
                timeText = new JTextField();
                timeText.setBounds(40, 40, 140, 23);
                timeText.setText(curTime);
                JButton jbTime = new JButton("确认");
                jbTime.setBounds(200, 40, 60, 25);
                jbTime.addActionListener(e1 -> {
                    f.dispose();
                    setBorrow(frame);
                });
                container.add(message);
                container.add(timeText);
                container.add(jbTime);
                f.setVisible(true);
            }else {
                setBorrow(frame);
            }

        });
    }

    private void setBorrow(JFrame frame) {
        String isbn = table.getValueAt(table.getSelectedRow(), 0).toString();
        String bookName = jtfBookName.getText();
        String expectTime = jtfExpectTime.getText();
        String userName = userNameText.getText();
        Borrow borrow = new Borrow();
        borrow.setUserName(userName);
        borrow.setIsbn(isbn);
        borrow.setBookName(bookName);
        borrow.setExpectDate(expectTime);
        curTime = timeText.getText();
        int count = BorrowAction.addRenewInf(borrow, curTime);
        if (count > 0) {
            JOptionPane.showMessageDialog(null, "续借成功");
            Borrow b = new Borrow();
            b.setUserName(userName);
            List<Borrow> curBorrow = BorrowDao.searchBorrowBook(b);
            setTable(frame, curBorrow);
        }
    }

    private void setContainer(JFrame frame) {
        panButton.add(jbBorrow);
        // 设置中间的panel布局为空
        panLabel.setLayout(null);

        panLabel.add(jlBookName);
        panLabel.add(jlBorrowTime);
        panLabel.add(jlExpectTime);
        panLabel.add(jtfBookName);
        panLabel.add(jtfBorrowTime);
        panLabel.add(jtfExpectTime);
        bg.add(jrbOneMonth);
        panLabel.add(jrbOneMonth);
        panLabel.add(jlTime);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(panLabel, BorderLayout.CENTER);
        frame.add(panButton, BorderLayout.SOUTH);

    }

    private void setTable(JFrame frame, List<Borrow> curBorrow) {
        Container c = frame.getContentPane();
        String[] colNames = {"ISBN", "图书名称", "借书时间", "应归还时间", "是否可办理续借"};
        Object[][] results = BorrowAction.renewTable(colNames, curBorrow);
        table = new JTable(results, colNames);
        // 设置可见视图的接口
        scrollPane.setViewportView(table);
        // 定义表格 宽600，高度200
        scrollPane.setPreferredSize(new Dimension(600, 200));
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = table.getSelectedRow();
                String isNotRenew = "否";
                int column = 4;
                if (isNotRenew.equals(table.getValueAt(selectRow, column).toString())) {
                    jtfBookName.setText(null);
                    JOptionPane.showMessageDialog(c, "此书，已办理过续借无法再次办理请尽快归还");
                } else {
                    jtfBookName.setText(table.getValueAt(selectRow, 1).toString());
                    jtfBorrowTime.setText(table.getValueAt(selectRow, 2).toString());
                    jtfExpectTime.setText(table.getValueAt(selectRow, 3).toString());
                }
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
        jtfExpectTime.setEditable(false);
    }

    private void setEnsure(JFrame f) {
        ensure.setBounds(200, 40, 60, 25);
        ensure.addActionListener(e -> {
            if (StringUtils.isEmpty(userNameText.getText())) {
                JOptionPane.showMessageDialog(null, "用户名不能为空");
            } else {
                Borrow borrow = new Borrow();
                borrow.setUserName(userNameText.getText());
                List<Borrow> curBorrow = BorrowDao.searchBorrowBook(borrow);
                if (curBorrow != null) {
                    JOptionPane.showMessageDialog(null, "请选择要续借的书");
                    f.dispose();
                    setFrame(curBorrow);
                } else {
                    JOptionPane.showMessageDialog(null, "该用户无借书信息");
                }
            }
        });
    }
}
