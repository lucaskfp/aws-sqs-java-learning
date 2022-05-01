package com.kennedy;

import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SqsClient sqsClient = Client.getClient();

        // SqsService.createQueue(sqsClient, "fila-02");

        // SqsService.listQueues(sqsClient);

        // SqsService.getQueueUrl(sqsClient, "fila-02");

        // SqsService.deleteQueue(sqsClient,"https://sqs.us-east-1.amazonaws.com/195365165857/fila-02");

        // SqsService.sendOnlyMessage(sqsClient,
        // "https://sqs.us-east-1.amazonaws.com/195365165857/fila-1",
        // "Msg 01");

        SqsService.receiveMessages(sqsClient, "https://sqs.us-east-1.amazonaws.com/195365165857/fila-1");

        sqsClient.close();
    }
}
