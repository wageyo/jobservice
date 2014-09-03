package esd.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zhangjianzong
 * 
 */
public class PaginationUtil {

	private int prefixSize = 4; // 本页前缀页数
	private int suffixSize = 4; // 本页后缀页数
	private int records = 0;// 总条数
	private int countPages = 0;// 总页数
	private int currentPage = 0;// 当前选中页

	@Override
	public String toString() {
		return "PaginationUtil [prefixSize=" + prefixSize + ", suffixSize="
				+ suffixSize + ", records=" + records + ", countPages="
				+ countPages + ", currentPage=" + currentPage + "]";
	}

	public PaginationUtil(int currentPage, int records, String status) {
		this.records = records;
		this.currentPage = currentPage;
	}

	public PaginationUtil(int currentPage, int records) {
		this.records = records;
		this.currentPage = currentPage;
	}

	private int getPages() {
		int pages = records / Constants.SIZE;
		if (records % Constants.SIZE != 0) {
			pages++;
		}
		return pages;
	}

	private int getStart() {
		if (currentPage > prefixSize) {
			return currentPage - prefixSize;
		}
		return 1;
	}

	private int getEnd() {
		if ((countPages - currentPage) > suffixSize) {
			return currentPage + suffixSize;
		} else {
			return countPages;
		}
	}

	public Map<String, Object> getHandler() {
		countPages = getPages();
		int start = getStart();
		int end = getEnd();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start == 0 ? 1 : start);
		map.put("end", end == 0 ? 1 : end);
		map.put("countPages", countPages == 0 ? 1 : countPages);// 总页数
		map.put("currentPage", currentPage == 0 ? 1 : currentPage);// 当前选择页数
		map.put("records", this.records);
		return map;
	}

}
