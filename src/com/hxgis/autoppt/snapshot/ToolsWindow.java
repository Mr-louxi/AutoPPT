package com.hxgis.autoppt.snapshot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class ToolsWindow extends JFrame {
	private ScreenWindow parent;
	private JPanel contentPane;
	
	public ToolsWindow(ScreenWindow parent, int x, int y) {
		this.parent = parent;
		this.init();
		this.setLocation(x, y);
		this.pack();
		
	}

	private void init() {
		this.setUndecorated(true);	//去掉窗口装饰
		this.setAlwaysOnTop(true);	//设置总是出现在最前面
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	//窗口最大化
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton replace = new JButton("图片替换");
		toolBar.add(replace);
		
		JSeparator ss = new JSeparator();
		toolBar.add(ss);
		
		JButton button = new JButton("单幅插入");
		toolBar.add(button);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		
		JButton button_1 = new JButton("二副插入");
		toolBar.add(button_1);
		
		JSeparator separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
		JButton button_2 = new JButton("三幅插入");
		toolBar.add(button_2);
		
		JSeparator separator_2 = new JSeparator();
		toolBar.add(separator_2);
		
		JButton button_3 = new JButton("四幅插入");
		toolBar.add(button_3);
		//图片替换
		replace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean flag = true;
					Common.getAutoPPT().insert(flag);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally{
					ScreenTip.showTip();
					ToolsWindow.this.dispose(); //销毁工具窗体
					parent.dispose();  //销毁截图窗体
				}
			}
		});
		//单幅导入
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Common.click1Num == 0){
						Common.flag = true;
					}else{
						Common.flag = false;
					}
					if(!Common.isSelectInsert){
						Common.getAutoPPT().insertPic(1,Common.click1Num);
					}else{
						Common.getAutoPPT().insertPic(1,Common.click1Num);
						int srcSlideNumber = Common.getSlideShow().getSlides().size();
						Common.getAutoPPT().moveTo(Common.getSlideShow(),srcSlideNumber,Integer.parseInt(Common.pageNum));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally{
					ScreenTip.showTip();
					ToolsWindow.this.dispose(); //销毁工具窗体
					parent.dispose();  //销毁截图窗体
				}
			}
		});
		//二幅插入
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!Common.isSelectInsert){
						Common.get2Num();
						Common.getAutoPPT().insertPic(2,Common.click2Num-1);
					}else{
						Common.get2Num();
						Common.getAutoPPT().insertPic(2,Common.click2Num-1);
						int srcSlideNumber = Common.getSlideShow().getSlides().size();
						if(Common.click2Num==2){
							Common.getAutoPPT().moveTo(Common.getSlideShow(),srcSlideNumber,Integer.parseInt(Common.pageNum));
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally{
					ScreenTip.showTip();
					ToolsWindow.this.dispose(); //销毁工具窗体
					parent.dispose();  //销毁截图窗体
				}
			}
		});
		// 三幅插入
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!Common.isSelectInsert){
						Common.get3Num();
						Common.getAutoPPT().insertPic(3,Common.click3Num-1);
					}else{
						Common.get3Num();
						Common.getAutoPPT().insertPic(3,Common.click3Num-1);
						int srcSlideNumber = Common.getSlideShow().getSlides().size();
						if(Common.click3Num==3){
							Common.getAutoPPT().moveTo(Common.getSlideShow(),srcSlideNumber,Integer.parseInt(Common.pageNum));
						}
					}
					/*String label = "{pic3_"+Common.click3Num+"}";
					Common.getAutoPPT().insert(3,Common.click3Num-1,label);*/
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					ScreenTip.showTip();
					ToolsWindow.this.dispose(); // 销毁工具窗体
					parent.dispose(); // 销毁截图窗体
				}
			}
		});
		// 四幅插入
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!Common.isSelectInsert){
						Common.get4Num();
						Common.getAutoPPT().insertPic(4,Common.click4Num-1);
					}else{
						Common.get4Num();
						Common.getAutoPPT().insertPic(4,Common.click4Num-1);
						int srcSlideNumber = Common.getSlideShow().getSlides().size();
						if(Common.click4Num == 4){
							Common.getAutoPPT().moveTo(Common.getSlideShow(),srcSlideNumber,Integer.parseInt(Common.pageNum));
						}
					}
					/*String label = "{pic4_"+Common.click4Num+"}";
					Common.getAutoPPT().insert(4,Common.click4Num-1,label);*/
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					ScreenTip.showTip();
					ToolsWindow.this.dispose(); // 销毁工具窗体
					parent.dispose(); // 销毁截图窗体
				}
			}
		});
	}
}
