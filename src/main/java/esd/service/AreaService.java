package esd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.dao.AreaDao;

/**
 * 地区操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class AreaService {

	@Autowired
	private AreaDao dao;

	// 保存一个对象
	public boolean save(Area area) {
		return dao.save(area);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(Area area) {
		return dao.update(area);
	}

	// 按id查询一个对象
	public Area getById(int id) {
		System.out.println("id in service = " + id);
		return (Area) dao.getById(id);
	}

	// 根据地区code查询一个对象
	public Area getByCode(String code) {
		Area a = new Area();
		a.setCode(code);
		List<Area> alist = dao.getByObj(a);
		if (alist != null && alist.size() > 0) {
			return alist.get(0);
		}
		return null;
	}

	// 按area自身属性查询
	public Area getByObj(Area area) {
		return (Area) dao.getByObj(area);
	}

	// 获得数据总条数
	public int getTotalCount() {
		return dao.getTotalCount(null);
	}

	// 获得所有数据
	public List<Area> getAll() {
		return dao.getAll();
	}

	// 得到省级菜单
	public List<Area> getProvinceList() {
		return dao.getSubArea("10______");
	}

	// 根据地区code,得到同级市级地区列表
	public List<Area> getCityListByCode(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		code = "20" + code.substring(2, 4) + "____";
		List<Area> list = dao.getByObj(new Area(code));
		return list;
	}

	// 根据地区code,得到同级县区级地区列表
	public List<Area> getDistrictListByCode(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		code = "30" + code.substring(2, 6) + "__";
		List<Area> list = dao.getByObj(new Area(code));
		return list;
	}

	// 根据地区code, 得到下属的市/县
	public List<Area> getSubArea(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "20" + mid + "____";
		} else if ("20".equals(start)) {
			mid = code.substring(2, 6);
			code = "30" + mid + "__";
		}
		return dao.getSubArea(code);
	}

}
