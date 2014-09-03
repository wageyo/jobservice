package esd.dao;

import esd.bean.Company;

/**
 * 公司信息操作Dao接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyDao extends IDao<Company> {

	// 根据 账号id 得到对应公司对象
	public Company getByAccount(int uid);

	//
}
