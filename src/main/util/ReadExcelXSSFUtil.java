package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.bean.MailSendBean;
import main.contant.MailConstant;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * AutoConfirmUtil Description: 根据第三方对账单进行自动对账 Company: www.edu24ol.com
 * 
 * @author pc-zw
 * @date 2015年12月3日下午5:37:38
 * @version 1.0
 */
public class ReadExcelXSSFUtil {

	private static Logger log;
	/** 行数 */
	private static int rowsNum = 0;

	/** 列数 */
	private static int columnNum = 0;

	//进行Excel文件读取
	public static List<MailSendBean> getSendInfoList(InputStream is,String attchFilePath,String attachmentSuffix) throws EncryptedDocumentException, InvalidFormatException {				
		try {
			//得到工作簿对象
			Workbook workbook = WorkbookFactory.create(is);
			//拿到第一个Sheet  
	        Sheet sheet = workbook.getSheetAt(0);   
	        // 获取Sheet的总行数
	     	rowsNum = sheet.getLastRowNum();;
	     	// 获取列数
	     	columnNum = getColumnNum(sheet, 1, 1);
	     	Row row = sheet.getRow(0);						
			//指定要读取的列--读取的列将作为封装对象的属性
			Map<String, Integer> readColumns = new HashMap<String, Integer>();
			readColumns.put(MailConstant.userName, 0);
			readColumns.put(MailConstant.attachFileName, 1);
			readColumns.put(MailConstant.email, 2);				
			//指定日期格式以及开始读取的行数
			return readSetColumn(sheet, 1, 1, rowsNum, columnNum, readColumns,attchFilePath,attachmentSuffix);
		} catch (IOException e) {
			log.error("read Excel Throws Exception!");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error("fileinputStream close Throws Exception!");
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取指定Sheet列数
	 * 
	 * @param sheet
	 * @param startRow
	 * @param startColumn
	 * @return
	 */
	private static int getColumnNum(Sheet sheet, int startRow,
			int startColumn) {
		int columnNum = 0;
		Row row = null;
		if (startRow == 0) {
			row = sheet.getRow(0);
		} else {
			row = sheet.getRow(startRow - 1);
		}
		for (int i = startColumn;; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				columnNum = i;
				break;
			} else {
				if (("").equals(cell.toString())) {
					columnNum = i;
					break;
				}
			}
		}
		return columnNum;
	}

	/**
	 * 处理并读取单元格的内容
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		// 空白
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		// 布尔
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		// 错误
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		// 数值
		case Cell.CELL_TYPE_NUMERIC:
			//判断是否是标准日期格式或者是自定义日期格式
			if (HSSFDateUtil.isCellDateFormatted(cell) || cell.getCellStyle().getDataFormat()==179) {
            		SimpleDateFormat sdf = new SimpleDateFormat(
            				MailConstant.minusyMdHms);            		
					ret = sdf.format(cell.getDateCellValue()); 
			}else{
					ret = cell.getNumericCellValue() + "";
			}            	             
            break;    
		// 字符串
		case Cell.CELL_TYPE_STRING:
			ret = cell.getStringCellValue();
			break;
		// 默认
		default:
			ret = null;
		}
		return ret;
	}

	/**
	 * 从指定行列开始读取指定列并组装对象
	 * @param sheet       sheet
	 * @param startRow    开始行数
	 * @param startColumn 开始列数
	 * @param totalRows   总行数
	 * @param columnNum   总列数
	 * @param readColumns 要读取的列其中key值需要与封装对象属性一致
	 * @return
	 */
	private static List<MailSendBean> readSetColumn(Sheet sheet,
			int startRow, int startColumn, int totalRows, int columnNum,
			Map<String, Integer> readColumns,String attachFilePath,String attachmentSuffix) {
		List<MailSendBean> mailSendBeans = new ArrayList<MailSendBean>();
		try {
			// 是否继续解析标记
			boolean flag = true;
			// 开始循环行
			for (int i = startRow; i <= totalRows; i++) {
				MailSendBean mailSendBean = new MailSendBean();
				if (!flag) {
					break;
				}
				Row row = sheet.getRow(i);
				if (row == null || row.getCell((short) 0)==null) {// 空行时跳出循环
					break;
				}
				// 开始读取指定列
				for (String columnsName : readColumns.keySet()) {
					String cellValue = null;
					int readColumn = readColumns.get(columnsName);
					Cell cell = row.getCell(readColumn);
					if (cell == null) {
						break;
					} else {
						cellValue = getCellValue(cell);
						if (cellValue==null || cellValue.equals("")) {
							break;
						}
					}
					if (columnsName.equals(MailConstant.userName)) {						
						mailSendBean.setUserName(cellValue.trim());
					} else if (columnsName.equals(MailConstant.attachFileName)) {
						mailSendBean.setAttachFileName(cellValue.trim());
						mailSendBean.setAttchFilePath(attachFilePath+File.separatorChar+cellValue.trim()+"."+attachmentSuffix);
					} else if (columnsName.equals(MailConstant.email)) {						
						mailSendBean.setEmail(cellValue.trim());
					}
				}
				if (mailSendBean.getUserName()!=null && !mailSendBean.getUserName().equals("")) {
					mailSendBeans.add(mailSendBean);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailSendBeans;
	}
}
