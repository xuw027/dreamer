package com.aliyun.producer.demo;


import com.alibaba.rocketmq.client.log.ClientLogger;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.aliyun.serviece.BusinessService;
import com.aliyun.utils.ConfigUtil;
import com.aliyun.utils.HashUtil;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TransactionProducerClient {
    private final static Logger log = ClientLogger.getLog(); // �û���Ҫ�����Լ���log, ��¼��־�����Ų�����

    public static void main(String[] args) throws InterruptedException {
        final BusinessService businessService = new BusinessService(); // ����ҵ��Service
        Properties properties = ConfigUtil.getProps();
        properties.put(PropertyKeyConst.ProducerId, properties.getProperty("ProducerId")); // ProducerId��Ҫ�������Լ���
        properties.put(PropertyKeyConst.AccessKey, properties.getProperty("AccessKey")); // AccessKey ��Ҫ�������Լ���
        properties.put(PropertyKeyConst.SecretKey, properties.getProperty("SecretKey")); // SecretKey ��Ҫ�������Լ���
        TransactionProducer producer = ONSFactory.createTransactionProducer(properties,
                new LocalTransactionCheckerImpl());
        producer.start();
        Message msg = new Message(properties.getProperty("topic"), "TagA", "Hello ONS transaction===".getBytes());
        // topic��Ҫ�������Լ���
        SendResult sendResult = producer.send(msg, new LocalTransactionExecuter() {

            public TransactionStatus execute(Message msg, Object arg) {
                // ��ϢID(�п�����Ϣ��һ��������Ϣid��һ��, ��ǰ��ϢID��console���Ʋ����ܲ�ѯ)
                String msgId = msg.getMsgID();
                // ��Ϣ�����ݽ���crc32, Ҳ����ʹ����������MD5
                long crc32Id = HashUtil.crc32Code(msg.getBody());
                // ��ϢID��crc32id��Ҫ��������ֹ��Ϣ�ظ�
                // ���ҵ�������ݵȵ�, ���Ժ���, ������Ҫ����msgId��crc32Id�����ݵ�
                // ���Ҫ����Ϣ���Բ��ظ�, �Ƽ������Ƕ���Ϣ��bodyʹ��crc32��md5����ֹ�ظ���Ϣ.
                Object businessServiceArgs = new Object();
                TransactionStatus transactionStatus = TransactionStatus.Unknow;
                try {
                    boolean isCommit =
                            businessService.execbusinessService(businessServiceArgs);
                    if (isCommit) {
                        // ��������ɹ����ύ��Ϣ
                        transactionStatus = TransactionStatus.CommitTransaction;
                    } else {
                        // ��������ʧ�ܡ��ع���Ϣ
                        transactionStatus = TransactionStatus.RollbackTransaction;
                    }
                } catch (Exception e) {
                    log.error("msgId:{}", msgId, e);
                }
                System.out.println(msg.getMsgID());
                log.warn("msgId:{}transactionStatus:{}", msgId, transactionStatus.name());
                return transactionStatus;
            }
        }, null);
        // demo example ��ֹ�����˳�(ʵ��ʹ�ò���Ҫ����)
        TimeUnit.MILLISECONDS.sleep(Integer.MAX_VALUE);
    }
}