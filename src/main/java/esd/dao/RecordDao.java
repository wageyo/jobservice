package esd.dao;

import java.util.Map;

import esd.bean.Record;

/**
 * 简历--职位关系类操作Dao接口
 * 
 * @author Administrator
 * 
 */
public interface RecordDao extends IDao<Record> {

	/**
	 * 检查7天内 是否存在同样的数据(个人投递简历/公司发送邀请)
	 * 
	 * @param uid
	 * @param jid
	 * @param direction  true--个人投向公司, false--公司发送邀请
	 * @return
	 */
	public int checkSentInSomeDays(Map<String, Object> map);
}
