package com.aliyun.producer.demo;


import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.utils.ConfigUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ProduceWithSpring {
    public static void main(String[] args) {
        /**
         * ������Bean������producer.xml��,��ͨ��ApplicationContext��ȡ����ֱ��ע�뵽������(��������Controller)��.
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
        Properties properties = ConfigUtil.getProps();
        Producer producer = (Producer) context.getBean("producer");
        //ѭ��������Ϣ
        for (int i = 0; i < 100; i++) {
            Message msg = new Message( //
                    // Message Topic
                    properties.getProperty("topic"),
                    // Message Tag,
                    // �����ΪGmail�еı�ǩ������Ϣ�����ٹ��࣬����Consumerָ������������ONS����������
                    "TagA",
                    // Message Body
                    // �κζ�������ʽ�����ݣ� ONS�����κθ�Ԥ��
                    // ��ҪProducer��ConsumerЭ�̺�һ�µ����л��ͷ����л���ʽ
                    "Hello ONS".getBytes());
            // ���ô�����Ϣ��ҵ��ؼ����ԣ��뾡����ȫ��Ψһ��
            // �Է��������޷������յ���Ϣ����£���ͨ��ONS Console��ѯ��Ϣ��������
            // ע�⣺������Ҳ����Ӱ����Ϣ�����շ�
            msg.setKey("ORDERID_100");
            // ������Ϣ��ֻҪ�����쳣���ǳɹ�
            try {
                SendResult sendResult = producer.send(msg);
                assert sendResult != null;
                System.out.println("send success: " + sendResult.getMessageId());
            }catch (ONSClientException e) {
                System.out.println("����ʧ��");
            }

        }
    }
}
