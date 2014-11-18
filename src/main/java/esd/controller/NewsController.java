package esd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Area;
import esd.bean.News;
import esd.service.KitService;
import esd.service.NewsService;

/**
 * 最新消息controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/news")
public class NewsController {

	private static Logger log = Logger.getLogger(NewsController.class);

	@Autowired
	private NewsService newsService;

	@RequestMapping("/search")
	public ModelAndView work(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("news/news");
		return mav;
	}

	// 最新消息 分页查询
	@RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest req,
			@PathVariable(value = "page") Integer page,HttpSession session) {
		log.info("--- search ---");
		//读取地区码
		//得到地区code
		Object obj = session.getAttribute("area");
		String acode = null;
		if (obj != null) {
			acode = ((Area) obj).getCode();
		}
		News n = new News();
		n.setType(Constants.ARTICLE_TYPE_NEWS);
		n.setArea(new Area(acode));
		Map<String, Object> entity = new HashMap<String, Object>();
		List<News> newsList = newsService.getListForShow(n, page,
				Constants.SIZE);
		Integer records = newsService.getTotalCount(new News(Constants.ARTICLE_TYPE_NEWS));
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (newsList != null && records != null && records > 0) {
			try {
				for (Iterator<News> iterator = newsList.iterator(); iterator
						.hasNext();) {
					News it = (News) iterator.next();
					log.debug(it.toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(it.getId()));
					map.put("createDate", KitService.dateForShow(it.getCreateDate()));
					map.put("title", it.getTitle());
					map.put("areaName", it.getArea().getName());
					map.put("author", it.getAuthor());
					map.put("source", it.getSource());
					list.add(map);
				}
			} catch (Exception e) {
				log.error("error in list", e);
			}
		}
		while (list.size() < Constants.SIZE) {
			Map<String, String> map = new HashMap<String, String>();
			list.add(map);
		}

		entity.put("list", list);
		log.info("*******************************************************************");
		log.info("records = " + records);
		PaginationUtil pagination = new PaginationUtil(page, records);
		entity.put("pagination", pagination.getHandler());
		return new ModelAndView("news/news-json", "entity", entity);
	}

	// 根据id得到一个新闻返回前台显示
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest req, RedirectAttributes ra) {
		log.info("--- getOneForShow ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "传递的参数有误!");
			return "/error";
		}
		// 将news放入到request中
		News news = newsService.getOneForShow(id);
		req.setAttribute("news", news);
		// 再取15条信息放入到request中
		List<News> list = newsService.getTitleList(news.getType(), 1, 15);
		req.setAttribute("newsList", list);
		return "news/news-detail";
	}

	
}
