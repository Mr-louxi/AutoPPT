package com.hxgis.autoppt.snapshot;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

public class Test {
	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(400, 100);
//		frame.setLocationRelativeTo(null);
//		frame.setResizable(false);
//		
//		JLabel label = new JLabel("The PrintScreenKey Code: ");
//		JLabel codeLabel = new JLabel(KeyEvent.VK_PRINTSCREEN + "");
//		
//		JLabel label2 = new JLabel("You Pressed Key Code: ");
//		final JLabel codeLabel2 = new JLabel();
//		
//		frame.setLayout(new GridLayout(2, 2));
//		frame.add(label);
//		frame.add(codeLabel);
//		frame.add(label2);
//		frame.add(codeLabel2);
//		
//		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//            public void eventDispatched(AWTEvent ae) {
//                if (ae instanceof KeyEvent) {
//                    KeyEvent e = (KeyEvent) ae;  
//                    if(e.getKeyCode() != 0)  codeLabel2.setText(e.getKeyCode() + "");
//                    System.out.println(e.getKeyCode());
//                }
//            }
//        }, AWTEvent.KEY_EVENT_MASK);
//		
//		frame.setVisible(true);
		
		//得到当前用户的桌面目录
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		
		System.out.println(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + File.separator + "save.png");
	}
}
