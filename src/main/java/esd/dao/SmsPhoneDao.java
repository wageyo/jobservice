package esd.dao;

import java.util.Map;

import esd.bean.SmsPhone;

/**
 * 短信自定义发送电话列表Dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-20
 */
public interface SmsPhoneDao extends IDao<SmsPhone> {

	
	/**
	 * 按地区code删除该地区所有电话号码
	 * @param acode
	 * @return
	 */
	Boolean deleteByArea(String acode);
	
	/**
	 * 根据电话号码查询电话是否已经存在, 返回该对象
	 * @param phone
	 * @return
	 */
	SmsPhone getByPhone(String phone);
	
	/**
	 * 根据电话号码, 地区码查询电话是否已经存在, 返回该对象
	 * @param map(含phone, acode两个参数 )
	 * @return
	 */
	SmsPhone getByPhoneAndArea(Map<String,Object> map);
}
