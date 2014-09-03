package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 文章表
 * 
 * @author Administrator
 * 
 */
@Component
public class News extends PrimaryKey {

	private String title; // 标题
	private String content; // 内容
	private String author; // 作者
	private String source; // 来源/来路
	private String type; // 文章类型
	private Area area; // 所属地区

	@Override
	public String toString() {
		return "News [title=" + title + ", author="
				+ author + ", source=" + source + ", type=" + type + ", area="
				+ area + "]";
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

}
