package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.SmsAccount;
import esd.controller.Constants;
import esd.dao.SmsAccountDao;

/**
 * 各地区名商通账号 service类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-4
 */
@Service
public class SmsAccountService {

	@Autowired
	private SmsAccountDao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param t
	 * @return
	 */
	public Boolean save(SmsAccount t) {
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
	public Boolean update(SmsAccount t) {
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
	public SmsAccount getById(String id) {
		return dao.getById(id);
	}

	/**
	 * 按acode查询一个对象
	 * 
	 * @param acode
	 * @return
	 */
	public SmsAccount getByArea(String acode) {
		return dao.getByArea(acode);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<SmsAccount> getByPage(SmsAccount smsFix, Integer startPage,
			Integer size) {
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
	public int getTotalCount(SmsAccount smsFix) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsFix", smsFix);
		return dao.getTotalCount(map);
	}

}
