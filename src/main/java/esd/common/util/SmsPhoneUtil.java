/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package esd.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import esd.bean.SmsPhone;

@Service
public class SmsPhoneUtil {
	private static final Logger logger = Logger.getLogger(SmsPhoneUtil.class
			.getName());

	private static HSSFWorkbook hWorkbook;// excel 2003之前的版本
	private static XSSFWorkbook xWorkbook;// excel 2007-2012

	/**
	 * 检测excel版本:解析为两个版本2003以前用HSSFWorkbook,2007开始用XSSFWorkbook.
	 * 银行：银行一般都是用2003以前的版本解析也就是HSSFWorkbook,在上传银行批处理时尽量用office2003 否则可能会失败
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Object hasParser(File file) throws FileNotFoundException,
			IOException {
		try {
			// 获取工作薄workbook
			FileInputStream fis = new FileInputStream(file);
			hWorkbook = new HSSFWorkbook(fis);
			logger.info("excel 97-2003");
			return hWorkbook;
		} catch (Exception e) {
			xWorkbook = new XSSFWorkbook(new FileInputStream(file));
			logger.info("excel 2007-2010");
			return xWorkbook;
		}
	}
	
	
	public static void main(String[] args) {
		String  regEx = "^[1][3|4|5|7|8][0-9]{9}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher("138");  
		System.out.println(matcher.matches());  
		
		
	}
	
	/**
	 * 检测电话号码是否符合电话规则
	 * @param phone
	 * @return
	 */
	private Boolean checkMobile(String phone){
		String  regEx = "^[1][3|4|5|7|8][0-9]{9}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(phone);  
		return matcher.matches();  
	}

	/**
	 * 解析成实体
	 * 
	 * @param row
	 * @return
	 * @throws ParseException
	 */
	public SmsPhone parseRow(List<Object> row) throws ParseException {
		SmsPhone combinedEntity = new SmsPhone();
		combinedEntity.setUpdateDate(new Date());
		for (int j = 0; j < row.size(); j++) {
			Object obj = row.get(j);
			String value = String.valueOf(obj).trim();
			// logger.info("共有 " + row.size() + "列, 当前第 " + (j + 1)
			// + "列, value = " + value);
			switch (j) {
			case 0:
				// 电话号码
				if (value != null && !"".equals(value) && !"null".equals(value)) {
					combinedEntity.setPhone(value);
					if(checkMobile(value)){
						combinedEntity.setIsOk(Boolean.TRUE);
					}else{
						combinedEntity.setIsOk(Boolean.FALSE);
						combinedEntity.setRemark("号码格式不正确, 请检查后重新导入.");
					}
				}
				break;
			case 1:
				// 姓名
				if (value != null && !"".equals(value) && !"null".equals(value)) {
					combinedEntity.setName(value);
				}
				break;
			}
		}
		logger.info("组装后的combinedEntity：" + combinedEntity.toString());
		return combinedEntity;
	}

	/**
	 * 解析成集合
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public List<SmsPhone> parse(File file, int sheetNumber)
			throws FileNotFoundException, IOException, ParseException {
		List<SmsPhone> list = null;
		// 判断excel版本
		Object obj = hasParser(file);
		if (obj == null) {
			return list;
		}
		list = new ArrayList<SmsPhone>();

		if (obj instanceof HSSFWorkbook) {// 97-2003
			logger.debug("excelFileType:{}" + "97-2003");

			HExcelSheetParser parser = new HExcelSheetParser(file);
			List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
			for (int i = 0; i < datas.size(); i++) {
				List<Object> row = datas.get(i);
				list.add(parseRow(row));
			}
		} else if (obj instanceof XSSFWorkbook) { // 2003-2010
			logger.debug("excelFileType:{}" + " 2003-2010");
			XExcelSheetParser parser = new XExcelSheetParser(file);
			List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
			for (int i = 0; i < datas.size(); i++) {
				List<Object> row = datas.get(i);
				list.add(parseRow(row));
			}
		}
		return list;
	}

}
