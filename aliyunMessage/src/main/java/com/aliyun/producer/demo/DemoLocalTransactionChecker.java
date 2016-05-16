package com.aliyun.producer.demo;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

public class DemoLocalTransactionChecker implements LocalTransactionChecker {
    public TransactionStatus check(Message msg) {
        System.out.println("begin check local transaction");
        return TransactionStatus.CommitTransaction; //���ݱ�������״̬��������ز�ͬ��TransactionStatus
    }
}