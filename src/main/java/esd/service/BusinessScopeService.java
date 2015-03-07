package esd.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.BusinessScope;
import esd.dao.BusinessScopeDao;

/**
 * 公司经营范围操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class BusinessScopeService {
	private static Logger log = Logger.getLogger(BusinessScopeService.class);
	@Autowired
	private BusinessScopeDao dao;

	// 保存一个对象
	public boolean save(BusinessScope businessScope) {
		return dao.save(businessScope);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(BusinessScope businessScope) {
		return dao.update(businessScope);
	}

	// 按id查询一个对象
	public BusinessScope getById(int id) {
		log.info("id in service = " + id);
		return (BusinessScope) dao.getById(id);
	}

	// 获得所有数据
	public List<BusinessScope> getAll() {
		return dao.getAll();
	}
}
