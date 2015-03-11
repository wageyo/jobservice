package esd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esd.bean.User;

public interface UserDao extends IDao<User> {

	public User getByUser(User user);

	/**
	 * 获得管理员（分页）
	 * 
	 * @param map
	 * @return
	 */
	List<User> getAdminByPage(Map<String, Object> map);

	/**
	 * 获得管理员（分页）
	 * 
	 * @param map
	 * @return
	 */
	int getAdminTotalCount(Map<String, Object> map);

	/**
	 * 根据账号id, 获得他的头像图片
	 * 
	 * @param id
	 * @return
	 */
	HashMap<String, Object> getHeadImage(Integer id);

	/**
	 * 查询对应等级以下的所有账号 分页查询方法,
	 * 
	 * @param map
	 * @return
	 */
	List<User> getByPageAuthority(Map<String, Object> map);

	/**
	 * 查询对应等级以下的所有账号获得数据总条数
	 * 
	 * @param map
	 * @return
	 */
	Integer getTotalCountAuthority(Map<String, Object> map);
}
