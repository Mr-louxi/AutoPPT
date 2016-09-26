package com.hxgis.autoppt.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.hxgis.autoppt.snapshot.Common;
import com.hxgis.autoppt.util.FileChooserUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExInsertFrm extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExInsertFrm frame = new ExInsertFrm();
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
	public ExInsertFrm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 315, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_1 = new JLabel("插入页码：");
		label_1.setBounds(27, 37, 74, 15);
		contentPane.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(113, 34, 146, 21);
		contentPane.add(textField_1);
		
		JButton button_1 = new JButton("确定");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Common.pageNum = textField_1.getText();
				Common.isSelectInsert = true;
				JOptionPane.showMessageDialog(null, "请按快捷键进行截图");
				setVisible(false);
			}
		});
		button_1.setBounds(27, 102, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("取消");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
			}
		});
		button_2.setBounds(166, 102, 93, 23);
		contentPane.add(button_2);
	}
}
