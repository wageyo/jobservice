package esd.dao;

import esd.bean.AdminInfo;

/**
 * NA级用户操作类Dao接口
 * 
 * @author Administrator
 * 
 */
public interface AdminInfoDao extends IDao<AdminInfo> {

	// 根据 账号id 得到对应NA对象
	public AdminInfo getByAccount(int uid);

	// // 根据县/区级id, 得到下属的NA
	// public List<AdminInfo> getByDistrict(int did);
	//
	// // 根据县/区级id, 得到下属的NA个数
	// public int getCountByDistrict(int did);
	//
	// // 根据地区id, 得到该地区总计的NA个数
	// public int getCountByArea(int aid);
	//
	// //根据地区code, 得到该地区总计的NA个数
	// public int getCountByAreaCode(String code);
	//
	// // 根据地区id, 得到该地区总计的NA详细列表
	// public List<AdminInfo> getByArea(int aid);
	//
	// // 根据地区code, 得到该地区总计的NA详细列表
	// public List<AdminInfo> getByAreaCode(String code);
}
