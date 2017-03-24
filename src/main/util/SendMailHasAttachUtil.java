package main.util;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SendMailHasAttachUtil {

    private MimeMessage message;
    private Session session;
    private Transport transport;
    private String mailHost="";
    private int mailPort;
    private String sender_username="";
    private String sender_password="";
    private String smtp_auth = "";
    private String nickName = "";
    private Vector file;
    private Properties properties = new Properties();
   
    /*
     * ��ʼ������
     */
    public SendMailHasAttachUtil(boolean debug) {
        InputStream in = SendMailHasAttachUtil.class.getResourceAsStream("/resource/mailConfig.properties");
        try {
            properties.load(in);
            this.mailHost = properties.getProperty("mail.smtp.host");
            this.mailPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
            this.sender_username = properties.getProperty("mail.sender.username");
            this.sender_password = properties.getProperty("mail.sender.password");
            this.smtp_auth = properties.getProperty("mail.smtp.auth");
            this.nickName = properties.getProperty("nickName");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Authenticator auth = new MyAuthenticator(sender_username, sender_password);
        session = Session.getDefaultInstance(properties, auth);
        session.setDebug(debug);//�������е�����Ϣ
        message = new MimeMessage(session);
    }

    /**
     * �����ʼ�
     *@param �������ǳ�
     * @param subject
     *            �ʼ�����
     * @param sendHtml
     *            �ʼ�����
     * @param receiveUser
     *            �ռ��˵�ַ
     * @throws UnsupportedEncodingException 
     * @throws MessagingException 
     */
    public boolean doSendHtmlEmail(String subject, String sendHtml,String attachFilePath,String receiveEmail) throws UnsupportedEncodingException, MessagingException {
            // ������
            //InternetAddress from = new InternetAddress(sender_username);
            // ������������÷����˵�Nick name
            InternetAddress from= new InternetAddress(MimeUtility.encodeWord(nickName)+" <"+sender_username+">");
            message.setFrom(from);
            // �ռ���
            InternetAddress to = new InternetAddress(receiveEmail);
            message.setRecipient(Message.RecipientType.TO, to);//��������CC��BCC
            // ������
            //message.setRecipient(Message.RecipientType.CC, new InternetAddress("13752381963@163.com"));
            // ������
            //message.setRecipient(Message.RecipientType.BCC, new InternetAddress("zhangwu@yy.com"));
            // �ʼ�����
            message.setSubject(subject);
            String content = sendHtml.toString();
            // �ʼ�����,Ҳ����ʹ���ı�"text/plain"
            message.setContent(content, "text/html;charset=UTF-8");
            file = new Vector();
            file.addElement(attachFilePath);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(content.toString(), "text/html;charset=gb2312");
            mp.addBodyPart(mbp);
            if(!file.isEmpty()){//�и���
                Enumeration efile=file.elements();
                while(efile.hasMoreElements()){
                    mbp=new MimeBodyPart();
                    attachFilePath=efile.nextElement().toString(); //ѡ���ÿһ��������
                    FileDataSource fds=new FileDataSource(attachFilePath); //�õ�����Դ
                    mbp.setDataHandler(new DataHandler(fds)); //�õ�������������BodyPart
                    mbp.setFileName(fds.getName());  //�õ��ļ���ͬ������BodyPart
                    mp.addBodyPart(mbp);
                }
                file.removeAllElements();
                message.setContent(mp); //Multipart���뵽�ż�
                message.setSentDate(new Date());     //�����ż�ͷ�ķ�������
                // �����ʼ�
                message.saveChanges();
                transport = session.getTransport("smtp");
                // smtp��֤���������������ʼ��������û�������
                transport.connect(mailHost,mailPort, sender_username, sender_password);
                // ����
                transport.sendMessage(message, message.getAllRecipients());
                return true;
	        }else{
	            	return false;
	        }         
   }

}
