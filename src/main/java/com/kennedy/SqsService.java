package com.kennedy;

import javax.xml.transform.stream.StreamSource;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteQueueResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SqsService {

    // Criar fila
    public static void createQueue(SqsClient sqsClient, String queueName) {
        try {
            CreateQueueRequest request = CreateQueueRequest.builder().queueName(queueName).build();

            CreateQueueResponse result = sqsClient.createQueue(request);

            System.out.println(
                    "Status code = " + result.sdkHttpResponse().statusCode() + "\nQueue url = " + result.queueUrl());
        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }

    // Listar filas
    public static void listQueues(SqsClient sqsClient) {
        try {
            ListQueuesRequest request = ListQueuesRequest.builder().build();

            ListQueuesResponse result = sqsClient.listQueues(request);

            for (String q : result.queueUrls()) {
                System.out.println(q);
            }

        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }

    // Buscar a url de uma fila pelo nome
    public static void getQueueUrl(SqsClient sqsClient, String queueName) {
        try {
            GetQueueUrlRequest request = GetQueueUrlRequest.builder().queueName(queueName).build();

            GetQueueUrlResponse result = sqsClient.getQueueUrl(request);

            System.out.println("Status code => " + result.sdkHttpResponse().statusCode());
            System.out.println("Queue url => " + result.queueUrl());
        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }

    // Deletar uma fila
    public static void deleteQueue(SqsClient sqsClient, String queueUrl) {
        try {
            DeleteQueueRequest request = DeleteQueueRequest.builder().queueUrl(queueUrl).build();

            DeleteQueueResponse result = sqsClient.deleteQueue(request);

            System.out.println("Status code => " + result.sdkHttpResponse().statusCode());
        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }

    // Envia uma mensagem
    public static void sendOnlyMessage(SqsClient sqsClient, String queueUrl, String messageBody) {
        try {

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();

            SendMessageResponse result = sqsClient.sendMessage(request);

            System.out.println("Status code => " + result.sdkHttpResponse().statusCode());
            System.out.println("Message ID => " + result.messageId());
            System.out.println("Hash => " + result.md5OfMessageBody());

        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }

    public static void receiveMessages(SqsClient sqsClient, String queueUrl) {
        try {
            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .waitTimeSeconds(20)
                    .build();

            System.out.println("Aguardando mensagens");
            ReceiveMessageResponse result = sqsClient.receiveMessage(request);

            for (Message msg : result.messages()) {
                System.out.println(msg.body());
            }

        } catch (SqsException e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println("Error " + e.awsErrorDetails().errorMessage());
        }
    }
}
