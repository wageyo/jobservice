package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Image;
import esd.controller.Constants;
import esd.dao.ImageDao;

/**
 * 图片操作类
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-12-30
 */
@Service
public class ImageService {

	@Autowired
	private ImageDao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param image
	 * @return 返回保存对象的id
	 */
	public String save(Image image) {
		String uuid = UUID.randomUUID().toString();
		image.setId(uuid);
		return dao.save(image) ? uuid : null;
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}
	
	// 删除一个对象
		public boolean deleteId(String id) {
			return dao.delete(id);
		}

	// 更新一个对象
	public boolean update(Image image) {
		image.setUpdateCheck(dao.getUpdateCheck(image.getId()));
		return dao.update(image);
	}

	// 按id查询一个对象
	public Image getById(int id) {
		return (Image) dao.getById(id);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<Image> getByPage(Image image, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", image);
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
	public Integer getTotalCount(Image image) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", image);
		return dao.getTotalCount(map);
	}

	/**
	 * 根据id获得单一图片
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getImageById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		HashMap<String, Object> resultMap = dao.getImageById(id);
		byte[] image = (byte[]) resultMap.get("image");
		return image;
	}

	/**
	 * 根据文章id获得对应图片
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getImageByNid(Integer nid) {
		if (nid == null || "".equals(nid)) {
			return null;
		}
		HashMap<String, Object> resultMap = dao.getImageByNid(nid);
		byte[] image = (byte[]) resultMap.get("image");
		return image;
	}
}
