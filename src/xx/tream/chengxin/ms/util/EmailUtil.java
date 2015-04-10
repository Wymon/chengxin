package xx.tream.chengxin.ms.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil
{
  private String auth = "true";

  private String protocol = "smtp";

  private String host = "smtp.163.com";

  private int port = 25;
  private Properties prop;

  public EmailUtil(String auth, String protocol, String host, int port)
  {
    this.auth = auth;
    this.protocol = protocol;
    this.host = host;
    this.port = port;
    init();
  }

  public EmailUtil()
  {
    init();
  }

  private void init()
  {
    this.prop = new Properties();
    this.prop.put("mail.smtp.auth", this.auth);
    this.prop.put("mail.transport.protocol", this.protocol);
    this.prop.put("mail.smtp.host", this.host);
    this.prop.put("mail.smtp.port", Integer.valueOf(this.port));
  }

  public boolean sendEmailToCus(String from, String username, String password, String to, Date date, String subject, String text, String content)
  {
    try
    {
      Session session = Session.getInstance(this.prop);
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(from));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      msg.setSentDate(date);
      msg.setSubject(subject);
      msg.setText(text);
      msg.setSubject(subject);

      if (content != null) {
        msg.setContent(text, content);
      }

      Transport tran = session.getTransport("smtp");

      tran.connect(this.host, username, password);
      tran.sendMessage(msg, msg.getAllRecipients());
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  public boolean simpleSend(String to, String subject, String text)
  {
    return sendEmailToCus("xxchengxin@163.com", "xxchengxin", "haochengxin", to, new Date(), subject, text, null);
  }
}