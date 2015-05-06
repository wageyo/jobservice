package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.SmsFix;
import esd.controller.Constants;
import esd.dao.SmsFixDao;

/**
 * 推送就业信息前后缀 自定义表 service类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-4
 */
@Service
public class SmsFixService {

	@Autowired
	private SmsFixDao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param t
	 * @return
	 */
	public Boolean save(SmsFix t) {
		if (t == null) {
			return Boolean.FALSE;
		}
		t.setId(KitService.getUUID());
		return dao.save(t);
	}

	/**
	 * 删除一个对象
	 * 
	 * @param id
	 * @return
	 */
	public Boolean delete(String id) {
		return dao.delete(id);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param t
	 * @return
	 */
	public Boolean update(SmsFix t) {
		if (t == null) {
			return Boolean.FALSE;
		}
		return dao.update(t);
	}

	/**
	 * 按id查询一个对象
	 * 
	 * @param id
	 * @return
	 */
	public SmsFix getById(String id) {
		return dao.getById(id);
	}
	
	/**
	 * 按acode查询一个对象
	 * 
	 * @param id
	 * @return
	 */
	public SmsFix getByArea(String acode) {
		return dao.getByArea(acode);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<SmsFix> getByPage(SmsFix smsFix, Integer startPage, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsFix", smsFix);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 * 获得数据总条数
	 * 
	 * @param smsFix
	 * @return
	 */
	public int getTotalCount(SmsFix smsFix) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsFix", smsFix);
		return dao.getTotalCount(map);
	}

}
