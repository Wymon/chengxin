package xx.tream.chengxin.ms.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil2 {
	private String auth = "true";

	private String protocol = "smtp";

	private String host = "smtp.163.com";

	private int port = 25;
	private Properties prop;
	private List<String> fileList = null;
	private List<String> fileNameList = null;
	

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public EmailUtil2(String auth, String protocol, String host, int port) {
		this.auth = auth;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		init();
	}

	public EmailUtil2() {
		init();
	}

	private void init() {
		this.prop = new Properties();
		this.prop.put("mail.smtp.auth", this.auth);
		this.prop.put("mail.transport.protocol", this.protocol);
		this.prop.put("mail.smtp.host", this.host);
		this.prop.put("mail.smtp.port", Integer.valueOf(this.port));
	}

	

	public boolean sendEmailToCus(String from, String username,
			String password, String to, Date date, String subject, String text,
			String content) {
		try {
			Session session = Session.getInstance(this.prop);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSentDate(date);
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSubject(subject);
			Multipart multipart = new MimeMultipart();

			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(text);
			multipart.addBodyPart(contentPart);
			for (int index = 0; index < fileList.size(); index++) {
				MimeBodyPart mailArchieve = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileList.get(index));
				mailArchieve.setDataHandler(new DataHandler(fds));
				mailArchieve.setFileName(MimeUtility.encodeText(fileNameList.get(index),
						"GBK", "B"));
				multipart.addBodyPart(mailArchieve);
			}   
			//BodyPart messageBodyPart = new MimeBodyPart();
			//DataSource source = new FileDataSource(this.affix);

			//messageBodyPart.setDataHandler(new DataHandler(source));

			//BASE64Encoder enc = new BASE64Encoder();
			//messageBodyPart.setFileName("=?UTF-8?B?"
			//		+ enc.encode(this.affixName.getBytes()) + "?=");
		//	multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport tran = session.getTransport("smtp");

			tran.connect(this.host, username, password);
			tran.sendMessage(msg, msg.getAllRecipients());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean simpleSend(String to, String subject, String text) {
		return sendEmailToCus("xxchengxin@163.com", "xxchengxin",
				"haochengxin", to, new Date(), subject, text, null);
	}
}