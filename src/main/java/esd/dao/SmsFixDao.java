package esd.dao;

import esd.bean.SmsFix;

/**
 * 推送就业信息前后缀 自定义表Dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-4
 */
public interface SmsFixDao extends IDao<SmsFix> {

	/**
	 * 根据地区code获得本地区的推送信息上下文
	 * @param acode
	 * @return
	 */
	SmsFix getByArea(String acode);
}
