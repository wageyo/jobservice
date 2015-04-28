package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.Articles;

/**
 * 消息Dao接口
 * 
 * @author Administrator
 * 
 */
public interface ArticlesDao extends IDao<Articles> {

	//取得不带内容的新闻列表
	public List<Articles> getTitleList(Map<String, Object> map);
	
	/**
	 * 取得5条带图片的最新信息
	 * @param map
	 * @return
	 */
	public List<Articles> getFiveWithImage(Map<String, Object> map);
	
	/**
	 * 根据acode  获得对应地区的“联系我们”
	 * @param acode
	 * @return
	 */
	public Articles getContact(String acode);
}
