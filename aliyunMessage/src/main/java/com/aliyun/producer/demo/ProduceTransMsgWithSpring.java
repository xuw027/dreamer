package com.aliyun.producer.demo;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.aliyun.utils.ConfigUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ProduceTransMsgWithSpring {
    public static void main(String[] args) {
        /**
         * 事务消息生产者Bean配置在transactionProducer.xml中,可通过ApplicationContext获取或者直接注入到其他类(比如具体的Controller)中.
         * 请结合例子"发送事务消息"
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("transactionProducer.xml");

        TransactionProducer transactionProducer = (TransactionProducer) context.getBean("transactionProducer");
        Properties properties = ConfigUtil.getProps();
        Message msg = new Message(properties.getProperty("topic"), "TagA", "Hello ONS transaction===".getBytes());

        SendResult sendResult = transactionProducer.send(msg, new LocalTransactionExecuter() {
            public TransactionStatus execute(Message msg, Object arg) {
                System.out.println("executor local transaction");
                return TransactionStatus.CommitTransaction; //根据本地事务执行结果来返回不同的TransactionStatus
            }
        }, null);
    }
}
