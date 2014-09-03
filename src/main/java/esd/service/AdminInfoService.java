package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.AdminInfo;
import esd.dao.AdminInfoDao;

/**
 * 管理员操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class AdminInfoService {
	private static Logger log = Logger.getLogger(AdminInfoService.class);
	@Autowired
	private AdminInfoDao dao;

	// 保存一个对象
	public boolean save(AdminInfo adminInfo) {
		return dao.save(adminInfo);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(AdminInfo adminInfo) {
		return dao.update(adminInfo);
	}

	// 按id查询一个对象
	public AdminInfo getById(int id) {
		log.info(" id in service is " + id);
		return (AdminInfo) dao.getById(id);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 名称为对应类的小写!!切记切记!! 字段的值即为查询条件; 2-startPage: 起始页;
	 *            3-size: 返回条数
	 */
	public List<AdminInfo> getByPage(AdminInfo adminInfo, int startPage,
			int size) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("adminInfo", adminInfo);
		if (startPage < 0)
			startPage = 1;
		if (size < 0)
			size = 50;
		map.put("start", (startPage - 1) * size);
		map.put("size", size);
		List<AdminInfo> list = dao.getByPage(map);
		for (AdminInfo j : list) {
			j.setUpdateDate(KitService.dateForShow(j.getUpdateDate()));
		}
		log.info("adminInfoList.size() = " + list.size());
		return list;
	}

	// 获得数据总条数
	public int getTotalCount(Map<String,Object> map) {
		return dao.getTotalCount(map);
	}

	// 根据用户账号id, 得到录入员对象
	public AdminInfo getByAccount(int uid) {
		log.info("uid in service  = " + uid);
		return dao.getByAccount(uid);
	}

	// //根据县/区级id, 得到其下属的录入员的个数
	// public int getCountByDistrict(int aid){
	// return dao.getCountByDistrict(aid);
	// }
	//
	// // 根据地区id, 得到该地区总计的录入员个数
	// public int getCountByArea(int aid){
	// return dao.getCountByArea(aid);
	// }

	// // 根据地区code, 得到该地区总计的录入员个数
	// public int getCountByArea(String code){
	// if(code==null || "".equals(code)){
	// return 0;
	// }
	// String start = code.substring(0,2);
	// String mid;
	// if("10000000".equals(code)){
	// code = "30______";
	// }else if("10".equals(start)){
	// mid = code.substring(2,4);
	// code = "30"+mid+"____";
	// }else if("20".equals(start)){
	// mid = code.substring(2,6);
	// code = "30"+mid+"__";
	// }
	// return dao.getCountByAreaCode(code);
	// }
	//
	// // 根据地区id, 得到该地区总计的录入员详细列表
	// public List<AdminInfo> getByArea(int aid){
	// return dao.getByArea(aid);
	// }
	//
	// // 根据地区code, 得到该地区总计的录入员详细列表
	// public List<AdminInfo> getByArea(String code){
	// if(code==null || "".equals(code)){
	// return null;
	// }
	// code = KitService.codeForSql(code);
	// return dao.getByAreaCode(code);
	// }
}
