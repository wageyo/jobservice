package esd.controller.manage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.SmsPhone;
import esd.bean.User;
import esd.common.PoiCreateExcel;
import esd.common.util.SmsPhoneUtil;
import esd.controller.Constants;
import esd.service.CookieHelper;
import esd.service.SMSService;
import esd.service.SmsPhoneService;
import esd.service.UserService;

/**
 * 短信发送 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-20
 */
@Controller
@RequestMapping("/manage/sms")
public class SMSManageController {
	private static Logger log = Logger.getLogger(SMSManageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SmsPhoneService smsPhoneService;

	@Autowired
	private SMSService smsService;
	
	@Autowired
	private SmsPhoneUtil smsPhoneUtil;

	// 转到 短信发送页面 页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView white_list(HttpServletRequest request) {
		log.debug("goto：短信发送页面");
		Map<String, Object> entity = new HashMap<>();
//		// 获取地区码
//		String userId = CookieHelper.getCookieValue(request,
//				Constants.ADMINUSERID);
//		if (userId == null || "".equals(userId)) {
//			return new ModelAndView("redirect:/loginManage/login");
//		}
//		Integer uid = Integer.parseInt(userId);
//		User userObj = userService.getById(uid);
//		String acode = userObj.getArea().getCode();
//		// 根据管理员的地区code, 获得本地区的所有发送过的电话列表(所有的, 不分页!)
//		SmsPhone paramEntity = new SmsPhone();
//		paramEntity.setArea(new Area(acode));
//		List<SmsPhone> smsPhoneList = smsPhoneService.getByPage(paramEntity,
//				Constants.START, Integer.MAX_VALUE);
//		entity.put("smsPhoneList", smsPhoneList);
		return new ModelAndView("manage/sms", entity);
	}

	// 得到本地区所有的电话号码
	@RequestMapping(value = "/getPhoneList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPhoneList(HttpServletRequest request) {
		log.debug("goto：post得到本地区所有的电话号码");
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			result.put(Constants.NOTICE, "管理员未登录, 请登陆后重新尝试.");
			return result;
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		String acode = userObj.getArea().getCode();
		List<SmsPhone> phoneList = smsPhoneService.getByArea(acode);
		result.put("phoneList", phoneList);
		result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		return result;
	}

	// 批量发送短信
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> white_add_post(HttpServletRequest request) {
		log.debug("goto：post批量发送短信");
		Map<String, Object> result = new HashMap<String, Object>();
		String[] phoneList = request.getParameterValues("phoneList"); // 电话号码列表
		String[] nameList = request.getParameterValues("nameList");// 名字列表
		String shortMessage = request.getParameter("shortMessage");// 短信内容
		// // 获取地区码
		// String userId = CookieHelper.getCookieValue(request,
		// Constants.ADMINUSERID);
		// if (userId == null || "".equals(userId)) {
		// result.put(Constants.NOTICE, "管理员未登录, 请登陆后重新尝试.");
		// return result;
		// }
		// Integer uid = Integer.parseInt(userId);
		// User userObj = userService.getById(uid);
		// String acode = userObj.getArea().getCode();
		// ①先将所有电话和名字保存到数据库中!!:smile:
		if (phoneList == null || nameList == null) {
			result.put(Constants.NOTICE, "传递的数据为空, 请重新尝试或联系管理员.");
			return result;
		}
		if (phoneList.length != nameList.length) {
			result.put(Constants.NOTICE, "传递电话号码和名字数量不一致, 请重新尝试或联系管理员.");
			return result;
		}
		// ②向这些电话发送短信,超过 80个电话号码, 则分次发送
		// 总字符数
		int total = phoneList.length;
		// 每次最多发送人数
		int size = 80;
		// 页码数
		int page = (total % size == 0) ? (total / size) : ((total / size) + 1);
		//非法字符集文件地址
		String url = request.getSession().getServletContext().getRealPath("/");
		Boolean mark = Boolean.TRUE;
		if (page >= 2) {
			for (int i = 1; i <= page; i++) {
				int beginIndex = (i - 1) * size; // 本段起始索引
				int endIndex = i * size - 1;// 本段结束索引
				if (endIndex >= total) {
					endIndex = total;
				}
				// 将截取的一段值赋给一个新的数组
				String[] paramPhoneList = new String[endIndex - beginIndex + 1];
				for (int k = 0; k < paramPhoneList.length; k++) {
					paramPhoneList[k] = phoneList[k + beginIndex];
				}
				Boolean bl = smsService.sendMessage(
						getRegularPhoneSentence(paramPhoneList), shortMessage,url);
				if (!bl) {
					mark = Boolean.FALSE;
				}
			}
		} else {
			Boolean bl = smsService.sendMessage(
					getRegularPhoneSentence(phoneList), shortMessage,url);
			if (!bl) {
				mark = Boolean.FALSE;
			}
		}
		if (mark) {
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			result.put(Constants.NOTICE, "发送过程中出现错误, 请联系管理员.");
		}
		return result;
	}

	// 删除电话号码
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request) {
		log.debug("goto：post批量发送短信");
		Map<String, Object> result = new HashMap<String, Object>();
		String phone = request.getParameter("phone"); // 电话号码
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			result.put(Constants.NOTICE, "管理员未登录, 请登陆后重新尝试.");
			return result;
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		String acode = userObj.getArea().getCode();
		// 查询对该电话对象
		SmsPhone smsPhone = smsPhoneService.getByPhoneAndArea(phone, acode);
		if (smsPhone == null) {
			result.put(Constants.NOTICE, "数据库中不存在该号码, 请重新尝试或联系管理员.");
			return result;
		}
		// 删除
		Boolean bl = smsPhoneService.delete(smsPhone.getId());
		if (bl) {
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}

	// 将电话号码拼接成 号码,号码,号码 的格式
	private String getRegularPhoneSentence(String[] phoneList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < phoneList.length; i++) {
			sb.append(phoneList[i]);
			if (i != (phoneList.length - 1)) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// 导入电话号码excel文件
	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST)
	public void importworker(@RequestParam("excel") CommonsMultipartFile excel,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		// ------------------------------------------------------------------//
		// 一. 将上传的excel, 放到指定的文件夹中 //
		// ------------------------------------------------------------------//
		// ① 初始化上传文件目录
		String url = request.getSession().getServletContext().getRealPath("/");
		// 上传的excel文件存放的目录
		String excelPath = url + "upload" +File.separator +"excel";
		// 缓存文件目录(比如提供下载的临时生成的excel文件)
		String tempPath = url + "upload" +File.separator +"temp";
		// 如果不存在以上 3个目录的话, 则进行创建
		File excelFolder = new File(excelPath); // upload下的 excel存放目录
		File tempFolder = new File(tempPath); // upload下的 生成的excel缓存目录
		if (!excelFolder.exists()) {
			excelFolder.mkdirs();
		}
		if (!tempFolder.exists()) {
			tempFolder.mkdirs();
		}
		// ② 上传的excel文件
		File excelFile = new File(excelPath + File.separator
				+ excel.getOriginalFilename());
		if (!excelFile.exists()) {
			excelFile.mkdir();
		}
		// ③ response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();

		// 将上传的excel放到 存放excelPath 路径的文件夹中
		try {
			excel.transferTo(excelFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			writer.write(Constants.NOTICE + ":"
					+ "上传并复制excel文件到服务器时发生错误,错误信息:" + e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
			writer.write(Constants.NOTICE + ":"
					+ "上传并复制excel文件到服务器时发生错误,错误信息:" + e.getMessage());
			return;
		}

		// ------------------------------------------------------------------//
		// 三. 文件上传成功，进入解析阶段 //
		// ------------------------------------------------------------------//

		// ①获取地区码,上传操作人等信息
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		String acode = userObj.getArea().getCode();

		// ② 读取上传的excel中的电话号码信息到list列表中
		List<SmsPhone> list = null; // 定义解析出来的电话号码list列表
		List<SmsPhone> wrongList = new ArrayList<SmsPhone>(); // 定义解析出来的格式不正确的电话号码list列表
		try {
			list = smsPhoneUtil.parse(excelFile, 0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			writer.write(Constants.NOTICE + ":"
			+ "解析excel表格式发生错误.");
			return;
		}
		if (list == null || list.size() <= 0) {
			// excel文件内部文本信息格式错误
			writer.write(Constants.NOTICE + ":"
					+ "excel文件内部文本信息格式错误,导致解析失败, 请检查文件内嗲话号码是否符合规范.");
			return;
		}
		
		// ③检查list集合中, 验证OK的存在数据库中, 不OK的则放入到错误列表中, 提供给下载.
		for (int i = 0; i < list.size(); i++) {
			SmsPhone item = list.get(i);
			if(item.getIsOk()){
				//检查该电话在该地区是否存在
				SmsPhone tmp = smsPhoneService.getByPhoneAndArea(item.getPhone(), acode);
				if(tmp!=null){
					item.setIsOk(Boolean.FALSE);
					item.setRemark("号码号码重复.");
					wrongList.add(item);
				}else{
					item.setArea(new Area(acode));
					item.setLogUser(userObj.getNickName());
					smsPhoneService.save(item);
				}
			}else{
				wrongList.add(item);
			}
		}

		// ④ 校验完毕后, 删除上传的excel文件
		if (excelFile.exists()) {
			excelFile.delete();
		}

		// ⑤ 校验完毕后, 从缓存表中读取到导入的数据总数, 正确条数, 错误条数
		Integer totalCount = list.size();
		Integer rightCount = list.size()-wrongList.size();
		Integer wrongCount = wrongList.size();

		// String notice = "excel文件数据校验成功, 共有残疾证号数据 " + totalCount + " 条,"
		// + "其中, 通过验证并可以导入的数据有 " + rightCount + " 条, "
		// + "错误数据有 " + wrongCount + " 条."
		// +"successEnd"

		String notice = totalCount + "@"  + rightCount + "*" + wrongCount +"successEnd";
		writer.write(Constants.Notice.SUCCESS.getValue() + ":" + notice);

		// ⑥ 如果有错误残疾职工信息的话, 则将其保存到缓存目录的excel文件中
		if (wrongCount != null && wrongCount > 0) {
			String uuid = UUID.randomUUID().toString().replace("-", ""); // 随机id,
																			// 防止重复
			String wrongPath = tempPath + File.separator+ uuid + ".xls"; // 错误错误列表 文件路径
			if (PoiCreateExcel.createSmsPhoneExcel(wrongPath, wrongList)) {
				// 项目在服务器上的远程绝对地址
				String serverAndProjectPath = request.getLocalAddr() + ":"
						+ request.getLocalPort() + request.getContextPath();
				// 文件所谓的远程绝对路径
				wrongPath = "http://" + serverAndProjectPath + "/upload/temp/"
						+ uuid + ".xls";
				writer.write("wrongPath:" + wrongPath);
			}
		}

	}
	
	
	
	/**
	 * 上传图片超出最大值时, 弹出的异常
	 * 
	 * @param ex
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void handlerException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf8");
		String notice = "error";
		if (ex instanceof MaxUploadSizeExceededException) {
			notice = "文件大小不超过"
					+ getFileMB(((MaxUploadSizeExceededException) ex)
							.getMaxUploadSize());
		} else {
			notice = "上传文件出现错误,错误信息:" + ex.getMessage();
		}
		PrintWriter writer = response.getWriter();
		writer.write(notice);
	}
	
	/**
	 * 字节转为MB 方法
	 * 
	 * @param byteFile
	 * @return
	 */
	private String getFileMB(long byteFile) {
		if (byteFile == 0) {
			return "0MB";
		}
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}
	
	
	
	
	// 将数组中的电话和名字对应保存起来
	// private Boolean savePhones(String[] phoneList, String[] nameList,String
	// acode){
	// for(int i=0;i<phoneList.length;i++){
	// //先查询该电话是否存在
	// String phone = phoneList[i];
	// String name = nameList[i];
	// SmsPhone resultSmsPhone = smsPhoneService.getByPhoneAndArea(phone,
	// acode);
	// //存在则更新姓名
	// if(resultSmsPhone != null){
	// resultSmsPhone.setName(name);
	// Boolean updateBl = smsPhoneService.update(resultSmsPhone);
	// if(!updateBl){
	// return Boolean.FALSE;
	// }else{
	// continue;
	// }
	// }else{
	// //不存在则保存此电话号码
	// resultSmsPhone = new SmsPhone();
	// resultSmsPhone.setPhone(phoneList[i]);
	// resultSmsPhone.setName(nameList[i]);
	// resultSmsPhone.setArea(new Area(acode));
	// Boolean saveBl = smsPhoneService.save(resultSmsPhone);
	// if(!saveBl){
	// return Boolean.FALSE;
	// }else{
	// continue;
	// }
	// }
	// }
	// //能走到这一步... 说明运行正常, 上面该更新的更新, 该保存的保存, 没有问题!
	// return Boolean.TRUE;
	// }

}
