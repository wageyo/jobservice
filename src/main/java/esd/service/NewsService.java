package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.News;
import esd.controller.Constants;
import esd.dao.NewsDao;

/**
 * 消息操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class NewsService {

	@Autowired
	private NewsDao dao;

	@Autowired
	private KitService kitService;

	// 保存一个对象
	public boolean save(News news) {
		return dao.save(news);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(News news) {
		return dao.update(news);
	}

	// 按id查询一个对象
	public News getById(int id) {
		return (News) dao.getById(id);
	}

	// 按id查询一个对象,以供前台显示
	public News getOneForShow(int id) {
		News news = (News) dao.getById(id);
		news = kitService.getForShow(news);
		return news;
	}

	// 分页查询方法,--标准分页方法
	// @param map中为具体的参数
	// 1-类对象, 名称为对应类的小写!!切记切记!! 字段的值即为查询条件; 2-start: 起始索引; 3-size: 返回条数
	public List<News> getByPage(News news, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("news", news);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<News> list = dao.getByPage(map);
		return list;
	}

	// 分页查询方法,其中数据已被处理成适合前台展示的
	public List<News> getForListShow(News news, int startPage, int size) {
		if(news!=null){
			if(news.getArea()!=null){
				String code = news.getArea().getCode();
				if(code!=null &&!"".equals(code)){
					// 处理成查询本省内的信息的code
					news.getArea().setCode(KitService.getCodeForNews(code));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("news", news);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<News> list = dao.getByPage(map);
		list = kitService.getForShowNews(list);
		return list;
	}

	// 得到最新的N个信息
	public List<News> getByNew(String acode, int size, String type) {
		acode = KitService.getCodeForNews(acode);
		Map<String, Object> map = new HashMap<String, Object>();
		News news = new News();
		news.setArea(new Area(acode));
		news.setType(type);
		map.put("news", news);
		map.put("start", Constants.START);
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<News> list = dao.getByPage(map);
		list = kitService.getForShowNews(list);
		return list;
	}

	// 获得数据总条数
	public int getTotalCount(News news) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("news", news);
		return dao.getTotalCount(map);
	}

	// 取得不带内容的新闻列表
	public List<News> getTitleList(String type, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		News news = new News();
		news.setType(type);
		map.put("news", news);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<News> list = dao.getTitleList(map);
//		for (News n : list) {
//			if (n.getCreateDate() != null) {
//				n.setCreateDate(KitService.dateForShow(n.getCreateDate()));
//			}
//		}
		return list;

	}
}
