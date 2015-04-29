package esd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.SmsHistory;
import esd.controller.Constants;
import esd.dao.SmsHistoryDao;

/**
 * 短信发送历史记录servie类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-29
 */
@Service
public class SmsHistoryService {

	@Autowired
	private SmsHistoryDao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param t
	 * @return
	 */
	public Boolean save(SmsHistory t) {
		if (t == null) {
			return Boolean.FALSE;
		}
		String uuid = UUID.randomUUID().toString();
		t.setId(uuid);
		return dao.save(t);
	}

	/**
	 * 保存多个电话号码的发送记录
	 * 
	 * @param phonenumberList
	 * @param shortMessage
	 * @return
	 */
	public Boolean saveBatch(String[] phonenumberList, String shortMessage,
			String logUser) {
		List<SmsHistory> list = new ArrayList<SmsHistory>();
		if (phonenumberList == null) {
			return Boolean.FALSE;
		}
		for (String phonenumber : phonenumberList) {
			SmsHistory sh = new SmsHistory();
			sh.setId(KitService.getUUID());
			sh.setLogUser(logUser);
			sh.setPhonenumber(phonenumber);
			sh.setContent(shortMessage);
			list.add(sh);
		}
		return dao.saveSmsHistoryBatch(list);
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
	public Boolean update(SmsHistory t) {
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
	public SmsHistory getById(String id) {
		return dao.getById(id);
	}

	/**
	 * 根据一个电话号码查询 发送历史记录
	 * 
	 * @param id
	 * @return
	 */
	public List<SmsHistory> getByPhone(String phone) {
		return dao.getByPhone(phone);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<SmsHistory> getByPage(SmsHistory smsHistory, Integer startPage,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsHistory", smsHistory);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 * 获得数据总条数
	 * 
	 * @param smsHistory
	 * @return
	 */
	public int getTotalCount(SmsHistory smsHistory) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsHistory", smsHistory);
		return dao.getTotalCount(map);
	}

}
