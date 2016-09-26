package com.hxgis.autoppt.snapshot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.hxgis.autoppt.dao.ReplacePPT;
import com.hxgis.autoppt.view.ExInsertFrm;
import com.hxgis.autoppt.view.HelpFrm;
import com.hxgis.autoppt.view.SettingFrm;
import com.jacob.com.Dispatch;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * 屏幕截图小程序
 * @author Maroon
 *
 */
public class SnapShoot extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton snapButton;
	private JLabel imageLabel;
	
	private int x, y, xEnd, yEnd;	//记录鼠标坐标
	
	private boolean isDoodle;		//涂鸦开关
	private boolean isLine;			//画线开关
	private boolean isCircle;		//画圆开关
	
	private BufferedImage bi;		//用于双缓冲
	
	private ButtonGroup buttonGroup;	//管理Radio开关
	private JRadioButton doodleButton;
	private JRadioButton lineButton;
	private JRadioButton circleButton;
	
	private JButton saveButton;
	private JFileChooser chooser;
	
	private JButton textWaterMarkButton;
	private JButton imageWaterMarkButton;
	
	private JButton clipBoardButton;
	private Image logo;
	
	public SnapShoot() {
		initUI();
		initLayout();
		createAction();
		
		addSystemTray();
		
		
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(logo);
		this.setSize(750, 500);
		this.setTitle("SnapShoot");
		this.setFocusable(true);
		this.setLocationRelativeTo(null);	//居中
//		this.setVisible(true);
	}
	
	private void initUI() {
		try {
			logo = ImageIO.read(SnapShoot.class.getResourceAsStream("logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		snapButton = new JButton("开始截图（点右键退出）");
		imageLabel = new JLabel();
		
		buttonGroup = new ButtonGroup();
		doodleButton = new JRadioButton("涂鸦");
		lineButton = new JRadioButton("画线");
		circleButton = new JRadioButton("画圆");
		
		buttonGroup.add(doodleButton);
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		
		saveButton = new JButton("保存到文件");
		chooser = new JFileChooser();
		//默认选中文件
		//得到当前用户桌面目录可以使用：FileSystemView.getFileSystemView().getHomeDirectory() 
		File selectedFile = new File(FileSystemView.getFileSystemView().getHomeDirectory(), "save.png");
		//设置默认选中文件
		chooser.setSelectedFile(selectedFile);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		chooser.setFileFilter(filter);
		
		textWaterMarkButton = new JButton("文字水印");
		imageWaterMarkButton = new JButton("图像水印");
		clipBoardButton = new JButton("保存到剪贴板");
	}
	
	private void initLayout() {
		JPanel pane = new JPanel();
		pane.add(imageLabel);
		JScrollPane imgScrollPane = new JScrollPane(pane);
		
		Container container = this.getContentPane();
		GroupLayout layout = new GroupLayout(container);
		container.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
		hGroup
			.addGroup(layout.createSequentialGroup().addComponent(snapButton).addComponent(doodleButton).addComponent(lineButton).addComponent(circleButton).addComponent(textWaterMarkButton).addComponent(imageWaterMarkButton).addComponent(clipBoardButton).addComponent(saveButton))
			.addComponent(imgScrollPane);
		layout.setHorizontalGroup(hGroup);
	
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(snapButton).addComponent(doodleButton).addComponent(lineButton).addComponent(circleButton).addComponent(textWaterMarkButton).addComponent(imageWaterMarkButton).addComponent(clipBoardButton).addComponent(saveButton))
			.addComponent(imgScrollPane);
		layout.setVerticalGroup(vGroup);
	}
	
	/**
	 * 加入系统托盘
	 */
	private void addSystemTray() {
		//修改窗口关闭和最小化事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				SnapShoot.this.setVisible(false);
			}
			
			public void windowIconified(WindowEvent e) {
				SnapShoot.this.setVisible(false);
			}
		});
		
		if (SystemTray.isSupported()) {
			
			SystemTray tray = SystemTray.getSystemTray();
			
			// 为这个托盘加一个弹出菜单
			final PopupMenu popup = new PopupMenu();
			//MenuItem item = new MenuItem("open  ctrl + shift + o");//图片编辑菜单
			MenuItem help = new MenuItem("Help");
			MenuItem setting = new MenuItem("Setting");
			MenuItem insert = new MenuItem("Insert");
			MenuItem save = new MenuItem("Save");
			MenuItem exit = new MenuItem("Exit");
			//popup.add(item);
			popup.add(help);
			popup.add(setting);
			popup.add(insert);
			popup.add(save);
			popup.add(exit);
			
			/*item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SnapShoot.this.setVisible(true);
				}
			});*/
			help.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HelpFrm helpFrm = new HelpFrm();
					helpFrm.setLocationRelativeTo(null);
					helpFrm.setVisible(true);
				}
			});
			
			setting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SettingFrm settingFrm = new SettingFrm();
					settingFrm.setLocationRelativeTo(null);
					settingFrm.setVisible(true);
				}
			});
			
			insert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ExInsertFrm insertFrm = new ExInsertFrm();
					insertFrm.setLocationRelativeTo(null);
					insertFrm.setVisible(true);
				}
			});
			
			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ReplacePPT.replace(Common.outputPath,Common.author,Common.time);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "PPT已生成！"); 
					Common.autoPPT = null;
					Common.slideShow = null;
					Common.slide = null;
				}
			});
			
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//清除系统热键
					JIntellitype.getInstance().cleanUp();
					//if (Common.ppt != null) { Dispatch.call(Common.ppt, "Close"); }
					//if (Common.app != null) { Common.app.invoke("Quit"); }
					System.exit(1);
				}
			});
			
			// 为这个托盘加一个提示信息
			Image scaleLogo = ((BufferedImage)logo).getScaledInstance(16, 16, Image.SCALE_FAST);
			TrayIcon trayIcon = new TrayIcon(scaleLogo, "PPT自动生成软件: AutoPPT\n作者：Maroon", popup);
			
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("无法向这个托盘添加新项： " + e);
			}
		} else {
			System.err.println("无法使用系统托盘!");
		}
	}
	
	private void printScreen() {
		try {
//			//隐藏主窗口
//			if(SnapShoot.this.isVisible()) {
//				SnapShoot.this.setVisible(false);
//			}
//			//防止ScreenWindow生成太快，在主窗口未隐藏前就截图
//			Thread.sleep(10);
			//开启模拟屏幕，将显示截图的目标组件传入
			
			new ScreenWindow(imageLabel);
			
			//显示主窗口，因为 Screen 显示在最前面，所以在 ScreenWindow 退出之前 看不到主窗口
//			SnapShoot.this.setVisible(true);
		} catch (AWTException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	private void save() {
		if(imageLabel.getIcon() == null) {
			JOptionPane.showMessageDialog(SnapShoot.this, "没有图片信息，请先截图", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		File file = null;
		int returnVal = chooser.showOpenDialog(SnapShoot.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       file = chooser.getSelectedFile();
	    }

	    //取得imageLabel中的图像
	    Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
	    
		try {
			if(file != null)  {
				ImageIO.write((BufferedImage)img, "png", file);
				JOptionPane.showMessageDialog(SnapShoot.this, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void addSystemHotKey() {
		JIntellitype.getInstance();  
		
		//检查是否已经有该程序在运行中
		if(JIntellitype.checkInstanceAlreadyRunning("SnapShoot")) {
			System.err.println("An instance of this application is already running");
			System.exit(1);
		}
		
		//注册系统热键为：ctrl + shift + p 截图，ctrl + shift + s 保存，ctrl + shift + o 打开主界面(为了不干扰其他程序的打印和保存快捷键)
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, (int)'P');
		//JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, (int)'S');
		//JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, (int)'O');
		
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int identifier) {
				if(identifier == 1) {
					printScreen();
				} else if(identifier == 2) {
					save();
				} else if(identifier == 3) {
					SnapShoot.this.setVisible(true);
				}
			}
		});
	}
	
	private void createAction() {
		addSystemHotKey();
		
//		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//            public void eventDispatched(AWTEvent ae) {
//                if (ae instanceof KeyEvent) {
//                    KeyEvent e = (KeyEvent) ae;            
//                    if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
//                    	printScreen();
//                    }
//                }
//            }
//        }, AWTEvent.KEY_EVENT_MASK);
		
		snapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printScreen();
			}
		});
		
		doodleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(doodleButton.isSelected()) {
					isDoodle = true;
					isLine = false;
					isCircle = false;
				}
			}
		});
		
		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lineButton.isSelected()) {
					isDoodle = false;
					isLine = true;
					isCircle = false;
				}
			}
		});
		
		circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(circleButton.isSelected()) {
					isDoodle = false;
					isLine = false;
					isCircle = true;
				}
			}
		});
		
		textWaterMarkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imageLabel.getIcon() == null) {
					JOptionPane.showMessageDialog(SnapShoot.this, "没有图片信息，请先截图", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//水印参数设置对话框
				WaterMarkBean bean = WaterMarkDialog.showDialog(SnapShoot.this, false);
				if(bean == null) {	//取消设置
					return;
				}
				
				//取得imageLabel中的图像
				Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
				
				//添加文字水印
				bi = GraphicsUtils.addImageWaterMark(img, bean.getText(), bean.getFont(), bean.getColor(), bean.getX(), bean.getY());
				
				//将加好水印的图像，设置到imageLabel
				imageLabel.setIcon(new ImageIcon(bi));
			}
		});
		
		imageWaterMarkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imageLabel.getIcon() == null) {
					JOptionPane.showMessageDialog(SnapShoot.this, "没有图片信息，请先截图", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				WaterMarkBean bean = WaterMarkDialog.showDialog(SnapShoot.this, true);
				if(bean == null || bean.getImage() == null) {	//取消设置或没选图片
					return;
				}
				
				//取得imageLabel中的图像
				Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
				
				//添加图像水印
				bi = GraphicsUtils.addImageWaterMark(img, bean.getImage(), bean.getX(), bean.getY());
				
				//将加好水印的图像，设置到imageLabel
				imageLabel.setIcon(new ImageIcon(bi));
				
			}
		});
		
		clipBoardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imageLabel.getIcon() == null) {
					JOptionPane.showMessageDialog(SnapShoot.this, "没有图片信息，请先截图", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
				//保存到剪贴板
				GraphicsUtils.setClipboardImage(img);
				JOptionPane.showMessageDialog(SnapShoot.this, "已经保存到系统剪贴板", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		imageLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//鼠标按下的点，作为画线的最初的起点
				x = e.getX();
				y = e.getY();
			}
			
			public void mouseReleased(MouseEvent e) {
				if(isLine || isCircle) {
					//该方法只用作画线或画圆时处理
					//鼠标弹起时需要将最后定为的图像 bi，调用imageLabel.setIcon()方法，设置进去。
					//这样就可以将线段或圆真的画进去了。为了使用变量 bi 将其转为 该类的private变量
					imageLabel.setIcon(new ImageIcon(bi));
				}
			}
		});
		
		imageLabel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				
				if(isDoodle) {	//涂鸦开关
					xEnd = e.getX();
					yEnd = e.getY();
					
					//鼠标移动时，在imageLabel展示的图像中，绘制点
					//1. 取得imageLabel中的图像
					Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
					
					//2. 创建一个缓冲图形对象 bi
					bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = (Graphics2D) bi.getGraphics();
					
					//3. 将截图的原始图像画到 bi
					g2d.drawImage(img, 0, 0, null);
					
					//4. 在鼠标所在的点，画一个点
					g2d.setColor(Color.RED);	//设置画笔颜色为红色
					g2d.drawLine(x, y, xEnd, yEnd);	//Java中没有提供点的绘制，使用起点和终点为同一个点的画线代替
					
					g2d.dispose();
					
					//5. 为了保留每一个点，不能直接使用imageLabel.getGraphics()来画，
					//需要使用imageLabel.setIcon()来直接将画了点的图像，设置到imageLabel中，
					//这样，在第一步中，取得img时，就为已经划过上一个点的图像了。
					imageLabel.setIcon(new ImageIcon(bi));
					
					//下次画线起点是设置为这次画线的终点
					x = xEnd;
					y = yEnd;
				}
				
				if(isLine || isCircle) {	//画线，画圆开关，两个很像，放一起了
					xEnd = e.getX();
					yEnd = e.getY();
					
					//鼠标移动时，在imageLabel展示的图像中，绘制点
					//1. 取得imageLabel中的图像
					Image img = ((ImageIcon)imageLabel.getIcon()).getImage();
					
					//2. 创建一个缓冲图形对象 bi
					bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = (Graphics2D) bi.getGraphics();
					
					//3. 将截图的原始图像画到 bi
					g2d.drawImage(img, 0, 0, null);
					
					//4. 在鼠标所在的点，画一个点
					g2d.setColor(Color.RED);	//设置画笔颜色为红色
					
					if(isLine) {
						g2d.drawLine(x, y, xEnd, yEnd);	//Java中没有提供点的绘制，使用起点和终点为同一个点的画线代替
					}
					if(isCircle) {
						//因为如果鼠标向上，或向左移动时，xEnd > x, yEnd > y ，所以画圆的起点要取两者中的较小的，
						//而宽度和高度是不能  < 0 的，所以取绝对值
						g2d.drawOval(Math.min(x, xEnd), Math.min(y, yEnd), Math.abs(xEnd - x), Math.abs(yEnd - y));
					}
					
					g2d.dispose();
					
					//5. 不需要保留在鼠标拖动过程中所画的线段，所以直接使用imageLabel.getGraphics()绘制
					//这样imageLabel.getIcon()并没有被改变，所以每次都只到原始截图和多了一条线，即为最后效果的演示
					Graphics g = imageLabel.getGraphics();
					g.drawImage(bi, 0, 0, null);	//将处理后的图片，画到imageLabel
					g.dispose();
				}
			}
		
			public void mouseMoved(MouseEvent e) {
				
			}
		});
	}
    
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		new SnapShoot();
	}
}