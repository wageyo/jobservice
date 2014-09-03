package esd.dao;

import java.util.List;

import esd.bean.BusinessScope;

/**
 * 行业类别操作Dao接口
 * @author Administrator
 *
 */
public interface BusinessScopeDao extends IDao<BusinessScope>{

	//得到所有数据
	public List<BusinessScope> getAll();
}
