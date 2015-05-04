package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Articles;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CookieHelper;
import esd.service.KitService;
import esd.service.ArticlesService;
import esd.service.ParameterService;
import esd.service.UserService;

/**
 * 文章(就业指导/最新资讯)后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/articles")
public class ArticlesManageController {
	private static Logger log = Logger.getLogger(ArticlesManageController.class);

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private ArticlesService articlesService;
	
	@Autowired
	private ParameterService pService;

	// 转到职位管理列表页面
	@RequestMapping(value = "/articles_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request) {
		log.debug("goto：文章(就业指导/最新资讯)管理");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Articles paramEntity = new Articles();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		paramEntity.setTitle(targetName);
		// 获取文章类型查询条件
		String articleType = request.getParameter("articleType");
		// 判断显示类型, 如果传递的参数为空的话, 则默认首先显示就业指导内容
		if (articleType == null || "".equals(articleType)) {
			articleType = Constants.ARTICLETYPE.DIRECT.getValue();
		}
		paramEntity.setType(articleType);

		// 获取地区码
		String userId = CookieHelper.getCookieValue(request, Constants.ADMINUSERID);
		if(userId == null || "".equals(userId)){
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Articles> resultList = articlesService.getListForShow(paramEntity, page,
				rows);
		Integer total = articlesService.getTotalCount(paramEntity); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			log.info("resultList.size()" + resultList.size());
			for (Articles tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("title", tmp.getTitle());// 标题
				tempMap.put("author", tmp.getAuthor());// 作者
				tempMap.put("source", tmp.getSource());// 来源/来路
				tempMap.put("articleType",
						KitService.getArticleName(tmp.getType()));// 文章类型
				tempMap.put("area", tmp.getArea()); // 所属地区
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取文章(就业指导/最新资讯)信息，size():" + total);
		} catch (Exception e) {
			log.error("获取文章(就业指导/最新资讯)信息时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("articleType", articleType);
//		entity.put("articleTypeName", KitService.getArticleName(articleType));
		//获取 文章类型 列表
		List<Parameter> pList = pService.getByType(Constants.ARTICLE_TYPE);
		entity.put("pList", pList);
		return new ModelAndView("manage/articles-list", entity);
	}

	// 转到 增加文章 页面
	@RequestMapping(value = "/add/{articleType}", method = RequestMethod.GET)
	public ModelAndView articles_add_get(@PathVariable(value="articleType") String articleType, HttpServletRequest request) {
		log.debug("goto：增加文章");
		Map<String, Object> entity = new HashMap<String, Object>();
		// 各种参数
		List<Parameter> plist = pService.getByType(Constants.ARTICLE_TYPE);
		entity.put("pList", plist);
		//新增 文章类型
		entity.put("targetArticleType", articleType);
		return new ModelAndView("manage/articles-add",entity);
	}

	// 提交 增加文章
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> articles_add_post(Articles params, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(" 增加文章" + params);
		Map<String, Object> result = new HashMap<String, Object>();
		//获取当前登录用户所在地区
		String userId = CookieHelper.getCookieValue(request, Constants.ADMINUSERID);
		if(userId == null || "".equals(userId)){
			result.put(Constants.NOTICE, "用户为登陆, 请刷新页面登陆后重新尝试.");
			return result;
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		params.setArea(userObj.getArea());
		if(articlesService.save(params)){
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}

	//删除文章
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete_articles(@PathVariable(value = "id") String id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = articlesService.delete(id);
		if(bl){
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			entity.put(Constants.NOTICE, "删除文章出错, 请联系管理员.");
		}
		return entity;
	}
	
	// 转到 文章编辑 页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView articles_edit_get(@PathVariable(value = "id") String id,
			HttpServletRequest request) {
		log.debug("goto：编辑文章 ");
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Articles obj = articlesService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getByType(Constants.ARTICLE_TYPE);
		entity.put("pList", plist);
		return new ModelAndView("manage/articles-edit",entity);
	}
	
	// 提交文章编辑
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> articles_edit_post(Articles params, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("   更新文章" + params);
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = articlesService.update(params);
		if(bl){
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			entity.put(Constants.NOTICE, "更新文章出错, 请联系管理员.");
		}
		return entity;
	}
	
}
