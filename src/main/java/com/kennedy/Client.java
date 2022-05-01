package com.kennedy;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

public class Client {

    public static SqsClient getClient() {
        return SqsClient.builder().region(Region.US_EAST_1).build();
    }

}
