package com.hxgis.autoppt.snapshot;

import java.io.IOException;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;

import com.hxgis.autoppt.dao.InsertPic;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class Common {
	
	/**********************
	 * 鼠标点击次数统计
	 */
	public static int click1Num = 0;//全局标识
	public static int click2Num = 0;//全局标识
	public static int click3Num = 0;//全局标识
	public static int click4Num = 0;//全局标识
	/**********************
	 * 图片插入操作全局对象
	 */
	public static InsertPic autoPPT = null;   //传入插入图片的对象;
	/**********************
	 * PPT操作对象
	 */
	public static HSLFSlideShow slideShow = null;
	public static HSLFSlide slide = null;
	public static boolean flag = true;//标识是否创建新的slider
	
	public static ActiveXComponent app = null; //com组件对象
	public static Dispatch ppt = null;
	public static Boolean isClose = false;
	
	/*********************
	 * 设置窗体参数配置
	 */
	//public static String templatePath = "C:\\Users\\Maroon720\\Desktop\\ppt_auto\\template.ppt";
	public static String templatePath = null;
	public static String outputPath = null;
	public static String picDir = null;
	public static String author = null;
	public static String time = null;
	
	/**
	 * 图片选择参数
	 */
	public static String comboBoxVaule = null;
	
	/**指定页码插入图片
	 * 
	 */
	public static String pageNum = null;
	
	public static boolean isSelectInsert = false; 
	public static void get2Num(){
		
		if(click2Num > 1){
			click2Num = 0;
		}
		
		if(click2Num == 0){
			flag = true;
		}else{
			flag = false;
		}
		
		click2Num++;
	}
	
	public static void get3Num(){
		if(click3Num > 2){
			click3Num = 0;
		}
		
		if(click3Num == 0){
			flag = true;
		}else{
			flag = false;
		}
		
		click3Num++;
		
	}
	
	public static void get4Num(){
		if(click4Num > 3){
			click4Num = 0;
		}
		
		if(click4Num == 0){
			flag = true;
		}else{
			flag = false;
		}
		
		click4Num++;
		
	}
	
	public static HSLFSlideShow getSlideShow(){
		if(slideShow != null){
			return slideShow;
		}else{
			try {
				slideShow = new HSLFSlideShow(new HSLFSlideShowImpl(templatePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return slideShow;
	}
	
	public static InsertPic getAutoPPT(){
		
		if(autoPPT != null){
			return autoPPT;
		}else{
			autoPPT = new InsertPic(); 
			return autoPPT;
		}
	}
}
