package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Filegags;
import esd.controller.Constants;
import esd.dao.FilegagsDao;

/**
 * 图片操作类
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-12-30
 */
@Service
public class FilegagsService {

	@Autowired
	private FilegagsDao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param image
	 * @return 返回保存对象的id
	 */
	public String save(Filegags t) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		t.setId(uuid);
		return dao.save(t) ? uuid : null;
	}

	// 删除一个对象
		public boolean deleteId(String id) {
			return dao.delete(id);
		}

	// 更新一个对象
	public boolean update(Filegags image) {
		image.setUpdateCheck(dao.getUpdateCheck(image.getId()));
		return dao.update(image);
	}

	/**
	 *  按id查询一个对象,不带二进制的文件哦
	 * @param id
	 * @return
	 */
	public Filegags getById(String id) {
		return (Filegags) dao.getById(id);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<Filegags> getByPage(Filegags image, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filegags", image);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 * 分页查询方法--得到总数,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public Integer getTotalCount(Filegags image) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", image);
		return dao.getTotalCount(map);
	}

	/**
	 * 根据id获得单一二进制文件
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getFileById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		HashMap<String, Object> resultMap = dao.getFilegagsById(id);
		byte[] image = (byte[]) resultMap.get("file");
		return image;
	}
	
	/**
	 * 根据id整个文件gags对象, 带文件的哦 .
	 * 
	 * @param id
	 * @return
	 */
	public Filegags getImageByIdWithFile(String id) {
		Filegags resultMap = dao.getFilegagsByIdWithFile(id);
		return resultMap;
	}
	

}
