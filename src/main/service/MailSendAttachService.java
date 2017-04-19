package main.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import main.bean.MailSendBean;
import main.contant.MailConstant;
import main.util.ReadExcelXSSFUtil;
import main.util.SendMailHasAttachUtil;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MailSendAttachService {

	/**
	 * ��ȡ����Bean
	 * @param recieverInfoFilePath  �ռ�����ϢExcel�ļ�·��
	 * @param attachFilePath        ����Ŀ¼����Ŀ¼
	 * @return
	 * @throws FileNotFoundException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public List<MailSendBean> getSendBeans(MultipartFile file,String findType,String attachFilePath,String attachmentSuffix) throws FileNotFoundException, EncryptedDocumentException, InvalidFormatException{
		InputStream is;
		try {
			is = file.getInputStream();
			List<MailSendBean> sendBeans = ReadExcelXSSFUtil.getSendInfoList(is,findType, attachFilePath,attachmentSuffix);
			return sendBeans;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	
	/**
	 * �ʼ�����
	 * @param subject
	 * @param content
	 * @param sendBeans
	 * @return
	 */
	public List<MailSendBean> sendMail(String subject,String content,String findType,List<MailSendBean> sendBeans){
		SendMailHasAttachUtil se = new SendMailHasAttachUtil(false);  			 
		if (sendBeans!=null && sendBeans.size()>0) {
			 int count = 0;
			for (MailSendBean mailSendBean : sendBeans) {
				 String userContent = content;
				 userContent = content.replaceAll("��������",mailSendBean.getUserName());
				 userContent = userContent.replaceAll("�����䡿",mailSendBean.getEmail());
				 try {
					 if (findType.equals("sigle")) {
						 if(se.doSendHtmlEmail(subject, userContent, mailSendBean.getAttchFilePath(), mailSendBean.getEmail().trim())){
								mailSendBean.setDealStatus(MailConstant.dealSuccess);
						 }else{
								 mailSendBean.setDealStatus(MailConstant.dealFaild);
								 mailSendBean.setErrorMsg("����ʱ�쳣,δ�ҵ�ָ���ļ�!");
						 }	
					}else{
						if(se.doSendHtmlManyAttachEmail(subject, userContent, mailSendBean.getAttchFilePathes(), mailSendBean.getEmail().trim())){
							mailSendBean.setDealStatus(MailConstant.dealSuccess);
					    }else{
							 mailSendBean.setDealStatus(MailConstant.dealFaild);
							 mailSendBean.setErrorMsg("δ�ҵ���ƥ���ļ�!");
					    }	
					}
					count++;
					if (count>=100) {
						Thread.sleep(10000);
						count=0;
					}else{
						continue;
					} 
				 } catch (IOException e5) {
					 mailSendBean.setDealStatus(MailConstant.dealFaild);
					 mailSendBean.setErrorMsg("����ʱ�쳣,δ�ҵ�ָ���ļ�!+"+e5.getMessage());
					 e5.printStackTrace();
				 }catch (AddressException e) {
					 mailSendBean.setDealStatus(MailConstant.dealFaild);
					 mailSendBean.setErrorMsg("�������ʼ�������Ϣ���ô���!"+e.getMessage());
				}catch (AuthenticationFailedException e2) {
					 mailSendBean.setDealStatus(MailConstant.dealFaild);
					 mailSendBean.setErrorMsg("��������������У�����!"+e2.getMessage());
				} catch (MessagingException e4) {
					 mailSendBean.setDealStatus(MailConstant.dealFaild);
					 mailSendBean.setErrorMsg("����ʱ�쳣!+"+e4.getMessage());
					 e4.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			}
		}
		return sendBeans;		
	}
}
