package esd.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.StatisticsCompany;
import esd.controller.Constants;
import esd.dao.StatisticsCompanyDao;

/**
 * 企业/职位数据统计类service类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-8
 */
@Service
public class StatisticsCompanyService {

	@Autowired
	private StatisticsCompanyDao<StatisticsCompany> dao;

	/**
	 * 根据地区code, 获得该地区的企业及招聘岗位数据信息对象
	 * 
	 * @param acode
	 * @return
	 */
	public StatisticsCompany getByArea(String acode) {
		if (acode == null || "".equals(acode)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.AREACODE, acode);
		StatisticsCompany sc = dao.getJobCount(map);
		if (sc != null) {
			Integer numberCompany = dao.getCompanyCount(map);
			sc.setNumberCompany(numberCompany);
		}
		return sc;
	}

}
