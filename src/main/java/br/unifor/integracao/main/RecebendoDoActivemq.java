package br.unifor.integracao.main;

import br.unifor.integracao.consumer.Consumer;

public class RecebendoDoActivemq {

    public static void main(String[] args) {

        Consumer consumer = new Consumer();

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }
}