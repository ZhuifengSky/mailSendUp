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
 * AutoConfirmUtil Description: ���ݵ��������˵������Զ����� Company: www.edu24ol.com
 * 
 * @author pc-zw
 * @date 2015��12��3������5:37:38
 * @version 1.0
 */
public class ReadExcelXSSFUtil {

	private static Logger log;
	/** ���� */
	private static int rowsNum = 0;

	/** ���� */
	private static int columnNum = 0;

	//����Excel�ļ���ȡ
	public static List<MailSendBean> getSendInfoList(InputStream is,String attchFilePath,String attachmentSuffix) throws EncryptedDocumentException, InvalidFormatException {				
		try {
			//�õ�����������
			Workbook workbook = WorkbookFactory.create(is);
			//�õ���һ��Sheet  
	        Sheet sheet = workbook.getSheetAt(0);   
	        // ��ȡSheet��������
	     	rowsNum = sheet.getLastRowNum();;
	     	// ��ȡ����
	     	columnNum = getColumnNum(sheet, 1, 1);
	     	Row row = sheet.getRow(0);						
			//ָ��Ҫ��ȡ����--��ȡ���н���Ϊ��װ���������
			Map<String, Integer> readColumns = new HashMap<String, Integer>();
			readColumns.put(MailConstant.userName, 0);
			readColumns.put(MailConstant.attachFileName, 1);
			readColumns.put(MailConstant.email, 2);				
			//ָ�����ڸ�ʽ�Լ���ʼ��ȡ������
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
	 * ��ȡָ��Sheet����
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
	 * ������ȡ��Ԫ�������
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		// �հ�
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		// ����
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		// ����
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		// ��ֵ
		case Cell.CELL_TYPE_NUMERIC:
			//�ж��Ƿ��Ǳ�׼���ڸ�ʽ�������Զ������ڸ�ʽ
			if (HSSFDateUtil.isCellDateFormatted(cell) || cell.getCellStyle().getDataFormat()==179) {
            		SimpleDateFormat sdf = new SimpleDateFormat(
            				MailConstant.minusyMdHms);            		
					ret = sdf.format(cell.getDateCellValue()); 
			}else{
					ret = cell.getNumericCellValue() + "";
			}            	             
            break;    
		// �ַ���
		case Cell.CELL_TYPE_STRING:
			ret = cell.getStringCellValue();
			break;
		// Ĭ��
		default:
			ret = null;
		}
		return ret;
	}

	/**
	 * ��ָ�����п�ʼ��ȡָ���в���װ����
	 * @param sheet       sheet
	 * @param startRow    ��ʼ����
	 * @param startColumn ��ʼ����
	 * @param totalRows   ������
	 * @param columnNum   ������
	 * @param readColumns Ҫ��ȡ��������keyֵ��Ҫ���װ��������һ��
	 * @return
	 */
	private static List<MailSendBean> readSetColumn(Sheet sheet,
			int startRow, int startColumn, int totalRows, int columnNum,
			Map<String, Integer> readColumns,String attachFilePath,String attachmentSuffix) {
		List<MailSendBean> mailSendBeans = new ArrayList<MailSendBean>();
		try {
			// �Ƿ�����������
			boolean flag = true;
			// ��ʼѭ����
			for (int i = startRow; i <= totalRows; i++) {
				MailSendBean mailSendBean = new MailSendBean();
				if (!flag) {
					break;
				}
				Row row = sheet.getRow(i);
				if (row == null || row.getCell((short) 0)==null) {// ����ʱ����ѭ��
					break;
				}
				// ��ʼ��ȡָ����
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
