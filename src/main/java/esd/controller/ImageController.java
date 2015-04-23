package esd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import esd.bean.Image;
import esd.bean.User;
import esd.common.util.FileUtil;
import esd.service.ImageService;
import esd.service.UserService;

/**
 * 图片处理controller
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-12-30
 */
@Controller
@RequestMapping("/image")
public class ImageController {

	private static Logger log = Logger.getLogger(ImageController.class);

	@Autowired
	private UserService<User> userService;

	@Autowired
	private ImageService imageService;

	// 接收上传的文件
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void importPic(@RequestParam("pic") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String imageid = request.getParameter("imageId");
		// ① response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (file == null) {
			writer.write(Constants.NOTICE + ":" + "网络发生错误, 上传照片失败.");
			return;
		}
		// 要更新的对象
		Image image = new Image();
		image.setImage(file.getBytes());
		String imageId;
		// 如果存在穿过来的id, 则说明是点了两次以上上传的, 则使用更新, 否则使用新增保存
		if (imageid != null && !"".equals(imageid)) {
			image.setId(imageid);
			if (imageService.update(image)) {
				imageId = imageid;
			} else {
				imageId = null;
			}
		} else {
			imageId = imageService.save(image);
			//保存完图片后, 将对应的图片/二进制文件 id保存到对应的关系表中
			String userid = request.getParameter("userid");
			if(userid != null &&!"".equals(userid)){
				User user = userService.getById(Integer.parseInt(userid));
				user.setHeadImage(imageId);
				userService.update(user);
			}
		}
		if (imageId != null) {
			writer.write(Constants.Notice.SUCCESS.getValue() + imageId);
		} else {
			writer.write(Constants.NOTICE + ":" + "上传图片失败");
		}
	}

	// 上传图片超出最大值时, 弹出的异常
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

	/**
	 * 序列化二进制文件到服务器指定文件夹
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/serialize")
	@ResponseBody
	public Map<String, Object> serializePicture(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询总共有多少图片
		int totalCount = imageService.getTotalCount(null);
		int success = 0, failure = 0; // 定义导入成功, 失败的数量
		// 图片采取分批导入到初始文件夹的方式, 防止数量过多, 内存溢出.
		int batchSize = 100; // 每批到100个
		int page = ((totalCount % batchSize) == 0) ? (totalCount / batchSize)
				: ((totalCount / batchSize) + 1);
		// 保存文件路径, 为根目录 file/images文件
		String filePath = request.getSession().getServletContext()
				.getRealPath("/");
		filePath += File.separator + "file";
		File dirFile = new File(filePath);
		//如果文件夹中有数据, 则先清空, 再导入
		new FileUtil().deleteFile(dirFile);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		for (int i = 0; i < page; i++) {
			Integer start = i * batchSize;
			List<Image> list = imageService.getByPage(null, start, batchSize);
			for (int j = 0; j < list.size(); j++) {
				Image image = list.get(j);
				String imagePath = filePath + File.separator + image.getId() + "." + image.getImageName();
				byte[] bs = imageService.getImageById(image.getId());
				Boolean bl = saveImageToLocalServer(imagePath, bs);
				if (!bl) {
					log.error("*************文件序列化本地出错,图片ID: " + image.getId()
							+ " *************");
					failure++;
				} else {
					success++;
				}
			}
		}
		String msg = "文件序列化本地完成,总图片数:" + totalCount + ", 成功: " + success
				+ ", 失败: " + failure;
		map.put(Constants.NOTICE, msg);
		log.error("**************" + msg + "*************");
		return map;
	}

	private Boolean saveImageToLocalServer(String filePath, byte[] bs) {
		try {
			// 输入流
			OutputStream os = new FileOutputStream(filePath);
			os.write(bs);
			os.close();
			return Boolean.TRUE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("************读取字节流到文件出错**************");
			return Boolean.FALSE;
		}
	}

	// 下载头像图片的方法
	@RequestMapping("/downloadFile/{id}")
	@ResponseBody
	public byte[] viewWorkerPicGet(@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		// response.addHeader("Content-Type", "image/gif");
		response.setContentType("image/gif");
		byte[] entity = imageService.getImageById(id);
		return entity;
	}
}
