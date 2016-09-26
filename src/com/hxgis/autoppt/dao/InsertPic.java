package com.hxgis.autoppt.dao;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.sl.usermodel.PictureData;

import com.hxgis.autoppt.snapshot.Common;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class InsertPic {
	public static Map<Integer, List<Rectangle>> standard = new HashMap<Integer, List<Rectangle>>();
	public FileOutputStream out;
	public InsertPic() {
		// 1屏截图
		List<Rectangle> l1 = new ArrayList<Rectangle>();
		Rectangle r1 = new Rectangle(168, 57, 398, 348);
		l1.add(r1);
		standard.put(1, l1);

		// 2屏截图
		List<Rectangle> l2 = new ArrayList<Rectangle>();
		Rectangle r2 = new Rectangle(30, 16, 338, 296);
		Rectangle r3 = new Rectangle(321, 156, 338, 296);
		l2.add(r2);
		l2.add(r3);
		standard.put(2, l2);
		
		// 3屏截图
		List<Rectangle> l3 = new ArrayList<Rectangle>();
		Rectangle r4 = new Rectangle(0, 0, 283, 202);
		Rectangle r5 = new Rectangle(211, 124, 283, 202);
		Rectangle r6 = new Rectangle(416, 266, 283, 202);
		l3.add(r4);
		l3.add(r5);
		l3.add(r6);
		standard.put(3, l3);
		
		// 4屏截图
		List<Rectangle> l4 = new ArrayList<Rectangle>();
		Rectangle r7 = new Rectangle(12, -2, 315, 225);
		Rectangle r8 = new Rectangle(388, -1, 315, 225);
		Rectangle r9 = new Rectangle(12, 238, 315, 225);
		Rectangle r10 = new Rectangle(388, 238, 315, 225);
		l4.add(r7);
		l4.add(r8);
		l4.add(r9);
		l4.add(r10);
		standard.put(4, l4);
		
		Common.app = new ActiveXComponent("PowerPoint.Application");// 创建一个PPT对象
		Common.app.setProperty("Visible", new Variant(true));
		
	}

	/**
	 * 获取Rectangle
	 * 
	 * @return
	 */

	public Rectangle getRectangle(int m, int k) {
		Rectangle rect = new Rectangle();
		List<Rectangle> rectList = standard.get(m);
		rect = rectList.get(k);
		return rect;
	}

	/**
	 * 插入外部图片到PPT模板,并创建新的Slide
	 * 
	 * @param args
	 * @throws Exception
	 */
	public void insertPic(int i,int index) throws Exception {
		if(Common.isClose){
			Dispatch.call(Common.ppt, "Close");
		}
		if (Common.flag) {
			// add a new slide
			Common.slide = Common.getSlideShow().createSlide();
		}
		// add a new picture to this slideshow
		HSLFPictureData pd = Common.getSlideShow().addPicture(
				new File(Common.picDir, "temp.png"),
				PictureData.PictureType.PNG);
		HSLFPictureShape pictNew = new HSLFPictureShape(pd);

		// set image position in the slide
		pictNew.setAnchor(new java.awt.Rectangle(getRectangle(i, index)));
		Common.slide.addShape(pictNew);
		// save changes in a file
		FileOutputStream out = new FileOutputStream(new File(Common.outputPath,
				"ReplaceAllPPT.ppt"));
		Common.getSlideShow().write(out);
		out.close();
		String filePath = Common.outputPath + "\\" + "ReplaceAllPPT.ppt";
		jacobPptUtil(filePath);
		Common.isClose = true;
	}
	
	public void moveTo(HSLFSlideShow slideShow, int srcSlideNumber, int targetSlideNumber) throws Exception {
		int stepNumber = srcSlideNumber - targetSlideNumber;// 需要几次对调
		for (int step = 0; step < stepNumber; step++) {
			/**
			 * 举例7-->4 
			 * 步骤: 
			 * 7<->6
			 * 6<->5 
			 * 5<->4
			 */
			slideShow.reorderSlide(srcSlideNumber - step, srcSlideNumber - 1 - step);
		}
		if(Common.isClose){
			Dispatch.call(Common.ppt, "Close");
		}
		FileOutputStream out = new FileOutputStream(new File(
				Common.outputPath, "ReplaceAllPPT.ppt"));
		Common.getSlideShow().write(out);
		out.close();
		String filePath = Common.outputPath + "\\" + "ReplaceAllPPT.ppt";
		jacobPptUtil(filePath);
		Common.isClose = true;
	}


	/**
	 * 处理图片插入操作
	 * @param slide
	 * @param i
	 * @param index
	 * @throws IOException
	 */
	public void processPic(HSLFSlide slide,String picType) throws IOException{
		HSLFPictureData pd = Common.getSlideShow().addPicture(new File(Common.picDir,"temp.png"),
				PictureData.PictureType.PNG);
		HSLFPictureShape pictNew = new HSLFPictureShape(pd);
		if(picType.equals("{pic}")){
			pictNew.setAnchor(new java.awt.Rectangle(285,64,405,405));
		}else if(picType.equals("{pic1}")){
			pictNew.setAnchor(new java.awt.Rectangle(342,85,398,398));
		}else if(picType.equals("{pic2}")){
			pictNew.setAnchor(new java.awt.Rectangle(0,100,374,368));
		}
		slide.addShape(pictNew);
	}
	/**
	 * 图片替换  图片标签
	 * @param i
	 * @param index
	 * @param picLabel
	 * @throws Exception 
	 */
	public void insert(boolean flag) throws Exception{
		if (Common.isClose) {
			Dispatch.call(Common.ppt, "Close");
		}
		for (HSLFSlide slide : Common.getSlideShow().getSlides()) {
			for (HSLFShape sh : slide.getShapes()) {
				if (flag) {
					if (sh instanceof HSLFTextBox) {
						HSLFTextBox shape = (HSLFTextBox) sh;
						if (shape.getText().contains("{pic}")) {
							processPic(slide, "{pic}");
							slide.removeShape(shape);
							flag = false;
						} else if (shape.getText().contains("{pic1}")) {
							processPic(slide, "{pic1}");
							slide.removeShape(shape);
							flag = false;
						} else if (shape.getText().contains("{pic2}")) {
							processPic(slide, "{pic2}");
							slide.removeShape(shape);
							flag = false;
						}
					}
				}
			}
		}
		FileOutputStream out = new FileOutputStream(new File(Common.outputPath,"ReplaceAllPPT.ppt"));
		Common.getSlideShow().write(out);
	    out.close();
	    String filePath = Common.outputPath+"\\"+"ReplaceAllPPT.ppt";
	    jacobPptUtil(filePath);
	    Common.isClose = true;
	}
	/**
	 * 打开PPT
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void  jacobPptUtil(String filePath) throws Exception {
		try {
				//ComThread.InitSTA();
				//Common.app = new ActiveXComponent("PowerPoint.Application");// 创建一个PPT对象
				//Common.app.setProperty("Visible", new Variant(true)); //
				// 不可见打开（PPT转换不运行隐藏，所以这里要注释掉）
				// app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
				System.out.println("打开文档 >>> " + filePath);
				Dispatch ppts = Common.app.getProperty("Presentations").toDispatch();
				Common.ppt = Dispatch.call(ppts, "Open", filePath, false,// ReadOnly
						true,// Untitled指定文件是否有标题
						true// WithWindow指定文件是否可见
						).toDispatch();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("========Error:打开文档失败：" + e.getMessage());
		} finally {
			//if (Common.ppt != null) { Dispatch.call(Common.ppt, "Close"); }
			//if (app != null) { app.invoke("Quit"); }
			//ComThread.Release();
			//ComThread.quitMainSTA();
		}
	}

	public static void main(String[] args) throws Exception {
		InsertPic a = new InsertPic();
		// 1. 抓取图片
		// scatchPic();
		// 2. 插入到ppt,分为单张插入，多张插入
		/*a.insertPic(1,0);
		a.insertPic(2,0);
		a.insertPic(2,1);
		a.insertPic(3,0);
		a.insertPic(3,1);
		a.insertPic(3,2);*/
		//insertPic(2);
		// 3. 打开ppt
		//a.jacobPptUtil(outpath);
	}
}
