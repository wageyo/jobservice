package esd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Attributes;
import esd.bean.Menu;
import esd.controller.Constants;
import esd.dao.MenuDao;

@Service
public class MenuService {

	private static Logger log = Logger.getLogger(MenuService.class);

	@Autowired
	private MenuDao dao;

	// 增
	public boolean save(Menu menu) {
		return dao.save(menu);
	}

	// 删
	public boolean delete(int id) {
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
	public List<Menu> getByAuthority(int authority) {
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menu = new Menu();
		menu.setAuthority(authority);
		menu.setEnable(true);
		map.put("menu", menu);
		map.put("start", Constants.START);
		map.put("size", Integer.MAX_VALUE);
		List<Menu> list = dao.getByPage(map);
		List<Menu> destList = new ArrayList<Menu>();
		for (Menu m : list) {
			if (authority == 999) {
				if (m.getUrl().equals("/jobservice/manage/setup")) {
					continue;
				}
			}
			Attributes a = new Attributes();
			a.setUrl(m.getUrl());
			m.setAttributes(a);
			destList.add(m);
		}
		return destList;
	}

	// 获得所有菜单状态
	public List<Menu> getMenuChecked() {
		return dao.getMenuChecked();
	}
}
