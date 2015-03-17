package esd.bean;

import java.util.Arrays;

import org.springframework.stereotype.Component;

/**
 * 图片pojo类
 * 
 * @author Administrator
 * 
 */
@Component
public class Image extends PrimaryKey_String {

	private byte[] image;// 图片
	private String imageName; // 图片文件名
	private String imageTitle; // 图片标题
	private String defaultWidth; // 图片原始宽度
	private String defaultHeight; // 图片原始高度
	private String newWidth; // 图片设定的新宽度
	private String newHeight; // 图片设定的新高度
	private String mark; // 备注

	@Override
	public String toString() {
		return "Image [image=" + Arrays.toString(image) + ", imageName="
				+ imageName + ", imageTitle=" + imageTitle + ", defaultWidth="
				+ defaultWidth + ", defaultHeight=" + defaultHeight
				+ ", newWidth=" + newWidth + ", newHeight=" + newHeight
				+ ", mark=" + mark + "]";
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
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
