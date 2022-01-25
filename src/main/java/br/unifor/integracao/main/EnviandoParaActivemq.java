package br.unifor.integracao.main;

import br.unifor.integracao.producer.Producer;

public class EnviandoParaActivemq {

    public static void main(String[] args) {

	// ATRIBUTOS:
	String nPedido = "2015396";
	String nValor = "1200,00";

        Producer producer = new Producer(nPedido, nValor);

        Thread producerThread = new Thread(producer);
        producerThread.start();
 
    }
}