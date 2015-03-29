package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.SmsPhone;
import esd.controller.Constants;
import esd.dao.SmsPhoneDao;

/**
 * 电话簿
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-22
 * @param <T>
 */
@Service
public class SmsPhoneService {

	@Autowired
	private SmsPhoneDao dao;

	/**
	 *  保存一个对象
	 * @param t
	 * @return
	 */
	public Boolean save(SmsPhone t) {
		if (t == null) {
			return Boolean.FALSE;
		}
		String uuid = UUID.randomUUID().toString();
		t.setId(uuid);
		return dao.save(t);
	}

	/**
	 *  删除一个对象
	 * @param id
	 * @return
	 */
	public Boolean delete(String id) {
		return dao.delete(id);
	}
	
	/**
	 * 根据地区code删除该地区所有电话号码
	 * @param acode
	 * @return
	 */
	public Boolean deleteByArea(String acode){
		return dao.deleteByArea(acode);
	}

	/**
	 *  更新一个对象
	 * @param t
	 * @return
	 */
	public Boolean update(SmsPhone t) {
		if (t == null) {
			return Boolean.FALSE;
		}
		return dao.update(t);
	}

	/**
	 *  按id查询一个对象
	 * @param id
	 * @return
	 */
	public SmsPhone getById(String id) {
		return dao.getById(id);
	}
	
	/**
	 * 根据电话号码来查询一个电话是否已经存在
	 * @param id
	 * @return
	 */
	public SmsPhone getByPhone(String phone) {
		return dao.getByPhone(phone);
	}
	
	/**
	 * 根据电话号码,地区code来查询一个电话是否已经存在
	 * @param phone
	 * @param acode
	 * @return
	 */
	public SmsPhone getByPhoneAndArea(String phone,String acode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("acode", acode);
		return dao.getByPhoneAndArea(map);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<SmsPhone> getByPage(SmsPhone smsPhone, Integer startPage,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsPhone", smsPhone);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 *  获得数据总条数
	 * @param smsPhone
	 * @return
	 */
	public int getTotalCount(SmsPhone smsPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsPhone", smsPhone);
		return dao.getTotalCount(map);
	}
	
	/**
	 * 分页查询方法,
	 */
	public List<SmsPhone> getByArea(String acode) {
		Map<String, Object> map = new HashMap<String, Object>();
		SmsPhone smsPhone = new SmsPhone();
		smsPhone.setArea(new Area(acode));
		map.put("smsPhone", smsPhone);
		map.put("start", Constants.START);
		map.put("size", Integer.MAX_VALUE);
		return dao.getByPage(map);
	}

}
