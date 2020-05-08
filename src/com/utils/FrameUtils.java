package com.utils;

import com.controller.MouseAction;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/4/29 23:31
 */
public class FrameUtils {
    public static void bigFrame(JFrame jFrame) {
        jFrame.setResizable(false);
        jFrame.setSize(800,520);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置Frame居中显示
        jFrame.setLocationRelativeTo(null);
        MouseAction ma = new MouseAction(jFrame);
        jFrame.addMouseListener(ma);
        jFrame.addMouseMotionListener(ma);
        jFrame.setVisible(true);
    }
    public static void middleFrame(JFrame jFrame){
        jFrame.setSize(600, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        MouseAction ma = new MouseAction(jFrame);
        jFrame.addMouseListener(ma);
        jFrame.addMouseMotionListener(ma);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
    public static void smallFrame(JFrame frame){
        frame.setLayout(null);
        frame.setSize(350,200);
        frame.setLocationRelativeTo(null);
        MouseAction ma = new MouseAction(frame);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
