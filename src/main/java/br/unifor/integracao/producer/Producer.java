package br.unifor.integracao.producer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer implements Runnable {

    //ATRIBUTOS:
    String numPedido;
    String numValor;

    public Producer(String pPedido, String pValor) {
        numPedido = pPedido;
        numValor = pValor;
    }

    public void run() {
        try { // Cria uma Connection Factory:
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            //Cria a Conexão:
            Connection connection = factory.createConnection();

            // Inicia a conexão:
            connection.start();

            // Cria uma sessão:
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Cria uma fila:
            Destination queue = session.createQueue("Integracao");

            // Cria um produtor:
            MessageProducer producer = session.createProducer(queue);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String msg = numPedido + "; " + numValor;

            // Insere uma mensagem:
            TextMessage message = session.createTextMessage(msg);
            System.out.println("==============================================");
            System.out.println("DADOS ENVIADOS COM SUCESSO: " + msg);
            System.out.println("==============================================");
            producer.send(message);

            // Fecha a sessão e a conexão:
            session.close();
            connection.close();
        }
        catch (Exception ex) {
        	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println("ERRO gerado no envio dos dados!!");
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
    }
}