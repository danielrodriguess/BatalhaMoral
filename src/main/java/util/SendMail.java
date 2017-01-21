package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class SendMail {

	public void send(String to, String codigo,String link,String senha,String nome) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Recuperação de Senha - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "Segue abaixo o código para que você possa continuar com o processo: \n\n"
						  + "Código: " + codigo +"\n\n"
					  	  + "Acesse http://batalhamoral.herokuapp.com/usuario?acao=codigo&id="+link+"&hash="+senha);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendConfirmacao(String to, String nome) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Senha alterada - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "Sua senha foi alterada com sucesso.\n\n"
						  + "Att.\n\n");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendBemVindo(String to, String nome,String link) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Email de boas vindas - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "Acesse o link para confirmar o cadastro\n"+link+"\n"
						  + "Att.\n\n");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void perguntarespondida(String to, String nome,String candidato) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Pegunte ao candidato - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "A pergunta que você fez ao candidato(a) "+candidato+" foi respondida\n"
						  + "Acesse http://batalhamoral.herokuapp.com e confira\n"
						  + "Att.\n\n");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void perguntaeditada(String to, String nome,String candidato,String v) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Pegunte ao candidato - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "A resposta para sua pergunta ao candidato(a) "+candidato+" foi editada\n"
						  + "Acesse http://batalhamoral.herokuapp.com e confira\n"
						  + "Att.\n\n");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void perguntaexcluida(String to, String nome,String candidato) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("batalhamoral","batalhamoral2016");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("password@batalhamoral.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Pegunte ao candidato - Batalha Moral");
			message.setText("Olá "+nome+",\n\n"
						  + "A pergunta que você fez ao candidato(a) "+candidato+" foi excluída pelo moderador\n"
						  + "Lamentamos o ocorrido!\n"
						  + "Att.\n\n");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}