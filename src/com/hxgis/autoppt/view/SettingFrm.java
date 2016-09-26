package com.hxgis.autoppt.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.AutumnSkin;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceSaharaLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

import com.hxgis.autoppt.snapshot.Common;
import com.hxgis.autoppt.util.FileChooserUtil;
import com.mkk.swing.JCalendarChooser;

public class SettingFrm extends JFrame {
	
	public JTextField pptTemplatePath;
	private JLabel outputLable;
	private JTextField outputPath;
	private JButton outputButton;
	private JTextField authorTextField;
	private JTextField timeTextField;
	public JTextField picDirTextField;
	private JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingFrm frame = new SettingFrm();
					frame.setVisible(true);
					JFrame.setDefaultLookAndFeelDecorated(true);
			        JDialog.setDefaultLookAndFeelDecorated(true);
			        UIManager.setLookAndFeel(UIManager
		                    .getCrossPlatformLookAndFeelClassName());
		            UIManager
		                    .setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
		            
		            SubstanceSaharaLookAndFeel.setSkin(new AutumnSkin());  
		            SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());
		            //SubstanceLookAndFeel.setCurrentTitlePainter(new Glass3DDecorationPainter());  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingFrm() {
		setResizable(true);
		setTitle("PPT参数设置");
		setBounds(100, 100, 486, 425);
		this.setLocation(200, 50);
		getContentPane().setLayout(null);
		
		JLabel templateLabel = new JLabel("导入模板：");
		templateLabel.setBounds(29, 30, 74, 15);
		getContentPane().add(templateLabel);
		
		pptTemplatePath = new JTextField();
		pptTemplatePath.setEditable(false);
		pptTemplatePath.setBounds(124, 27, 202, 21);
		getContentPane().add(pptTemplatePath);
		pptTemplatePath.setColumns(10);
		
		JButton selectFileButton = new JButton("选择模板文件");
		
		selectFileButton.setBounds(348, 26, 105, 23);
		getContentPane().add(selectFileButton);
		
		outputLable = new JLabel("存放路径：");
		outputLable.setBounds(29, 274, 78, 15);
		getContentPane().add(outputLable);
		
		outputPath = new JTextField();
		outputPath.setColumns(10);
		outputPath.setBounds(120, 273, 206, 21);
		getContentPane().add(outputPath);
		
		outputButton = new JButton("选择文件目录");
		
		outputButton.setBounds(346, 271, 99, 23);
		getContentPane().add(outputButton);
		
		JLabel authorLabel = new JLabel("作    者：");
		authorLabel.setBounds(29, 129, 70, 15);
		getContentPane().add(authorLabel);
		
		authorTextField = new JTextField();
		authorTextField.setText("周璇");
		authorTextField.setColumns(10);
		authorTextField.setBounds(124, 126, 202, 21);
		getContentPane().add(authorTextField);
		
		JLabel timeLabel = new JLabel("制作时间：");
		timeLabel.setBounds(29, 177, 80, 15);
		getContentPane().add(timeLabel);
		
		timeTextField = new JTextField();
		timeTextField.setEditable(false);
		timeTextField.setColumns(10);
		timeTextField.setBounds(124, 174, 202, 21);
		getContentPane().add(timeTextField);
		
		JButton selectTimeButton = new JButton("选择时间");
		selectTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		selectTimeButton.setBounds(348, 173, 102, 23);
		getContentPane().add(selectTimeButton);
		
		JLabel lblT = new JLabel("图片目录：");
		lblT.setBounds(29, 70, 106, 15);
		getContentPane().add(lblT);
		
		picDirTextField = new JTextField();
		picDirTextField.setEditable(false);
		picDirTextField.setColumns(10);
		picDirTextField.setBounds(124, 67, 202, 21);
		getContentPane().add(picDirTextField);
		
		JButton picDirButton = new JButton("选择目录");
		
		picDirButton.setBounds(349, 66, 104, 23);
		getContentPane().add(picDirButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u66FF\u6362\u6587\u672C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(19, 101, 436, 133);
		getContentPane().add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u751F\u6210PPT", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(18, 242, 435, 71);
		getContentPane().add(panel_2);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Common.templatePath = pptTemplatePath.getText();
				Common.picDir = picDirTextField.getText();
				Common.outputPath = outputPath.getText();
				Common.author = authorTextField.getText();
				Common.time = timeTextField.getText();
				JOptionPane.showMessageDialog(null, "请按快捷键进行截图");
				setVisible(false);
			}
		});
		btnNewButton.setBounds(78, 340, 93, 23);
		getContentPane().add(btnNewButton);
		
		JButton button = new JButton("取消");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pptTemplatePath.setText("");
				picDirTextField.setText("");
				outputPath.setText("");
				timeTextField.setText("");
			}
		});
		button.setBounds(233, 340, 93, 23);
		getContentPane().add(button);
		//选择模板文件
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooserUtil fileChooser = new FileChooserUtil();
				String path = fileChooser.openWin();
				pptTemplatePath.setText(path);
			}
		});
		//选择时间
		selectTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				JCalendarChooser jcalendarchooser = new JCalendarChooser(null);
				Calendar c=jcalendarchooser.showCalendarDialog();
				timeTextField.setText(sdf.format(c.getTime()));
			}
		});
		//选择PPT输出路径
		outputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();  
                jfc.setDialogTitle("请选择要导出目录");  
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = jfc.showOpenDialog(SettingFrm.this);  
                File file = null;  
                if(JFileChooser.APPROVE_OPTION == result) {  
                    file = jfc.getSelectedFile();  
                    if(!file.isDirectory()) {  
                        JOptionPane.showMessageDialog(null, "你选择的目录不存在");  
                        return ;  
                    }  
                    String path = file.getAbsolutePath();  
                    outputPath.setText(path);  
                } else {  
                    return ;  
                }  
			}
		});
		
		//设置截图临时目录
		picDirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();  
                jfc.setDialogTitle("请选择要导出目录");  
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = jfc.showOpenDialog(SettingFrm.this);  
                File file = null;  
                if(JFileChooser.APPROVE_OPTION == result) {  
                    file = jfc.getSelectedFile();  
                    if(!file.isDirectory()) {  
                        JOptionPane.showMessageDialog(null, "你选择的目录不存在");  
                        return ;  
                    }  
                    String path = file.getAbsolutePath();  
                    picDirTextField.setText(path);  
                } else {  
                    return ;  
                }  
			}
		});
	}
}
