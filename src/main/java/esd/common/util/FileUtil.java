package esd.common.util;

import java.io.File;

/**
 * 文件操作类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-21
 */
public class FileUtil {

	/**
	 * 删除文件或文件夹和其下面的所有文件
	 * @param file
	 */
	public void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			System.out.println("所删除的文件不存在！" + '\n');
		}
	}
}
