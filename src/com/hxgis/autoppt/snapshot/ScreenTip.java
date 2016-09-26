package com.hxgis.autoppt.snapshot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

/**
 * 屏幕提示文字工具类
 * @author pengranxiang
 */
public class ScreenTip {
	//以对话框形式出现，防止在系统任务栏出现一个窗口最小化时的图标
	private static JDialog frame = new JDialog();
	
	static {
		frame.setAlwaysOnTop(true);	//设置对话框为 顶层窗口
        frame.setUndecorated(true);	//取消对话口的窗口框
        frame.setSize(300, 100);	
        frame.setLocationRelativeTo(null);	//居中
        AWTUtilities.setWindowOpaque(frame, false);	// 设置对话框为透明。 此方法需要 JDK6 以上

        //显示文字用的容器
        final JPanel pane = new JPanel() {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//在容器中绘制提示文字
			protected void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		
        		g.setColor(Color.RED);
        		g.setFont(new Font("宋体", Font.BOLD, 20));
        		g.drawString("贴图已插入PPT", 10, 50);
        		
        	}
        };
        
        //设置容器最适应大小
        pane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        //设置容器为透明
        pane.setOpaque(false);

        frame.setLayout(new BorderLayout());
        frame.add(pane);
        
        
        
	}

	public static void showTip() {
		Thread thread;
		
		thread = new Thread(new Runnable() {
        	public void run() {
        		frame.setVisible(true);
        		try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		frame.setVisible(false);
        	}
        });
		thread.start();
	}
	
	public static void main(String[] args) {
		ScreenTip.showTip();
	}
}
