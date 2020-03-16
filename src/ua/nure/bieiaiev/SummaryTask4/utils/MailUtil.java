package ua.nure.bieiaiev.SummaryTask4.utils;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

public class MailUtil  implements Serializable{

	private static final long serialVersionUID = 7249629220377997618L;

	private static final Logger LOG = Logger.getLogger(MailUtil.class);

	private static final String USERNAME = "testing.portal.sup@gmail.com";
	private static final String PASSWORD = "SummaryTask4";

	public static void sendMail(String email, String login, String firstName, String lastName)
			throws ApplicationException {
		Message msg = new MimeMessage(getSession());
		try {
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("Successfull registration on Testing Portal");
			String message = "Dear, " + firstName + " " + lastName
					+ ". You are successfully registered on Testing Portal with Login - " + login;
			msg.setText(message);
			Transport.send(msg);
		} catch (AddressException e) {
			LOG.error(Messages.ERR_CANNOT_FIND_SUCH_EMAIL_ADDRESS, e);
			throw new ApplicationException(Messages.ERR_CANNOT_FIND_SUCH_EMAIL_ADDRESS, e);
		} catch (MessagingException e) {
			LOG.error(Messages.ERR_CANNOT_SEND_EMAIL, e);
			throw new ApplicationException(Messages.ERR_CANNOT_SEND_EMAIL, e);
		}
	}

	private static Session getSession() {
		Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		return session;
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}
}
