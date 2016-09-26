package com.hxgis.autoppt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextBox;

import com.hxgis.autoppt.snapshot.Common;

public class TetsShape {

	public static void main(String[] args) throws IOException {
		HSLFSlideShow slideShow = new HSLFSlideShow(new HSLFSlideShowImpl("C:\\Users\\Maroon720\\Desktop\\newPPT.ppt"));
		for (HSLFSlide slide : slideShow.getSlides()) {
			for (HSLFShape sh : slide.getShapes()) {
					System.out.print("x:"+sh.getAnchor().x+"y:"+sh.getAnchor().y+"weight:"+
							sh.getAnchor().width+"height:"+sh.getAnchor().height+"\n");
				}
			}
		
		/*int srcSlideNumber = slideShow.getSlides().size();
		int targetSlideNumber = 2;
		int stepNumber = srcSlideNumber - targetSlideNumber + 1;// 需要几次对调
		 
		for (int step = 0; step < stepNumber; step++) {
			*//**
			 * 举例7-->4 
			 * 步骤: 
			 * 7<->6
			 * 6<->5 
			 * 5<->4
			 *//*
			slideShow.reorderSlide(srcSlideNumber - step, srcSlideNumber - 1 - step);
		}
		
		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Maroon720\\Desktop\\ppt_auto\\"+"ReplaceAllPPT.ppt"));
		slideShow.write(out);
		out.close();*/
	}

}
