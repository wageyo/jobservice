package esd.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 文章表
 * 
 * @author Administrator
 * 
 */
@Component
public class News extends PrimaryKeyInt {

	private String title; // 标题
	private String content; // 内容
	private String author; // 作者
	private String source; // 来源/来路
	private String type; // 文章类型
	private Area area; // 所属地区
	private Date updateDate; // 岗位有效日期截至
	private Date releaseDate;
	private String imageId; // 图片id

	@Override
	public String toString() {
		return "News [title=" + title + ", author=" + author + ", source="
				+ source + ", type=" + type + ", area=" + area + "]";
	}

	public News() {
	}

	public News(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
