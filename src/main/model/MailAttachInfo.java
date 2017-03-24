package main.model;

/**
 * ∑¢ÀÕ–≈œ¢
 * @author pc-zw
 *
 */
public class MailAttachInfo {

	private String subject;
	private String emailInfoPath;
	private String attachmentPath;
	private String attachmentSuffix;
	private String content;
	private String sendUserInfoStr;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailInfoPath() {
		return emailInfoPath;
	}
	public void setEmailInfoPath(String emailInfoPath) {
		this.emailInfoPath = emailInfoPath;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachmentSuffix() {
		return attachmentSuffix;
	}
	public void setAttachmentSuffix(String attachmentSuffix) {
		this.attachmentSuffix = attachmentSuffix;
	}
	public String getSendUserInfoStr() {
		return sendUserInfoStr;
	}
	public void setSendUserInfoStr(String sendUserInfoStr) {
		this.sendUserInfoStr = sendUserInfoStr;
	}
	
	
}
