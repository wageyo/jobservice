package esd.controller;

import java.io.IOException;
import java.io.PrintWriter;

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

	// 接收上传的的文章中的图片
	@RequestMapping(value = "/uploadNewsPic", method = RequestMethod.POST)
	public void importPic(@RequestParam("pic") CommonsMultipartFile pic,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String imageid = request.getParameter("imageId");
		// ① response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (pic == null) {
			writer.write(Constants.NOTICE + ":" + "网络发生错误, 上传照片失败.");
			return;
		}
		// 要更新的对象
		Image image = new Image();
		image.setImage(pic.getBytes());
		String imageId;
		//如果存在穿过来的id, 则说明是点了两次以上上传的, 则使用更新, 否则使用新增保存
		if (imageid != null && !"".equals(imageid)) {
			image.setId(imageid);
			if (imageService.update(image)) {
				imageId = imageid;
			} else {
				imageId = null;
			}
		} else {
			imageId = imageService.save(image);
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

	// 下载头像图片的方法
	@RequestMapping("/downloadPic/{id}")
	@ResponseBody
	public byte[] viewWorkerPicGet(@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		// response.addHeader("Content-Type", "image/gif");
		response.setContentType("image/gif");
		byte[] entity = imageService.getImageById(id);
		return entity;
	}

}
