package com.aliyun.producer.demo;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.utils.ConfigUtil;

import java.util.Properties;

public class ProducerDelayTest {
    public static void main(String[] args) {
        Properties properties = ConfigUtil.getProps();
        Producer producer = ONSFactory.createProducer(properties);
        // �ڷ�����Ϣǰ���������start����������Producer��ֻ�����һ�μ��ɡ�
        producer.start();
        Message msg = new Message( //
                // Message Topic
                properties.getProperty("topic"),
                // Message Tag,
                // �����ΪGmail�еı�ǩ������Ϣ�����ٹ��࣬����Consumerָ������������ONS����������
                "tag",
                // Message Body
                // �κζ�������ʽ�����ݣ� ONS�����κθ�Ԥ����ҪProducer��ConsumerЭ�̺�һ�µ����л��ͷ����л���ʽ
                "Hello ONS".getBytes());
        // ���ô�����Ϣ��ҵ��ؼ����ԣ��뾡����ȫ��Ψһ��
        // �Է��������޷������յ���Ϣ����£���ͨ��ONS Console��ѯ��Ϣ��������
        // ע�⣺������Ҳ����Ӱ����Ϣ�����շ�
        msg.setKey("ORDERID_100");
        // deliver time ��λ ms��ָ��һ��ʱ�̣������ʱ��֮����ܱ����ѣ�������ӱ�ʾ3s����ܱ�����
        long delayTime = 3000;
        msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        // ������Ϣ��ֻҪ�����쳣���ǳɹ�
        SendResult sendResult = producer.send(msg);
        System.out.println("msg id:" + sendResult.getMessageId());
        // ��Ӧ���˳�ǰ������Producer����<br>
        // ע�⣺���������Ҳû������
        producer.shutdown();
    }
}