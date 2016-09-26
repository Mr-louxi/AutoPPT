package com.hxgis.autoppt.snapshot;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.*;

/**
 * 自定义字体选择对话框---Java中没有直接提供字体选择对话框
 * @author pengranxiang
 */
public class PFontChooser extends JPanel implements ActionListener,ListSelectionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog jd;//用于显示模态的窗体
    private JComboBox fonts;//用于选择字体的下拉框
    private JList face,size;//用于选择字形和字号的列表
    private JTextField sizeJT;//用于显示选中的字形和字号
    private JButton ok,cancel;//表示选中和取消的按钮
    private Font current;//表示当然选中的字体
    private GraphicsEnvironment ge;//表示当前的图形环境
    private JLabel demo;//表示预览的label
    private String fontName="宋体";
    private int fontStyle=Font.PLAIN,fontSize=20;
    private Frame owner;//表示父类的组件窗体
    private Hashtable<String,Integer> ht;//名字到大小的映射
    /** Creates a new instance of JFontChooser */
    private PFontChooser() {
        initOther();
    }
    private void initOther(){
        current=new Font(fontName,fontStyle,fontSize);
        ht=new Hashtable<String,Integer>();
        sizeJT=new JTextField("20");
        sizeJT.setEditable(false);
        sizeJT.setBounds(260,40,50,20);
        ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] family=ge.getAvailableFontFamilyNames();
        fonts=new JComboBox(family);
        fonts.setEditable(false);
        fonts.setMaximumRowCount(5);
        demo=new JLabel("字体选择  ABCD abcd",JLabel.CENTER);
        demo.setFont(current);
        String[] faceString={"正常","粗体","斜体","粗体+斜体"};
        String[] sizeString={"初号","小初","一号","小一","二号","小二",
        "三号","小三","四号","小四","五号","小五","六号","小六","七号",
        "八号","5","8","9","10","11","12","14","16","18","20","22","24",
        "26","28","36","48","72"};
        int[] sizeValue={42,36,26,24,22,18,16,15,14,12,11,9,7,6,5,4,5,8,9,10,11,12,14,16,18,20,
        22,24,26,28,36,48,72};
        for(int i=0;i<sizeString.length;i++){
            ht.put(sizeString[i],sizeValue[i]);
        }
        face=new JList(faceString);
        size=new JList(sizeString);
        face.setSelectedIndex(0);
        size.setSelectedIndex(25);
        fonts.setSelectedItem("宋体");
        face.setVisibleRowCount(4);
        size.setVisibleRowCount(4);
        ok=new JButton("确定");
        cancel=new JButton("取消");
        ok.addActionListener(this);
        cancel.addActionListener(this);
        fonts.addActionListener(this);
        face.addListSelectionListener(this);
        size.addListSelectionListener(this);
    }
    private void initWindow(String title){
        this.setLayout(new BorderLayout());
        JLabel fontLabel=new JLabel("字体");
        JLabel faceLabel=new JLabel("字形");
        JLabel sizeLabel=new JLabel("字号");
        fontLabel.setForeground(Color.RED);
        faceLabel.setForeground(Color.RED);
        sizeLabel.setForeground(Color.RED);
        fontLabel.setBounds(20,20,100,20);
        faceLabel.setBounds(180,20,80,20);
        sizeLabel.setBounds(260,20,50,20);
        fonts.setBounds(10,40,127,21);
        JScrollPane faceScroll=new JScrollPane(face);
        JScrollPane sizeScroll=new JScrollPane(size);
        faceScroll.setBounds(180,40,65,100);
        sizeScroll.setBounds(260,60,50,80);
        JPanel up=new JPanel(null);
        JPanel center=new JPanel(new BorderLayout());
        JPanel bottom=new JPanel();
        up.setPreferredSize(new Dimension(345,160));
        up.add(fontLabel);
        up.add(faceLabel);
        up.add(sizeLabel);
        up.add(fonts);
        up.add(faceScroll);
        up.add(sizeScroll);
        up.add(sizeJT);
        up.setBorder(BorderFactory.createTitledBorder("选择区"));
        center.add(demo,BorderLayout.CENTER);
        center.setBorder(BorderFactory.createTitledBorder("预览区"));
        bottom.add(ok);
        bottom.add(cancel);
        this.add(up,BorderLayout.NORTH);
        this.add(center,BorderLayout.CENTER);
        this.add(bottom,BorderLayout.SOUTH);
        jd=new JDialog(owner,title,true);
        jd.getContentPane().add(this,BorderLayout.CENTER);
        jd.setSize(360,360);
        jd.setResizable(false);
        jd.setLocationRelativeTo(owner);
        jd.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                current=null;
                jd.dispose();
            }
        });
        jd.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==fonts){
            fontName=(String)fonts.getSelectedItem();
            current=new Font(fontName,fontStyle,fontSize);
            demo.setFont(current);
            this.repaint();
        }else if(ae.getSource()==ok){
            jd.dispose();
        }else if(ae.getSource()==cancel){
            current=null;
            jd.dispose();
        }
    }
    public void valueChanged(ListSelectionEvent le){
        if(le.getSource()==face){
            String value=(String)face.getSelectedValue();
            if(value.equals("正常")){
                fontStyle=Font.PLAIN;
            }else if(value.equals("粗体")){
                fontStyle=Font.BOLD;
            }else if(value.equals("斜体")){
                fontStyle=Font.ITALIC;
            }else if(value.equals("粗体+斜体")){
                fontStyle=Font.ITALIC|Font.BOLD;
            }
            current=new Font(fontName,fontStyle,fontSize);
            demo.setFont(current);
            this.repaint();
        }else if(le.getSource()==size){
            String sizeName=(String)size.getSelectedValue();
            sizeJT.setText(sizeName);
            fontSize=ht.get(sizeName);
            current=new Font(fontName,fontStyle,fontSize);
            demo.setFont(current);
            this.repaint();
        }
    }
    public static Font showDialog(Frame owner,String title){
        PFontChooser jf=new PFontChooser();
        jf.initWindow(title);
        return jf.current;
    }
   
    public static void main(String[] args) {
    	final JFrame frame = new JFrame("test");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JButton button = new JButton("字体选择框");
    	button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PFontChooser.showDialog(frame, "字体选择");
			}
    		
    	});
    	frame.getContentPane().add(button);
    	frame.pack();
    	frame.setVisible(true);
    }
}