package esd.dao;

import java.util.List;

import esd.bean.Company;

/**
 * 公司信息操作Dao接口
 * @author yufu
 * @email ilxly01@126.com
 * 2015-3-20
 */
public interface CompanyDao extends IDao<Company> {

	/**
	 *  根据 账号id 得到对应公司对象
	 * @param uid
	 * @return
	 */
	public Company getByAccount(int uid);

	/**
	 * 根据多个id获得一列公司
	 * @param ids
	 * @return
	 */
	public List<Company> getByIds(Integer[] ids);
}
