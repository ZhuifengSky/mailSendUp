package main.controller;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.bean.MailSendBean;
import main.contant.MailConstant;
import main.model.MailAttachInfo;
import main.service.MailSendAttachService;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 邮件发送控制类
 * @author pc-zw
 *
 */
@Controller
@RequestMapping("/sendMail")
public class MailSendAttchController {

	@Autowired
	private MailSendAttachService mailService;
	
	@RequestMapping("/preView.do")
	public String previewResult(@RequestParam MultipartFile file, MailAttachInfo  mailAttachInfo,HttpServletRequest request, HttpServletResponse response,
			Model model){
		try {
			List<MailSendBean> sendBeans = mailService.getSendBeans(file,mailAttachInfo.getFindType(), mailAttachInfo.getAttachmentPath(),mailAttachInfo.getAttachmentSuffix());
			Gson gson = new Gson();
			String sendUserInfos = gson.toJson(sendBeans);
			model.addAttribute("sendBeans", sendBeans);
			model.addAttribute("subject", mailAttachInfo.getSubject());
			model.addAttribute("findType", mailAttachInfo.getFindType());
			model.addAttribute("content", mailAttachInfo.getContent());
			model.addAttribute("sendUserInfos", sendUserInfos);
		} catch (EncryptedDocumentException | FileNotFoundException
				| InvalidFormatException e) {
			e.printStackTrace();
			model.addAttribute("msg", "未找到指定文件!");
			return "/jsp/error.jsp";
		}
		return "/jsp/preview.jsp";		
	}
	
	
	@RequestMapping("/send.do")
	public String sendMail(MailAttachInfo  mailAttachInfo,HttpServletRequest request, HttpServletResponse response,
			Model model){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<MailSendBean>>(){}.getType();
		List<MailSendBean> sendBeans = gson.fromJson(mailAttachInfo.getSendUserInfoStr().trim(), listType);
		if (sendBeans!=null && sendBeans.size()>0) {
			List<MailSendBean> resultBeans = mailService.sendMail(mailAttachInfo.getSubject(), mailAttachInfo.getContent(),mailAttachInfo.getFindType(), sendBeans);
			if (resultBeans!=null && resultBeans.size()>0) {
				List<MailSendBean> successBeans = new ArrayList<MailSendBean>();
				List<MailSendBean> errorBeans = new ArrayList<MailSendBean>();
				for (MailSendBean mailSendBean : resultBeans) {
					if (mailSendBean.getDealStatus().equals(MailConstant.dealSuccess)) {
						successBeans.add(mailSendBean);
					}
					if (mailSendBean.getDealStatus().equals(MailConstant.dealFaild)) {
						errorBeans.add(mailSendBean);
					}
				}
				model.addAttribute("successBeans", successBeans);
				model.addAttribute("errorBeans", errorBeans);
				model.addAttribute("findType", mailAttachInfo.getFindType());
			}			
			return "/jsp/result.jsp";	
		}else{
			model.addAttribute("msg", "无可发送所需的用户信息!");
			return "/jsp/error.jsp";
		}
	
	}
}
