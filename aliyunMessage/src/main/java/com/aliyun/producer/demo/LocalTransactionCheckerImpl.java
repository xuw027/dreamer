package com.aliyun.producer.demo;

import com.alibaba.rocketmq.client.log.ClientLogger;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.aliyun.serviece.BusinessService;
import com.aliyun.utils.HashUtil;
import org.slf4j.Logger;

public class LocalTransactionCheckerImpl implements LocalTransactionChecker {
    private final static Logger log = ClientLogger.getLog();
    final BusinessService businessService = new BusinessService();

    public TransactionStatus check(Message msg) {
        //��ϢID(�п�����Ϣ��һ��������Ϣid��һ��, ��ǰ��ϢID��console���Ʋ����ܲ�ѯ)
        String msgId = msg.getMsgID();
        //��Ϣ�����ݽ���crc32, Ҳ����ʹ�������ķ�����MD5
        long crc32Id = HashUtil.crc32Code(msg.getBody());
        //��ϢID����Ϣ��body crc32Id��Ҫ��������ֹ��Ϣ�ظ�
        //���ҵ�������ݵȵ�, ���Ժ���, ������Ҫ����msgId��crc32Id�����ݵ�
        //���Ҫ����Ϣ���Բ��ظ�, �Ƽ������Ƕ���Ϣ��bodyʹ��crc32��md5����ֹ�ظ���Ϣ.
        //ҵ���Լ��Ĳ�������, ����ֻ��һ��ʾ��, ʵ����Ҫ�û��������������
        Object businessServiceArgs = new Object();
        TransactionStatus transactionStatus = TransactionStatus.Unknow;
        try {
            boolean isCommit = businessService.checkbusinessService(businessServiceArgs);
            if (isCommit) {
                //���������ѳɹ����ύ��Ϣ
                transactionStatus = TransactionStatus.CommitTransaction;
            } else {
                //����������ʧ�ܡ��ع���Ϣ
                transactionStatus = TransactionStatus.RollbackTransaction;
            }
        } catch (Exception e) {
            log.error("msgId:{}", msgId, e);
        }
        log.warn("msgId:{}transactionStatus:{}", msgId, transactionStatus.name());
        return transactionStatus;
    }
}