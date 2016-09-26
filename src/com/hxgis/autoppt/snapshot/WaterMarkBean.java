package com.hxgis.autoppt.snapshot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

/**
 * 用于水印参数的 JavaBean
 * @author pengranxiang
 */
public class WaterMarkBean {
	private Image image;	//水印图像
	private String text;	//水印文字
	private Font font;		//文字字体
	private Color color;	//文字颜色
	private int x,y;		//水印位置
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
