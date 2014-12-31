package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.News;

/**
 * 消息Dao接口
 * 
 * @author Administrator
 * 
 */
public interface NewsDao extends IDao<News> {

	//取得不带内容的新闻列表
	public List<News> getTitleList(Map<String, Object> map);
	
	//取得5条带图片的最新信息
	public List<News> getFiveWithImage(Map<String, Object> map);
	
	
}
