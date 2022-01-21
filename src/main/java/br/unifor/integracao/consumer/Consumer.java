package br.unifor.integracao.consumer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {

	public void run() {
		boolean MsgComErro = false;
		String text = "";

		try { // Cria uma Connection Factory:
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

			// Cria uma conexão:
			Connection connection = factory.createConnection();

			// Inicia a conexão:
			connection.start();

			// Cria uma sessão:
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Cria uma fila:
			Destination queue = session.createQueue("Integracao");

			// Cria um consumidor:
			MessageConsumer consumer = session.createConsumer(queue);

			Message message = consumer.receive(1000);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				text = textMessage.getText();
				System.out.println("---------------------------------------------------");
				System.out.println("Ok!! DADOS RECEBIDOS COM SUCESSO: " + text);
				System.out.println("---------------------------------------------------");
			}

			// Fecha a sessão e a conexão:
			session.close();
			connection.close();
		} catch (Exception ex) {
			MsgComErro = true;
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Ocorreu um ERRO de recebimento dos dados!!!");
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		}

		// Verifica se existe dados recebidos do ActiveMQ:
		if (text.isEmpty()) {

			if (!MsgComErro) {
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				System.out.println("Não existe mensagem na fila do ActiveMQ!");
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			}

		}

		else {

			// Tratamento dos dados recebidos de ActiveMQ:
			String vetDados[] = text.split(";");
			String PedidoRec = vetDados[0];
			String ValorRec = vetDados[1];

			// Cálculo e Formatação da data de entrega:
			LocalDate dataAtual = LocalDate.now();
			LocalDate dataEntrega = dataAtual.plusDays(10); // yyyy/MM/dd
			String dataFormatada = dataEntrega.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			// Formatação do corpo do e-mail:
			String TextoEmail = "\nPrezado Cliente, " + "\nO pedido " + PedidoRec + " com o total de R$" + ValorRec
					+ " foi cadastrado com sucesso!!!" + "\nO prazo de entrega será até " + dataFormatada + ".";
			
			System.out.println("---------------------------------------------------");
			System.out.println("Dados formatados com sucesso!");
			System.out.println("---------------------------------------------------");

			// Enviando o email:
			if (AppEmail.EnviandoEmail(TextoEmail)) {
				System.out.println("---------------------------------------------------");
				System.out.println("Feito!!! Email enviado!");
				System.out.println("---------------------------------------------------");

			}

		}

	}

}