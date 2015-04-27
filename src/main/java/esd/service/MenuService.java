package esd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Menu;
import esd.controller.Constants;
import esd.dao.MenuDao;

@Service
public class MenuService {

	@Autowired
	private MenuDao dao;

	// 增
	public boolean save(Menu menu) {
		return dao.save(menu);
	}

	// 删
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	// 改
	public boolean update(Menu menu) {
		return dao.update(menu);
	}

	// 根据id查
	public Menu getById(String id) {
		return dao.getById(id);
	}

	// 按管理员级别获得管理员菜单
	public List<Menu> getByAuthority(Integer authority) {
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menu = new Menu();
		menu.setAuthority(authority);
		menu.setIsActive(Boolean.TRUE);
		map.put("menu", menu);
		map.put("start", Constants.START);
		map.put("size", 999);
		List<Menu> resultList = dao.getByPage(map);
		if (resultList == null) {
			return null;
		}
		// 一级菜单列表
		List<Menu> topMenuList = new ArrayList<Menu>();
		for (Menu temp : resultList) {
			if ("10".equals(temp.getId().subSequence(0, 2))) {
				topMenuList.add(temp);
			}
		}
		// 将二级菜单添加到一级菜单上
		if (topMenuList.size() == 0) {
			return null;
		}
		for (Menu topMenu : topMenuList) {
			String topId = topMenu.getId();
			for (Menu result : resultList) {
				String resultId = result.getId();
				if (result.getId().startsWith("20")
						&& (resultId.substring(2, 4).equals(topId.subSequence(
								2, 4)))) {
					topMenu.getChildren().add(result);
				}
			}
		}
		return topMenuList;
	}

}
