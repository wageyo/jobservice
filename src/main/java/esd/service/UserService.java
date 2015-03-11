package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.dao.AreaDao;
import esd.dao.ParameterDao;
import esd.dao.UserDao;

/**
 * 用户service类
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@Service
public class UserService<T> {

	@Autowired
	private UserDao dao;

	@Autowired
	private ParameterService pService;

	@Autowired
	private ParameterDao pDao;

	@Autowired
	private AreaDao aDao;

	// 保存一个对象
	public boolean save(User user) {
		// 审核处理
		boolean bl = pService.getSwitchStatus(Constants.Switch.USER.toString(),
				user.getArea().getCode());
		if (user != null) {
			// 如果user审核开关打开的话, 则将user设置为 待审核 状态
			if (bl) {
				user.setCheckStatus(Constants.CheckStatus.DAISHEN.toString());
			} else {
				// //如果user审核开关没有打开的话, 则将user设置为 ok 状态
				user.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
		}
		return dao.save(user);
	}

	// 保存管理员用户
	public boolean saveAdmin(User user) {
		// 检查开关是否存在 --不存在时, 先保存用户, 再插入 该地区的开关
		boolean bl_admin = false;
		// 保存用户对象
		if (user != null) {
			// 管理员账号审核状态,默认为OK
			user.setCheckStatus(Constants.CheckStatus.OK.toString());
			// 管理员权限
			user.setIdentity(Constants.Identity.ADMIN.getValue());
			user.setAuthority(Constants.Authority.ADMIN.getValue());
			user.setUpdateCheck(dao.getUpdateCheck(user.getId()));
			bl_admin = dao.save(user);
			// 保存失败时
			if (!bl_admin) {
				return false;
			}
		}
		// 保存该用户所在地区审核开关
		// 检查该地区开关是否存在 --存在时
		Parameter p = new Parameter();
		p.setArea(user.getArea());
		p.setType(Constants.SWITCH);
		List<Parameter> plist = pService.getByParameter(p);
		if (plist != null && plist.size() != 0) {
			return true;
		}
		// 先查出该账号地区对象,方便下面使用
		Area area = aDao.getByCode(user.getArea().getCode());
		p.setArea(area);
		p.setValue(Constants.SWITCH_OFF);
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString();

		// 开关默认值为关闭状态 "off"
		// 账号开关
		p.setId(uid);
		p.setName(Constants.Switch.USER.toString());
		p.setMark("是否审核开关--账号--" + p.getArea().getName());
		boolean bl1 = pDao.save(p);
		// 企业
		uuid = UUID.randomUUID();
		uid = uuid.toString();
		p.setId(uid);
		p.setName(Constants.Switch.COMPANY.toString());
		p.setMark("是否审核开关--企业--" + p.getArea().getName());
		boolean bl2 = pDao.save(p);
		// 职位
		uuid = UUID.randomUUID();
		uid = uuid.toString();
		p.setId(uid);
		p.setName(Constants.Switch.JOB.toString());
		p.setMark("是否审核开关--职位--" + p.getArea().getName());
		boolean bl3 = pDao.save(p);
		// 简历
		uuid = UUID.randomUUID();
		uid = uuid.toString();
		p.setId(uid);
		p.setName(Constants.Switch.RESUME.toString());
		p.setMark("是否审核开关--简历--" + p.getArea().getName());
		boolean bl4 = pDao.save(p);
		if (bl1 && bl2 && bl3 && bl4) {
			return true;
		}
		return false;
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(User user) {
		user.setUpdateCheck(dao.getUpdateCheck(user.getId()));
		return dao.update(user);
	}

	// 按id查询一个对象
	public User getById(int id) {
		return (User) dao.getById(id);
	}

	// 查询用户名是否存在
	public User check(String loginName) {
		User user = new User();
		user.setLoginName(loginName);
		return dao.getByUser(user);
	}

	// 查询用户名和账号是否存在
	public User check(User user) {
		return dao.getByUser(user);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<User> getByPage(User user, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.USER, user);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 * 地区code不做处理
	 * 
	 * @param user
	 * @return
	 */
	public List<User> getByPageWithOldAreaCode(User user, int startPage,
			int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.USER, user);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	// 获得数据总条数
	public int getTotalCount(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.USER, user);
		return dao.getTotalCount(map);
	}

	// 获得账号的头像
	public byte[] getHeadImage(Integer id) {
		if (id == null || id <= 0) {
			return null;
		}
		HashMap<String, Object> resultMap = dao.getHeadImage(id);
		byte[] headImage = (byte[]) resultMap.get("head_image");
		return headImage;
	}

	/**
	 * 查询对应等级以下的所有账号 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<User> getByPageAuthority(User user, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.USER, user);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPageAuthority(map);
	}

	/**
	 * 查询对应等级以下的所有账号获得数据总条数
	 * @param user
	 * @return
	 */
	public int getTotalCountAuthority(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.USER, user);
		return dao.getTotalCountAuthority(map);
	}
}
