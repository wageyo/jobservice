package esd.dao;

import esd.bean.SmsAccount;

/**
 * 各地区名商通账号dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-6
 */
public interface SmsAccountDao extends IDao<SmsAccount> {

	/**
	 * 根据地区code获得本地区的名商通账号
	 * 
	 * @param acode
	 * @return
	 */
	SmsAccount getByArea(String acode);
}
