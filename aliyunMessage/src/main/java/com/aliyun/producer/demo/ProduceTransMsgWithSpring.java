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
         * ������Ϣ������Bean������transactionProducer.xml��,��ͨ��ApplicationContext��ȡ����ֱ��ע�뵽������(��������Controller)��.
         * ��������"����������Ϣ"
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("transactionProducer.xml");

        TransactionProducer transactionProducer = (TransactionProducer) context.getBean("transactionProducer");
        Properties properties = ConfigUtil.getProps();
        Message msg = new Message(properties.getProperty("topic"), "TagA", "Hello ONS transaction===".getBytes());

        SendResult sendResult = transactionProducer.send(msg, new LocalTransactionExecuter() {
            public TransactionStatus execute(Message msg, Object arg) {
                System.out.println("executor local transaction");
                return TransactionStatus.CommitTransaction; //���ݱ�������ִ�н�������ز�ͬ��TransactionStatus
            }
        }, null);
    }
}
