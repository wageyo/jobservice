package esd.bean;

import java.util.Arrays;

import org.springframework.stereotype.Component;

/**
 * 文件pojo类
 * 
 * @author Administrator
 * 
 */
@Component
public class Filegags extends PrimaryKeyString {

	private byte[] file;// 图片
	private String fileName; // 图片文件名
	private String fileTitle; // 图片标题
	private String fileSuffix; // 文件后缀名或类型
	private String defaultWidth; // 图片原始宽度
	private String defaultHeight; // 图片原始高度
	private String newWidth; // 图片设定的新宽度
	private String newHeight; // 图片设定的新高度
	private String mark; // 备注

	@Override
	public String toString() {
		return "Filegags [file=" + Arrays.toString(file) + ", fileName="
				+ fileName + ", fileTitle=" + fileTitle + ", defaultWidth="
				+ defaultWidth + ", defaultHeight=" + defaultHeight
				+ ", newWidth=" + newWidth + ", newHeight=" + newHeight
				+ ", mark=" + mark + "]";
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(String defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public String getDefaultHeight() {
		return defaultHeight;
	}

	public void setDefaultHeight(String defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public String getNewWidth() {
		return newWidth;
	}

	public void setNewWidth(String newWidth) {
		this.newWidth = newWidth;
	}

	public String getNewHeight() {
		return newHeight;
	}

	public void setNewHeight(String newHeight) {
		this.newHeight = newHeight;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
