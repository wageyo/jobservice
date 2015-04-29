package esd.dao;

import java.util.List;

import esd.bean.SmsHistory;

/**
 * 短信发送历史
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-29
 */
public interface SmsHistoryDao extends IDao<SmsHistory> {

	/***
	 * 批量插入数据
	 * 
	 * @param smsHistoryList
	 * @return
	 */
	public Boolean saveSmsHistoryBatch(List<SmsHistory> smsHistoryList);
	
	/**
	 * 根据电话号码获取发送历史记录
	 * @param phonenumber
	 * @return
	 */
	public List<SmsHistory> getByPhone(String phonenumber);

}
