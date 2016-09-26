package com.hxgis.autoppt.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.hxgis.autoppt.snapshot.Common;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpFrm extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpFrm frame = new HelpFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HelpFrm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//dispose();
			}
		});
		setTitle("帮助");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 381, 232);
		getContentPane().setLayout(null);
		
		JEditorPane dtrpnsettingpptpptsave = new JEditorPane();
		dtrpnsettingpptpptsave.setFont(new Font("宋体", Font.PLAIN, 12));
		dtrpnsettingpptpptsave.setText("按钮说明\r\n1.Setting按钮\r\n设置PPT的相关参数；\r\n2.Save按钮\r\n保存PPT到设置的位置；\r\n3.Exit按钮\r\n退出系统");
		dtrpnsettingpptpptsave.setBounds(43, 33, 151, 112);
		getContentPane().add(dtrpnsettingpptpptsave);
		
		JEditorPane dtrpnppt = new JEditorPane();
		dtrpnppt.setText("操作流程\r\n1.设置PPT相关参数\r\n2.按需要截取图片\r\n3.保存PPT");
		dtrpnppt.setToolTipText("");
		dtrpnppt.setEditable(false);
		dtrpnppt.setBounds(204, 33, 151, 112);
		getContentPane().add(dtrpnppt);
	}
}
