package main.bean;

import java.util.List;

/**
 * ÎÄ¼þ·¢ËÍBean
 * @author pc-zw
 *
 */
public class MailSendBean {

	private String userName;
	private String attachFileName;
	private String email;
	private String attchFilePath;
	private String dealStatus;
	private String errorMsg;
	private List<String> attchFilePathes;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAttachFileName() {
		return attachFileName;
	}
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAttchFilePath() {
		return attchFilePath;
	}
	public void setAttchFilePath(String attchFilePath) {
		this.attchFilePath = attchFilePath;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<String> getAttchFilePathes() {
		return attchFilePathes;
	}
	public void setAttchFilePathes(List<String> attchFilePathes) {
		this.attchFilePathes = attchFilePathes;
	}
	
}
