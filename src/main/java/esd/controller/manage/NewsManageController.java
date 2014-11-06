package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Job;
import esd.bean.News;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.KitService;
import esd.service.NewsService;


/**
 * 文章(就业指导/最新资讯)后台管理控制器
 * @author yufu
 * @email ilxly01@126.com
 * 2014-11-5
 */
@Controller
@RequestMapping("/manage/news")
public class NewsManageController {
	private static Logger log = Logger.getLogger(NewsManageController.class);

	@Autowired
	private NewsService newsService;

	// 转到职位管理列表页面
		@RequestMapping(value = "/news_list", method = RequestMethod.GET)
		public ModelAndView job_list(HttpServletRequest request, HttpSession session) {
			log.debug("goto：文章(就业指导/最新资讯)管理");
			Map<String, Object> entity = new HashMap<>();
			String pageStr = request.getParameter("page");
			Integer page = KitService.getInt(pageStr) > 0 ? KitService
					.getInt(pageStr) : 1;
					Integer rows = Constants.SIZE;
			News paramEntity = new News();
			// 名称模糊查询
			String targetName = request.getParameter("targetName");
			paramEntity.setTitle(targetName);
			// 获取文章类型查询条件
			String articleType = request.getParameter("articleType");
			// 判断显示类型, 如果传递的参数为空的话, 则默认首先显示就业指导内容
			if (articleType == null || "".equals(articleType)) {
				articleType = Constants.ARTICLE_TYPE_DIRECT;
			}
			paramEntity.setType(articleType);
			
			// // 获取地区码
			// User userObj = (User) request.getSession().getAttribute(
			// Constants.USER);
			// String acode = userObj.getArea().getCode();
			// // 设置查看地区码条件为管理员所在地区
			// jobType.setArea(new Area(acode));
//			// 如果为超级管理员, 则不设置地区码, 即读取所有地区信息
//						if (!userObj.getIdentity().equals(
//								Constants.Identity.SUPERADMIN.toString())) {
//							newsType.setArea(new Area(userObj.getArea().getCode()));// 地区码
//						}
			
			List<News> resultList = newsService.getListForShow(paramEntity, page,
					rows);
			Integer total = newsService.getTotalCount(paramEntity); // 数据总条数
			try {
				List<Map<String, Object>> list = new ArrayList<>();
				System.out.println("resultList.size()" + resultList.size());
				for (News tmp : resultList) {
					Map<String, Object> tempMap = new HashMap<>();
					tempMap.put("id", tmp.getId());// id
					tempMap.put("title", tmp.getTitle());// 标题
					tempMap.put("author", tmp.getAuthor());// 作者
					tempMap.put("source", tmp.getSource());// 来源/来路
					tempMap.put("articleType", KitService.getArticleName(tmp.getType()));// 文章类型
					tempMap.put("area", tmp.getArea());	//所属地区
					list.add(tempMap);
				}
				entity.put("total", total);
				entity.put("entityList", list);
				log.debug("获取文章(就业指导/最新资讯)信息，size():" + total );
			} catch (Exception e) {
				log.error("获取文章(就业指导/最新资讯)信息时发生错误。");
				e.printStackTrace();
			}
			//放入当前页数, 总页数, 职位名, 审核状态
			entity.put("currentPage", page);
			entity.put("totalPage", KitService.getTotalPage(total));
			entity.put("targetName", targetName);
			entity.put("articleType", articleType);
			entity.put("articleTypeName", KitService.getArticleName(articleType));
			return new ModelAndView("manage/news-list", entity);
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
