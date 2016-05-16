package com.aliyun.producer.demo;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.utils.ConfigUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Created by xuw on 2016/5/14.
 */
public class ProducerSpecialDelayTest {
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
                "Hello ProducerSpecialDelayTest".getBytes());
        // ���ô�����Ϣ��ҵ��ؼ����ԣ��뾡����ȫ��Ψһ��
        // �Է��������޷������յ���Ϣ����£���ͨ��ONS Console��ѯ��Ϣ��������
        // ע�⣺������Ҳ����Ӱ����Ϣ�����շ�
        msg.setKey("ORDERID_110");
        /**
         * ��ʱ��ϢͶ�ݣ�����Ͷ�ݵľ���ʱ�������λ��������2016-03-07 16:21:00Ͷ��
         */
        long timeStamp = 0;
        try {
            timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-05-15 11:12:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        msg.setStartDeliverTime(timeStamp);

        // ������Ϣ��ֻҪ�����쳣���ǳɹ�
        SendResult sendResult = producer.send(msg);
        System.out.println("msg id:" + sendResult.getMessageId());
        // ��Ӧ���˳�ǰ������Producer����<br>
        // ע�⣺���������Ҳû������
        producer.shutdown();
    }
}
