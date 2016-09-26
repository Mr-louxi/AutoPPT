package com.hxgis.autoppt.snapshot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicsUtils {
	//public final static String picpath = "C:\\Users\\Maroon720\\Desktop\\ppt_auto\\pic\\temp.png";
	//public final static String picpath = "C:\\Users\\hx168\\Desktop\\ppt_auto\\pic\\20160218.png";
	/**
	 * 截图屏幕中制定区域的图片
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return 被截部分的BufferedImage对象
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static BufferedImage getScreenImage(int x, int y, int w, int h) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		w = w > 0 ? w : 1;
		h = h > 0 ? h : 1;
		BufferedImage screen = robot.createScreenCapture(new Rectangle(x, y, w, h));
		return screen;
	}
    
    /**
     * 给图片添加文字水印
     * @param targetImage 需要加上水印的图片
     * @param text 用做水印的文字
     * @param font 水印文字的字体
     * @param color 水印文字的颜色
     * @param x
     * @param y
     * @return 加上水印后的BufferedImage对象
     */
    public static BufferedImage addImageWaterMark(Image targetImage, String text, Font font, Color color, int x, int y) {
    	int width = targetImage.getWidth(null);
    	int height = targetImage.getHeight(null);
    	
    	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics g = bi.getGraphics();
    	g.drawImage(targetImage, 0, 0, null);
    	g.setFont(font);
    	g.setColor(color);
    	g.drawString(text, x, y);
    	g.dispose();
    	
    	return bi;
    }
    
    /**
     * 给图片添加图片水印
     * @param markImage 用做水印的图片
     * @param targetImage 需要加上水印的图片 
     * @param x 
     * @param y 
     * @return 加上水印后的BufferedImage对象
     */  
    public static BufferedImage addImageWaterMark(Image targetImage, Image markImage, int x, int y) {  
        int wideth = targetImage.getWidth(null);  
        int height = targetImage.getHeight(null);  
        
        BufferedImage  bi = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);  
        Graphics g = bi.createGraphics();  
        g.drawImage(targetImage, 0, 0, null);  
        g.drawImage(markImage, x, y, null);              
        g.dispose();  
       
        return bi; 
    }
    
    /**
     * 将指定图片写入系统剪贴板
     * @param image
     */
    public static void setClipboardImage(final Image image) {
    	Transferable trans = new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
	}
    
    public static void saveLocalImg(final Image image){
    	File file = new File(Common.picDir+File.separator+"temp.png");
    	try {
			ImageIO.write((BufferedImage)image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}