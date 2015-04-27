package esd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import esd.service.AreaService;
import esd.service.CookieHelper;
import esd.service.KitService;
import esd.service.NewsService;

/**
 * 就业指导controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/direct")
public class DirectController {

	private static Logger log = Logger.getLogger(DirectController.class);

	@Autowired
	private NewsService newsService;
	@Autowired
	private AreaService areaService;

	@RequestMapping("/search")
	public ModelAndView work(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("direct/direct");
		return mav;
	}

	// 就业指导信息 分页查询
	@RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
	public ModelAndView search(@PathVariable(value = "page") Integer page,HttpServletRequest request, HttpServletResponse response) {
		log.info("--- search ---");
		// 读取地区码
		// 得到地区code
	
		String acode = CookieHelper.getCookieValue(request, Constants.AREACODE);
		News n = new News();
		n.setType(Constants.ARTICLETYPE.DIRECT.getValue());
		n.setArea(new Area(acode));
		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			n.setTitle(keyWord);
		}
		String releaseDateStr = request.getParameter("releaseDate");
		if (releaseDateStr != null && !"".equals(releaseDateStr)) {
			Integer releaseDate = Integer.valueOf(releaseDateStr);
			Date update_Date=KitService.getreleaseTime(releaseDate.longValue());
			n.setUpdateDate(update_Date);
			
		}
		Map<String, Object> entity = new HashMap<String, Object>();
		List<News> newsList = newsService.getListForShow(n, page,
				Constants.SIZE);
		Integer records = newsService.getTotalCount(n);
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
		return new ModelAndView("direct/direct-json", "entity", entity);
	}

	// 根据id得到一个 就业指导信息 返回前台显示
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
		// 将direct放入到request中
		News news = newsService.getOneForShow(id);
		req.setAttribute("news", news);
		String acode = CookieHelper.getCookieValue(req, Constants.AREACODE);
		Area area = areaService.getByCode(acode);
		News newsparameter=new News();
		newsparameter.setType(Constants.ARTICLETYPE.DIRECT.getValue());
		newsparameter.setArea(area);
		
		// 再取15条信息放入到request中
		List<News> list = newsService.getTitleList(newsparameter,
				1, 15);
		req.setAttribute("newsList", list);
		return "direct/direct-detail";
	}

}
