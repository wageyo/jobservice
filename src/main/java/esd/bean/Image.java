package esd.bean;

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
	private Integer nid; // 文章id
	private String mark; // 备注

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

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
