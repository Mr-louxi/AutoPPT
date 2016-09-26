package com.hxgis.autoppt.dao;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;

import com.hxgis.autoppt.snapshot.Common;
import com.jacob.com.Dispatch;

public class ReplacePPT {
	public static HSLFSlideShow ppt = Common.getSlideShow();
	public static boolean flag = true;
	public static String period_xun = null;
	/**
	 * 替换文本
	 * 
	 * @param templatePath
	 * @param outputPath
	 * @param author
	 * @param time
	 * @throws Exception
	 */
	public static void replace(String outputPath,String author, String time) throws Exception {
		if(Common.isClose){
			Dispatch.call(Common.ppt, "Close");
		}
		for (HSLFSlide slide : ppt.getSlides()) {
			for (HSLFShape sh : slide.getShapes()) {
				if (sh instanceof HSLFTextBox) {
					HSLFTextBox shape = (HSLFTextBox) sh;
					if (shape.getText().contains("{author}")) {
						// 替换作者
						HSLFTextBox txt = new HSLFTextBox();
						txt.setText(shape.getText().replace("{author}", author));
						txt.setAnchor(shape.getAnchor());

						// use RichTextRun to work with the text format
						HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
						tp.setAlignment(TextAlign.CENTER);
						HSLFTextRun rt = tp.getTextRuns().get(0);
						rt.setFontSize(24.);
						rt.setFontFamily("华文行楷");
						rt.setBold(false);
						rt.setItalic(false);
						rt.setUnderlined(false);
						rt.setFontColor(Color.BLUE);

						slide.removeShape(shape); // 删除之前的文本框
						slide.addShape(txt); // 添加新的文本框
					} else if (shape.getText().contains("{time}")) {
						// 替换时间
						HSLFTextBox txt = new HSLFTextBox();
						txt.setText(shape.getText().replace("{time}", time));
						txt.setAnchor(shape.getAnchor());

						// use RichTextRun to work with the text format
						HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
						tp.setAlignment(TextAlign.CENTER);
						HSLFTextRun rt = tp.getTextRuns().get(0);
						rt.setFontSize(24.);
						rt.setFontFamily("华文行楷");
						rt.setBold(false);
						rt.setItalic(false);
						rt.setUnderlined(false);
						rt.setFontColor(Color.BLUE);

						slide.removeShape(shape); // 删除之前的文本框
						slide.addShape(txt); // 添加新的文本框
					} else if (shape.getText().contains("{period}")) {
						// 替换时间间隔
						HSLFTextBox txt = new HSLFTextBox();
						Date date = new SimpleDateFormat("yyyy年MM月dd日")
								.parse(time);
						SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						int day = c.get(Calendar.DATE);
						c.set(Calendar.DATE, day - 7); // 获取前7天日期
						txt.setText(shape.getText().replace(
								"{period}",
								sdf.format(c.getTime()) + "~"
										+ sdf.format(date)));
						txt.setAnchor(shape.getAnchor());
						// use RichTextRun to work with the text format
						HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
						tp.setAlignment(TextAlign.LEFT);
						HSLFTextRun rt = tp.getTextRuns().get(0);
						rt.setFontSize(28.);
						rt.setFontFamily("华文新魏");
						rt.setBold(false);
						rt.setItalic(false);
						rt.setUnderlined(false);
						rt.setFontColor(Color.BLUE);

						slide.removeShape(shape); // 删除之前的文本框
						slide.addShape(txt); // 添加新的文本框
						
					} else if (shape.getText().contains("{period_future}")) {
						// 替换时间间隔
						HSLFTextBox txt = new HSLFTextBox();
						Calendar c = Calendar.getInstance();
						Date date = new SimpleDateFormat("yyyy年MM月dd日")
								.parse(time);
						c.setTime(date);
						int day = c.get(Calendar.DATE);
						c.set(Calendar.DATE, day + 7); // 获取后7天日期
						SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
						txt.setText(shape.getText().replace(
								"{period_future}",
								sdf.format(date) + "~"
										+ sdf.format(c.getTime())));
						txt.setAnchor(shape.getAnchor());

						HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
						tp.setAlignment(TextAlign.LEFT);
						HSLFTextRun rt = tp.getTextRuns().get(0);
						rt.setFontSize(32.);
						rt.setFontFamily("华文新魏");
						rt.setBold(false);
						rt.setItalic(false);
						rt.setUnderlined(false);
						rt.setFontColor(Color.BLACK);

						slide.removeShape(shape); // 删除之前的文本框
						slide.addShape(txt); // 添加新的文本框
					} else if(shape.getText().contains("{title_xun}")){
						String s = null;
						HSLFTextBox txt = new HSLFTextBox();
						Calendar c = Calendar.getInstance();
						Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(time);
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年");
						String year = sdf1.format(date);
						SimpleDateFormat sdf2 = new SimpleDateFormat("MM月");
						String month = sdf2.format(date);
						c.setTime(date);
						int day = c.get(Calendar.DATE);  // 本月的某一天
						if(day>=1&&day<=10){
							s = "中旬";
							period_xun = month+"11日"+"-"+month+"20日";
						}else if(day>10&&day<=20){
							s= "下旬";
							period_xun = month+"21日"+"-"+month+"30日";
						}else{
							s = "上旬";
							period_xun = month+"01日"+"-"+month+"10日";
						}
						
						SimpleDateFormat sdf = new SimpleDateFormat("MM月");
						// flag为true，则处理第一页标签,处理完成后置为false;若flag为false,则处理后面几页标签.
						if(flag){
							txt.setText(shape.getText().replace("{title_xun}",year+sdf.format(date) + s));
							txt.setAnchor(shape.getAnchor());
							
							HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
							tp.setAlignment(TextAlign.CENTER);
							HSLFTextRun rt = tp.getTextRuns().get(0);
							rt.setFontSize(54.);
							rt.setFontFamily("隶书");
							rt.setBold(true);
							rt.setItalic(false);
							rt.setUnderlined(false);
							rt.setFontColor(Color.RED);
							slide.removeShape(shape); // 删除之前的文本框
							slide.addShape(txt); // 添加新的文本框
							flag = false;
						}else{
							txt.setText(shape.getText().replace("{title_xun}",sdf.format(date) + s));
							txt.setAnchor(shape.getAnchor());
							
							HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
							tp.setAlignment(TextAlign.CENTER);
							HSLFTextRun rt = tp.getTextRuns().get(0);
							rt.setFontSize(40.);
							rt.setFontFamily("隶书");
							rt.setBold(true);
							rt.setItalic(false);
							rt.setUnderlined(false);
							rt.setFontColor(Color.BLUE);
							slide.removeShape(shape); // 删除之前的文本框
							slide.addShape(txt); // 添加新的文本框
							}
						} else if(shape.getText().contains("{page_xun}")){
							String s = null;
							HSLFTextBox txt = new HSLFTextBox();
							Calendar c = Calendar.getInstance();
							Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(time);
							c.setTime(date);
							int day = c.get(Calendar.DATE);  // 本月的某一天
							if(day>=1&&day<=10){
								s = "中旬";
							}else if(day>10&&day<=20){
								s= "下旬";
							}else{
								s = "上旬";
							}
							SimpleDateFormat sdf = new SimpleDateFormat("MM月");
							txt.setText(shape.getText().replace("{page_xun}",sdf.format(date) + s));
							txt.setAnchor(shape.getAnchor());
							
							HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
							tp.setAlignment(TextAlign.LEFT);
							HSLFTextRun rt = tp.getTextRuns().get(0);
							rt.setFontSize(18.);
							rt.setFontFamily(rt.getFontFamily());
							rt.setBold(true);
							rt.setItalic(false);
							rt.setUnderlined(false);
							rt.setFontColor(Color.RED);
							slide.removeShape(shape); // 删除之前的文本框
							slide.addShape(txt); // 添加新的文本框
						}else if(shape.getText().contains("{period_xun}")){
							HSLFTextBox txt = new HSLFTextBox();
							txt.setText(shape.getText().replace(
									"{period_xun}",period_xun));
							txt.setAnchor(shape.getAnchor());
							// use RichTextRun to work with the text format
							HSLFTextParagraph tp = txt.getTextParagraphs().get(0);
							tp.setAlignment(TextAlign.CENTER);
							HSLFTextRun rt = tp.getTextRuns().get(0);
							rt.setFontSize(40.);
							rt.setFontFamily("隶书");
							rt.setBold(true);
							rt.setItalic(false);
							rt.setUnderlined(false);
							rt.setFontColor(Color.BLUE);
							slide.removeShape(shape); // 删除之前的文本框
							slide.addShape(txt); // 添加新的文本框
						}
					}
				}
		}
		
		//插入最后一页PPT
		Common.slide = ppt.createSlide();
		HSLFPictureData pd = ppt.addPicture(new File(Common.picDir,"last.png"),
				PictureData.PictureType.PNG);
		HSLFPictureShape pictNew = new HSLFPictureShape(pd);
		pictNew.setAnchor(new java.awt.Rectangle(174,120,432,240));
		Common.slide.addShape(pictNew);
		FileOutputStream out = new FileOutputStream(new File(outputPath,"ReplaceAllPPT.ppt"));
		ppt.write(out);
		out.close();
		String filePath = Common.outputPath+"\\"+"ReplaceAllPPT.ppt";
		InsertPic.jacobPptUtil(filePath);
		Common.isClose = true;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * try {
		 * replaceText(Common.templatePath,Common.outputPath,"周璇","2015年10月19日"
		 * ); } catch (IOException | ParseException e) { e.printStackTrace(); }
		 */
		try {
			replace("C:\\Users\\Maroon720\\Desktop\\ppt_auto\\newPPT.ppt",
					"周璇", "2015年10月19日");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
