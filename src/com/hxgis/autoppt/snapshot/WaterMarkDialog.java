package com.hxgis.autoppt.snapshot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 水印设置对话框
 * @author pengranxiang
 */
public class WaterMarkDialog extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WaterMarkBean result = new WaterMarkBean();
	private boolean isImageType;	//是否为图像水印，否则为文字水印
	
	private JLabel imagePathLabel;
	private JLabel imageShowLabel;
	private JButton selectImageButton;
	
	private JLabel textLabel;
	private JTextField textField;
	
	private JButton selectFontButton;
	private JLabel fontLabel;
	
	private JButton selectColorButton;
	private JLabel colorLabel;
	
	private JLabel xLabel;
	private JTextField xTextField;
	private JLabel yLabel;
	private JTextField yTextField;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private JDialog dialog;
	private JFrame parent;
	
	public WaterMarkDialog(JFrame parent, boolean isImageType) {
		this.parent = parent;
		this.isImageType = isImageType;
		result.setText("文字水印");
		result.setFont(new Font("宋体", Font.BOLD, 30));
		result.setColor(Color.RED);
		result.setX(100);
		result.setY(100);
		
		initUI();
		initLayout();
		createAction();
		dialog.setVisible(true);
	}
	
	private void initUI() {
		imagePathLabel = new JLabel("水印图");
		imageShowLabel = new JLabel("缩略图");
		selectImageButton = new JButton("图片");
		
		textLabel = new JLabel("文字");
		textField = new JTextField("水印文字");
		
		selectFontButton = new JButton("宋体");
		fontLabel = new JLabel("字体");
		
		selectColorButton = new JButton("");
		selectColorButton.setBackground(Color.RED);
		selectColorButton.setMaximumSize(new Dimension(30, 30));
		selectColorButton.setPreferredSize(new Dimension(30, 18));
		colorLabel = new JLabel("颜色");
		
		xLabel = new JLabel("X坐标");
		xTextField = new JTextField("100");
		
		yLabel = new JLabel("Y坐标");
		yTextField = new JTextField("100");
		
		okButton = new JButton("确定");
		cancelButton = new JButton("取消");
		
		dialog = new JDialog(parent, "水印参数设置", true);
		
		dialog.getContentPane().add(this, BorderLayout.CENTER);
		dialog.setSize(250,300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dialog.dispose();
                result = null;
            }
        });
        
        lockByType();
	}
	
	private void lockByType() {
		if(isImageType) {
			textField.setEditable(false);
			selectFontButton.setEnabled(false);
			selectColorButton.setEnabled(false);
		} else {
			selectImageButton.setEnabled(false);
		}
	}
	
	private void initLayout() {
		JPanel panel = new JPanel();
		panel.add(okButton);
		panel.add(cancelButton);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
		hGroup
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(imagePathLabel).addComponent(textLabel).addComponent(fontLabel).addComponent(colorLabel).addComponent(xLabel).addComponent(yLabel))
				.addGroup(layout.createParallelGroup().addComponent(selectImageButton).addComponent(textField).addComponent(selectFontButton).addComponent(selectColorButton).addComponent(xTextField).addComponent(yTextField))
				.addGroup(layout.createParallelGroup().addComponent(imageShowLabel))
			)
			.addComponent(panel);
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(imagePathLabel).addComponent(selectImageButton).addComponent(imageShowLabel))
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(textLabel).addComponent(textField))
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(fontLabel).addComponent(selectFontButton))
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(colorLabel).addComponent(selectColorButton))
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(xLabel).addComponent(xTextField))
			.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(yLabel).addComponent(yTextField))
			.addComponent(panel);
		layout.setVerticalGroup(vGroup);
	}
	
	private void createAction() {
		selectFontButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Font font = PFontChooser.showDialog(parent, "水印字体选择");
				if(font != null) {
					result.setFont(font);
					selectFontButton.setText(font.getName());
				}
			}
		});
		
		selectColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(parent, "文字颜色选择", Color.RED);
				result.setColor(color);
				selectColorButton.setBackground(color);
			}
		});
		
		selectImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG,PNG,GIF Images", "jpg", "png", "gif");
				chooser.setFileFilter(filter);

				File file = null;
				int returnVal = chooser.showOpenDialog(parent);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       file = chooser.getSelectedFile();
			    }
			    
			    if(file != null) {
			    	BufferedImage bi = null;
			    	try {
						bi = ImageIO.read(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					result.setImage(bi);
					//设置缩略图：30 * 30
					imageShowLabel.setText("");
					imageShowLabel.setIcon(new ImageIcon(bi.getScaledInstance(30, 30, Image.SCALE_FAST)));
			    }
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setText(textField.getText());
				result.setX(Integer.valueOf(xTextField.getText()));
				result.setY(Integer.valueOf(yTextField.getText()));
				dialog.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				result = null;
			}
		});
	}
	
	public static WaterMarkBean showDialog(JFrame parent, boolean isImageType) {
		WaterMarkDialog dialog = new WaterMarkDialog(parent, isImageType);
		return dialog.result;
	}
	
	public static void main(String[] args) {
    	WaterMarkDialog.showDialog(null, true);
    }
}
