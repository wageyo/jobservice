package esd.dao;

import java.util.HashMap;

import esd.bean.Filegags;

public interface FilegagsDao extends IDao<Filegags> {

	/**
	 * 根据id, 获得文件
	 * @param id
	 * @return
	 */
	HashMap<String,Object> getFilegagsById(String id);
	
	/**
	 * 根据id获得整个文件对象, 带图片的哦.
	 * @param id
	 * @return
	 */
	Filegags getFilegagsByIdWithFile(String id);
}
