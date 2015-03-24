/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */package esd.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 解析 excel 97-2003
 * 
 * @author zhangjianzong
 * 
 */
public class HExcelSheetParser {
	// log4j
	private Logger logger = Logger.getLogger(HExcelSheetParser.class);

	private HSSFWorkbook workbook;// 工作簿

	public HExcelSheetParser(File file) {
		try {
			// 获取工作薄workbook
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<List<Object>> getDatasInSheet(int sheetNumber) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		// 获得指定的sheet
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet总行数
		int rowCount = sheet.getLastRowNum();
		logger.info("found excel rows count:" + rowCount);
		if (rowCount < 1) {
			return result;
		}
		// 遍历行row
		for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);
			if (null != row) {
				List<Object> rowData = new ArrayList<Object>();
				// 获得本行中单元格的个数
				int cellCount = row.getLastCellNum();
				// 遍历列cell
				for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
					HSSFCell cell = row.getCell(cellIndex);
					// 获得指定单元格中的数据
					Object cellStr = this.getCellString(cell);

					rowData.add(cellStr);
				}
				result.add(rowData);
			}
		}

		return result;
	}

	private Object getCellString(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		Object result = null;
		if (cell != null) {
			// 单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
			int cellType = cell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				}
				result =  df.format(cell.getNumericCellValue());
//				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			default:
				logger.info("枚举了所有类型");
				break;
			}
		}
		return result;
	}

	
	public String getCell(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}
}
