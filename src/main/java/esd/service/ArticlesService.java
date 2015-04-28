package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.Articles;
import esd.bean.Parameter;
import esd.controller.Constants;
import esd.dao.ArticlesDao;
import esd.dao.ParameterDao;

/**
 * 消息操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class ArticlesService {

	@Autowired
	private ArticlesDao dao;

	@Autowired
	private KitService kitService;
	
	@Autowired
	private ParameterDao parameterDao;
	
	// 保存一个对象
	public boolean save(Articles articles) {
		articles.setId(KitService.getUUID());
		return dao.save(articles);
	}

	// 删除一个对象
	public boolean delete(String id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(Articles articles) {
		return dao.update(articles);
	}

	// 按id查询一个对象
	public Articles getById(String id) {
		return (Articles) dao.getById(id);
	}

	// 按id查询一个对象,以供前台显示
	public Articles getOneForShow(String id) {
		Articles articles = (Articles) dao.getById(id);
		articles = kitService.getForShow(articles);
		return articles;
	}

	/**
	 * 获得对应地区的联系我们信息
	 * @param areaCode
	 * @return
	 */
	public Articles getContact(String acode){
		if(acode == null || "".equals(acode)){
			return null;
		}
		return dao.getContact(acode);
	}
	
	/**
	 * 分页查询方法, 其中的数据没有做处理
	 * 后台/常用--使用
	 */
	public List<Articles> getByPage(Articles articles, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", articles);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Articles> list = dao.getByPage(map);
		return list;
	}
	/**
	 * 分页查询方法, 其中数据已被处理成适合前台展示的形式 前台--使用
	 * 
	 * @param object
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Articles> getForListShow(Articles object, int startPage, int size) {
		if (object != null) {
			// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围
			if (object.getArea() != null) {
				if (object.getArea().getCode() != null
						&& !"".equals(object.getArea().getCode())) {
					Parameter parameter = parameterDao
							.getShareScopeByArea(object.getArea().getCode());
					if (parameter != null) {
						String areaSql = KitService.getAreaSqlFromShareScope(
								parameter.getValue(), object.getArea()
										.getCode());
						object.setArea(new Area(areaSql));
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Articles> list = dao.getByPage(map);
		list = kitService.getForShowNews(list);
		return list;
	}
	
	/**
	 * 分页查询方法, 其中数据已被处理成适合前台展示的
	 * 前台--使用
	 */
	public List<Articles> getListForShow(Articles articles, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", articles);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Articles> list = dao.getByPage(map);
		list = kitService.getForShowNews(list);
		return list;
	}

	// 得到最新的N个信息
	public List<Articles> getByNew(String acode, int size, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Articles articles = new Articles();
		if(Constants.AREACOUNTRY.equals(acode)){
			articles.setArea(new Area(null));
		}else{
			articles.setArea(new Area(acode));
		}
		articles.setType(type);
		map.put("articles", articles);
		map.put("start", Constants.START);
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Articles> list = dao.getByPage(map);
		list = kitService.getForShowNews(list);
		return list;
	}

	// 获得数据总条数
	public int getTotalCount(Articles articles) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", articles);
		return dao.getTotalCount(map);
	}

	// 取得不带内容的新闻列表
	public List<Articles> getTitleList(Articles object, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articles", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Articles> list = dao.getTitleList(map);
//		for (Articles n : list) {
//			if (n.getCreateDate() != null) {
//				n.setCreateDate(KitService.dateForShow(n.getCreateDate()));
//			}
//		}
		return list;

	}

	/**
	 * 获得指定地区最新的5个带图片的新闻
	 * @param acode
	 * @return
	 */
	public List<Articles> getFiveChangeList(String acode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("area", acode);
		map.put("type", Constants.ARTICLETYPE.NEWS.getValue());
		return dao.getFiveWithImage(map);
	}
}
