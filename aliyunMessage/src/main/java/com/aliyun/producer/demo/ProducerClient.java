package com.aliyun.producer.demo;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.utils.ConfigUtil;

public class ProducerClient {
    public static void main(String[] args) {
        Properties properties = ConfigUtil.getProps();
        Producer producer = ONSFactory.createProducer(properties);
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //�ڷ�����Ϣǰ���������start����������Producer��ֻ�����һ�μ��ɡ�
        producer.start();
        int i=0;
        while (i<10)
        {
            Message msg = new Message(
                    //Message Topic
                    properties.getProperty("topic"),
                    //Message Tag,
                    //�����ΪGmail�еı�ǩ������Ϣ�����ٹ��࣬����Consumerָ������������ONS����������
                    "TagA",
                    //Message Body
                    //�κζ�������ʽ�����ݣ�ONS�����κθ�Ԥ����ҪProducer��ConsumerЭ�̺�һ�µ����л��ͷ����л���ʽ
                    ("msg:"+fmt.format(new Date())).getBytes()
            );

            // ���ô�����Ϣ��ҵ��ؼ����ԣ��뾡����ȫ��Ψһ��
            // �Է��������޷������յ���Ϣ����£���ͨ��ONS Console��ѯ��Ϣ��������
            // ע�⣺������Ҳ����Ӱ����Ϣ�����շ�
            msg.setKey("ORDERID_"+System.nanoTime());

            //������Ϣ��ֻҪ�����쳣���ǳɹ�
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
            i++;
        }

        // ��Ӧ���˳�ǰ������Producer����
        // ע�⣺���������Ҳû������
        producer.shutdown();
    }
}
