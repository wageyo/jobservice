package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.News;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.NewsService;

/*
 * 文章管理控制器
 * */

@Controller
@RequestMapping("/manage")
public class NewsManageController {
	private static Logger log = Logger.getLogger(NewsManageController.class);

	@Autowired
	private NewsService newsService;

	/*
	 * 转到 文章列表 页面
	 */
	@RequestMapping(value = "/news_list", method = RequestMethod.GET)
	public ModelAndView news_list(HttpServletRequest request) {
		log.debug("goto：文章管理");
		return new ModelAndView("manage/news-list");
	}

	/*
	 * 转到 文章显示 页面
	 */
	@RequestMapping(value = "/news_view", method = RequestMethod.GET)
	public ModelAndView news_view(HttpServletRequest request) {
		log.debug("goto：显示文章");
		return new ModelAndView("manage/news-view");
	}

	/*
	 * 转到 增加文章 页面
	 */
	@RequestMapping(value = "/news_add/{id}", method = RequestMethod.GET)
	public ModelAndView news_add(@PathVariable(value = "id") int id,
			HttpServletRequest request) {
		log.debug("goto： 增加文章");

		if (id != -1) {
			request.setAttribute("id", id);
		}
		return new ModelAndView("manage/news-add");
	}

	/*
	 * 增加文章
	 */
	@RequestMapping(value = "/news_add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean newsAdd(News params, HttpServletRequest request) {

		try {
			log.debug(" 增加文章" + params);
			User u = (User) request.getSession().getAttribute(Constants.USER);
			params.setArea(u.getArea());
			boolean b = newsService.save(params);
			log.debug(" 增加文章" + params + "       结果为：" + b);
			return b;
		} catch (Exception e) {
			log.error("增加文章错误");
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * 更新文章
	 */
	@RequestMapping(value = "/news_edit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean news_edit(News params, HttpServletRequest request) {

		try {
			log.debug("   更新文章" + params);
			User u = (User) request.getSession().getAttribute(Constants.USER);
			params.setArea(u.getArea());

			boolean b = newsService.update(params);
			log.debug("   更新文章" + params + "       结果为：" + b);
			return b;
		} catch (Exception e) {
			log.error("  更新文章错误");
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * 查看文章
	 */
	@RequestMapping(value = "/news_view", method = RequestMethod.POST)
	@ResponseBody
	public Object news_view(@RequestParam(value = "id") Integer id,
			HttpServletRequest request) {

		try {
			log.debug(" 查看文章" + id);

			News news = newsService.getById(id);
			log.debug(" 查看文章" + news);
			return news;
		} catch (Exception e) {
			log.error("查看文章错误");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取 文章 列表页面数据
	 */
	@RequestMapping(value = "/news_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> job_lisdatat(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			String type = request.getParameter("type");
			// 获取地区码
			User userObj = (User) request.getSession().getAttribute(
					Constants.USER);
			if (type == null) {
				log.error("获取文章列表数据，类型参数错误。");
				return null;
			}
			News newsType = new News();
			// 如果为超级管理员, 则不设置地区码, 即读取所有地区信息
			if (!userObj.getIdentity().equals(
					Constants.Identity.SUPERADMIN.toString())) {
				newsType.setArea(new Area(userObj.getArea().getCode()));// 地区码
			}
			log.info("area " + newsType.getArea());
			// 判断显示类型
			// 就业指导
			if (type.equals(Constants.INFO_TYPE_DIRECT)) {
				newsType.setType(Constants.INFO_TYPE_DIRECT);// 文章类型
				log.debug("get：文章  就业指导 数据");
			}
			// 最新资讯
			if (type.equals(Constants.INFO_TYPE_NEWS)) {
				newsType.setType(Constants.INFO_TYPE_NEWS);// 文章类型
				log.debug("get：文章 最新资讯 数据");
			}
			List<News> newslist = newsService.getForListShow(newsType, page,
					rows);
			Integer total = newsService.getTotalCount(newsType); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (News news : newslist) {
				Map<String, Object> newsmap = new HashMap<>();
				newsmap.put("id", news.getId());// id
				newsmap.put("title", news.getTitle());// 标题
				newsmap.put("author", news.getAuthor());// 作者
				newsmap.put("source", news.getSource());// 来源/来路
				String sType = news.getType();
				if (sType.equals(Constants.INFO_TYPE_NEWS)) {
					newsmap.put("type", "最新资讯");// 文章类型
				}
				if (sType.equals(Constants.INFO_TYPE_DIRECT)) {
					newsmap.put("type", "就业指导");// 文章类型
				}
				list.add(newsmap);
			}
			log.debug("getListData: 文章数据：" + total + "   user:" + userObj);
			entity.put("total", total);
			entity.put("rows", list);

		} catch (Exception e) {
			log.error("获取 文章列表数据信息时发生错误。");
			e.printStackTrace();
		}
		return entity;
	}

	/*
	 * 删除文章
	 */
	@RequestMapping(value = "/news_del", method = RequestMethod.POST)
	@ResponseBody
	public boolean audit_resume(
			@RequestParam(value = "params[]") Integer params[],
			HttpServletRequest request) {

		if (params == null || params.length <= 0) {

			log.error("dele news error");
			return false;
		}
		// 删除单个
		if (params.length == 1) {
			log.debug("del news dange");
			return newsService.delete(params[0]);
		}
		// 删除多个
		else {
			for (int i = 0; i < params.length; i++) {

				newsService.delete(params[i]);
			}
			log.debug("del news 多个");
			return true;
		}

	}

}
