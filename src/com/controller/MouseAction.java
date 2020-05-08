package com.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author zzx
 * 2020/4/29 21:07
 */
public class MouseAction extends MouseAdapter implements MouseMotionListener {
    private Point point;
    private JFrame frame;

    public MouseAction(JFrame frame) {
        this.point = new Point();
        this.frame = frame;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        point.x = e.getX();
        point.y = e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        Point loc = frame.getLocation();
        frame.setLocation(loc.x+e.getX()-point.x,loc.y+e.getY()-point.y);

    }
}
