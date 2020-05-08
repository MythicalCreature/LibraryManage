package com.utils;

import com.view.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author zzx
 * 2020/4/29 21:21
 */
public class BgImageUtils {
    public BgImageUtils(JFrame jFrame, Container c, String imageName) {
        URL url =BgImageUtils.class.getResource("/images/"+imageName);
        ImageIcon icon= new ImageIcon(url);
        final JLabel labelBackground = new JLabel();
        labelBackground.setIcon(icon);
        // 设置label的大小
        labelBackground.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        // 将背景图片标签放入桌面面板的最底层
        jFrame.getLayeredPane().add(labelBackground,Integer.valueOf(Integer.MIN_VALUE));
        // 将容器转换为面板设置为透明
        JPanel panel = (JPanel)c;
        panel.setOpaque(false);
    }
}
