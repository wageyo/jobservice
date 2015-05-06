package esd.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Filegags;
import esd.common.util.FileUtil;
import esd.controller.Constants;
import esd.dao.FilegagsDao;

/**
 * 图片操作类
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-12-30
 */
@Service
public class FilegagsService {
	private static Logger log = Logger.getLogger(FilegagsService.class);
	@Autowired
	private FilegagsDao dao;

	/**
	 * 保存二进制文件
	 * @param t
	 * @return
	 */
	public String save(Filegags t) {
		String uuid = KitService.getUUID();
		t.setId(uuid);
		return dao.save(t) ? uuid : null;
	}
	
	
	

	// 删除一个对象
	public boolean deleteId(String id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(Filegags image) {
		image.setUpdateCheck(dao.getUpdateCheck(image.getId()));
		return dao.update(image);
	}

	/**
	 * 按id查询一个对象,不带二进制的文件哦
	 * 
	 * @param id
	 * @return
	 */
	public Filegags getById(String id) {
		return (Filegags) dao.getById(id);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<Filegags> getByPage(Filegags image, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filegags", image);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		return dao.getByPage(map);
	}

	/**
	 * 分页查询方法--得到总数,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public Integer getTotalCount(Filegags image) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", image);
		return dao.getTotalCount(map);
	}

	/**
	 * 根据id获得单一二进制文件
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getFileById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		HashMap<String, Object> resultMap = dao.getFilegagsById(id);
		byte[] image = (byte[]) resultMap.get("file");
		return image;
	}

	/**
	 * 根据id整个文件gags对象, 带文件的哦 .
	 * 
	 * @param id
	 * @return
	 */
	public Filegags getImageByIdWithFile(String id) {
		Filegags resultMap = dao.getFilegagsByIdWithFile(id);
		return resultMap;
	}

	/**
	 * 将所有二进制文件序列到本地服务器上
	 * 
	 * @param destFile
	 *            应该为 项目/uploadfile/ 目录
	 * @return
	 */
	public String serialize(String basePath) {
		// 查询总共有多少图片
		int totalCount = getTotalCount(null);
		int success = 0, failure = 0; // 定义导入成功, 失败的数量
		// 图片采取分批导入到初始文件夹的方式, 防止数量过多, 内存溢出.
		int batchSize = 100; // 每批到100个
		int page = ((totalCount % batchSize) == 0) ? (totalCount / batchSize)
				: ((totalCount / batchSize) + 1);
		// 保存文件路径, 为根目录/uploadfile/文件夹下
		basePath += File.separator + "uploadfile";
		File dirFile = new File(basePath);
		// 如果文件夹中有数据, 则先清空, 再导入
		new FileUtil().deleteFile(dirFile);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// 循环批次导入
		for (int i = 0; i < page; i++) {
			Integer start = i * batchSize;
			List<Filegags> list = getByPage(null, start, batchSize);
			for (int j = 0; j < list.size(); j++) {
				Filegags image = list.get(j);
				String imagePath = basePath + File.separator + image.getId()
						+ "." + image.getFileSuffix();
				byte[] bs = getFileById(image.getId());
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
		return totalCount + ":" + success + ":" + failure;
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

}
