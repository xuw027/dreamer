package com.aliyun.consumer.demo;
import com.aliyun.openservices.ons.api.*;
import com.aliyun.utils.ConfigUtil;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ConsumerClient {
    public static void main(String[] args) {
        Properties properties = ConfigUtil.getProps();
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(properties.getProperty("topic"), "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                try {
                    System.out.println("body:" + new String(message.getBody(),"UTF-8")+",Receive: " + message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}