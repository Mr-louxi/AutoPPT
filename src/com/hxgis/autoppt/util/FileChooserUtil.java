package com.hxgis.autoppt.util;

import java.io.File;
//import java.io.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**

*/
public class FileChooserUtil extends JFileChooser {

	JFileChooser jfc = new JFileChooser();

	public String openWin() {
		jfc.setAcceptAllFileFilterUsed(false);// 设置文件过滤条件，在文件选择中没有“所有文件”的选项
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// 设置文件选择类型，在这里只是选择具体文件
		/*jfc.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith(".ppt")) {
					return f.getName().toLowerCase().endsWith(".ppt");// 添加过滤文件类型。以后缀做判断
				} else if (f.getName().toLowerCase().endsWith(".ppt")) {
					return f.getName().toLowerCase().endsWith(".ppt");
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "PPT Template File";// 在文件类型中的显示
			}
		});*/
		jfc.showOpenDialog(null);
		File xls = jfc.getSelectedFile();

		if (xls == null) {
			return "";
		}
		String resultOpen = jfc.getSelectedFile().getPath();// 获取文件路径
		return resultOpen;
	}
}
