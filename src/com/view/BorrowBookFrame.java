package com.view;

import com.controller.BookAction;
import com.controller.BorrowAction;
import com.dao.UserDao;
import com.model.User;
import com.utils.DateUtils;
import com.utils.FrameUtils;
import com.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zzx
 * 2020/4/29 23:19
 */
public class BorrowBookFrame {
    private JButton ensure;

    private Container container;

    private JScrollPane scrollPane;
    private JTable table;

    private JRadioButton jrbBookName;
    private JRadioButton jrbBookAuthor;
    private ButtonGroup bg1;
    private String selItem;
    private JTextField jtfSearch;
    private JButton jbSearch;

    private JLabel jlBookName;
    private JLabel jlBookAuthor;
    private JLabel jlBorrowTime;

    private JTextField userNameText;
    private JTextField jtfBookName;
    private JTextField jtfBookAuthor;
    private JTextField jtfBorrowTime;

    private ButtonGroup bg2;
    private JLabel jlTime;
    private JRadioButton jrbOneMonth;
    private JRadioButton jrbTwoMonth;
    private JButton jbBorrow;
    private Panel panButton;
    private Panel panLabel;
    private String time;
    public BorrowBookFrame() {
        JFrame frame = new JFrame("借书窗口");
        Container c = frame.getContentPane();
        JLabel mes = new JLabel("请输入需要借书的用户名");
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

    private void setFrame() {
        JFrame jFrame = new JFrame("填写还书信息");
        container = jFrame.getContentPane();
        FrameUtils.middleFrame(jFrame);
        bg1 = new ButtonGroup();
        jrbBookName = new JRadioButton("按书名查找");
        jrbBookAuthor = new JRadioButton("按作者查找");
        jrbBookName.setSelected(true);
        jtfSearch = new JTextField();
        jbSearch = new JButton("查找");
        setSearch();
        jlBookName = new JLabel("图书名称：");
        jlBookAuthor = new JLabel("图书作者：");
        jlBorrowTime = new JLabel("借书日期：");
        jtfBookName = new JTextField();
        jtfBookAuthor = new JTextField();
        jtfBorrowTime = new JTextField();
        bg2 = new ButtonGroup();
        jlTime = new JLabel("借书期限：");
        jrbOneMonth = new JRadioButton("一个月");
        jrbTwoMonth = new JRadioButton("两个月");
        jbBorrow = new JButton("借书");
        scrollPane = new JScrollPane();

        setTable(null,selItem);
        panButton = new Panel();
        panLabel = new Panel();

        // 给标签和文本框设置位置
        jlBookAuthor.setBounds(10, 40, 100, 100);
        jlBookAuthor.setHorizontalAlignment(JLabel.RIGHT);
        jtfBookAuthor.setBounds(110, 75, 150, 30);

        jlBookName.setBounds(280, 40, 100, 100);
        jlBookName.setHorizontalAlignment(JLabel.RIGHT);
        jtfBookName.setBounds(380, 75, 150, 30);

        jlBorrowTime.setBounds(10, 100, 100, 100);
        jlBorrowTime.setHorizontalAlignment(JLabel.RIGHT);
        jtfBorrowTime.setBounds(110, 135, 150, 30);
        // 自动获取借书时间(当前时间)
        time = DateUtils.getTime();
        jtfBorrowTime.setText(time);

        jrbOneMonth.setSelected(true);
        jlTime.setBounds(280,135,100,30);
        jlTime.setHorizontalAlignment(JLabel.RIGHT);
        jrbOneMonth.setBounds(380, 135, 100, 30);
        jrbTwoMonth.setBounds(480, 135, 100, 30);
        
        setContainer(jFrame);
        setBorrow();
    }

    private void setSearch() {
        jrbBookName.setBounds(40, 10, 100, 30);
        jrbBookAuthor.setBounds(140, 10, 100, 30);
        if(jrbBookAuthor.isSelected()){
            selItem = "bookAuthor";
        } else{
            selItem = "bookName";
        }
        jtfSearch.setBounds(250, 10, 160, 30);
        jbSearch.setBounds(450, 10, 80, 30);
        jbSearch.addActionListener(e -> {
            String searchText = jtfSearch.getText();
            if (StringUtils.isEmpty(searchText)) {
                JOptionPane.showMessageDialog(container, "检索内容不能为空");
                setTable(null,selItem);
            } else {
                setTable(searchText,selItem);
            }
        });
    }

    private void setTable(String searchText,String selItem) {
        String[] colNames = {"ISBN", "图书名称", "图书作者", "图书价格(￥)", "是否可借"};
        Object[][] results = BorrowAction.selTable(colNames,searchText,selItem);
        if(results==null){
            JOptionPane.showMessageDialog(container,"查询结果为空\n请重新查询");
            setTable(null,selItem);
        }else {
            table = new JTable(results, colNames);
            scrollPane.setViewportView(table);
            scrollPane.setPreferredSize(new Dimension(600, 150));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            // 获取表里的值
            table.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectRow = table.getSelectedRow();
                    String isNotBorrow = "否";
                    int column = 4;
                    if (isNotBorrow.equals(table.getValueAt(selectRow, column).toString())) {
                        jtfBookName.setText(null);
                        jtfBookAuthor.setText(null);
                        JOptionPane.showMessageDialog(container, "书库无此书，\n无法借阅，\n请选择其他书");
                    } else {
                        jtfBookName.setText(table.getValueAt(selectRow, 1).toString());
                        jtfBookAuthor.setText(table.getValueAt(selectRow, 2).toString());
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
            jtfBookAuthor.setEditable(false);
            jtfBorrowTime.setEditable(true);
        }
    }

    private void setBorrow() {
        JOptionPane.showMessageDialog(container,"测试阶段\n可自行设置当前时间以验证该功能的正确性");
        jbBorrow.addActionListener(arg0 -> {
            time = jtfBorrowTime.getText();
            int year = DateUtils.subYear(time);
            int month = DateUtils.subMonth(time);
            int day = DateUtils.subDay(time);
            String expectDate;
            if(jrbTwoMonth.isSelected()){
                expectDate = DateUtils.expect(year,month,day);
                year = DateUtils.subYear(expectDate);
                month = DateUtils.subMonth(expectDate);
                day = DateUtils.subDay(expectDate);
                expectDate = DateUtils.expect(year,month,day);
            }else{
                expectDate = DateUtils.expect(year,month,day);
            }
            String userName = userNameText.getText();
            String bookName = jtfBookName.getText();
            String borrowDate = jtfBorrowTime.getText();
            if(StringUtils.isEmpty(bookName)){
                JOptionPane.showMessageDialog(container,"请先选择要借的书");
            }else{
                int selRow = table.getSelectedRow();
                String isbn = table.getValueAt(selRow,0).toString();
                int count = BorrowAction.addBorrowInf(userName,isbn,bookName, borrowDate, expectDate);
                if(count>0){
                    JOptionPane.showMessageDialog(container,"借书成功，\n请在"+expectDate+"之前归还或办理续借");
                    BookAction.delEnabledBorrow(isbn);
                    setTable(null,selItem);
                }else{
                    JOptionPane.showMessageDialog(container,"借书失败");
                }
            }
        });
    }

    private void setContainer(JFrame jFrame) {
        panButton.add(jbBorrow);
        panLabel.setLayout(null);
        // 默认选择JB1
        // 加入组，避免出现可以两个都选择的情况
        bg1.add(jrbBookName);
        bg1.add(jrbBookAuthor);
        panLabel.add(jrbBookName);
        panLabel.add(jrbBookAuthor);
        panLabel.add(jtfSearch);
        panLabel.add(jbSearch);

        panLabel.add(jlBookName);
        panLabel.add(jlBookAuthor);
        panLabel.add(jlBorrowTime);
        panLabel.add(jtfBookName);
        panLabel.add(jtfBookAuthor);
        panLabel.add(jtfBorrowTime);

        bg2.add(jrbOneMonth);
        bg2.add(jrbTwoMonth);
        panLabel.add(jrbOneMonth);
        panLabel.add(jrbTwoMonth);
        panLabel.add(jlTime);
        jFrame.add(scrollPane, BorderLayout.NORTH);
        jFrame.add(panLabel, BorderLayout.CENTER);
        jFrame.add(panButton, BorderLayout.SOUTH);
    }

    private void setEnsure(JFrame f) {
        ensure.setBounds(200,40,60,25);
        ensure.addActionListener(e -> {
            if(StringUtils.isEmpty(userNameText.getText())){
                JOptionPane.showMessageDialog(null,"用户名不能为空");
            }else {
                User user = new User();
                user.setUserName(userNameText.getText());
                User curUser = UserDao.queryUserName(user);
                if(curUser !=null){
                    JOptionPane.showMessageDialog(null,"请选择要借的书");
                    f.dispose();
                    setFrame();
                }else{
                    JOptionPane.showMessageDialog(null,"无此用户信息");
                }
            }
        });
    }

}
