package br.unifor.integracao.consumer;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AppEmail {

	@SuppressWarnings("finally")
	public static boolean EnviandoEmail(String CorpoDaMsg) {

		String corpoEmail = CorpoDaMsg;
		boolean EmailEnviadoComSucesso = false;

		Properties props = new Properties();

		// Parâmetros de conexão com servidor Gmail:
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("germanomacieira@gmail.com", "T3r3$in#@4");
			}
		});

		// Ativa Debug para sessão:
		session.setDebug(true);
		

		try {

			Message message = new MimeMessage(session);
			// Remetente:
			message.setFrom(new InternetAddress("germanomacieira@gmail.com"));

			// Destinatário:
			Address[] toUser = InternetAddress.parse("germanomacieira@hotmail.com");

			message.setRecipients(Message.RecipientType.TO, toUser);

			// Assunto:
			message.setSubject("Pedido de Compras");
			message.setText(corpoEmail);
			System.out.println("Passou por aqui - linha 54");

			// Método para enviar a mensagem criada:
			Transport.send(message);
			System.out.println("Passou por aqui - linha 57");

			EmailEnviadoComSucesso = true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} finally {
			if (!EmailEnviadoComSucesso) {
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				System.out.println("O envio do email falhou! Tentar novamente! (Linha 67)");
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			}

			return (EmailEnviadoComSucesso);

		}

	}
}