package esd.dao;

import esd.bean.WhiteList;

/**
 * 白名单类操作Dao接口
 * @author yufu
 * @email ilxly01@126.com
 * 2015-3-20
 */
public interface WhiteListDao extends IDao<WhiteList> {

	/**
	 * 根据ip或域名来判断 该ip是否在白名单列表中
	 * @param whiteList
	 * @return
	 */
	public WhiteList getByIdAndServername(WhiteList whiteList);
}
