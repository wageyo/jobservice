package esd.dao;

import java.util.HashMap;

import esd.bean.Image;

public interface ImageDao extends IDao<Image> {

	/**
	 * 根据id, 获得图片
	 * @param id
	 * @return
	 */
	HashMap<String,Object> getImageById(String id);
	
	/**
	 * 根据新闻id, 获得图片
	 * @param id
	 * @return
	 */
	HashMap<String,Object> getImageByNid(Integer nid);
}
