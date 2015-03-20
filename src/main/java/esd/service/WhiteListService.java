package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.WhiteList;
import esd.controller.Constants;
import esd.dao.WhiteListDao;

@Service
public class WhiteListService {

	@Autowired
	private WhiteListDao dao;

	// 保存一个对象
	public boolean save(WhiteList whiteList) {
		String id = UUID.randomUUID().toString();
		whiteList.setId(id);
		return dao.save(whiteList);
	}

	// 删除一个对象
	public boolean delete(String id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(WhiteList whiteList) {
		return dao.update(whiteList);
	}

	// 按id查询一个对象,以供前台显示
	public WhiteList getById(String id) {
		return dao.getById(id);
	}

	/**
	 * 分页查询方法, 其中的数据没有做处理 管理后台/常用情况--使用
	 * 
	 * @param whiteList
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<WhiteList> getByPage(WhiteList whiteList, int startPage,
			int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("whiteList", whiteList);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<WhiteList> list = dao.getByPage(map);
		return list;
	}

	/**
	 * 获得数据总条数
	 * 
	 * @param object
	 *            --带有各种查询属性的对象
	 * @return
	 */
	public int getTotalCount(WhiteList object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("whiteList", object);
		return dao.getTotalCount(map);
	}

	/**
	 * 检查传递进来的ip或域名是否存在于白名单中!!!!
	 * 
	 * @param url
	 */
	public WhiteList checkWhiteList(String ip,String serverName) {
		WhiteList whiteList = new WhiteList();
		whiteList.setIp(ip);
		whiteList.setDomainName(serverName);
		WhiteList result = dao.getByIdAndServername(whiteList);
		return result;
	}
}
