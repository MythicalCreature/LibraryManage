package com.view;

import com.utils.BgImageUtils;
import com.utils.FrameUtils;
import com.utils.MenuUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/4/29 21:20
 */
class MainFrame {
    MainFrame(){
        JFrame jFrame = new JFrame("图书管理后台");
        Container c = jFrame.getContentPane();
        new BgImageUtils(jFrame,c,"mainFrame.jpg");
        new MenuUtils(jFrame);
        FrameUtils.bigFrame(jFrame);
    }
}
