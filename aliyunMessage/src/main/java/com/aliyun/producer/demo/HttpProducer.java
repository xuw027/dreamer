package com.aliyun.producer.demo;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import com.aliyun.utils.ConfigUtil;
import com.aliyun.utils.MD5;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import com.aliyun.openservices.ons.api.impl.authority.AuthUtil;

public class HttpProducer {
    public static String SIGNATURE="Signature";
    public static String NUM="num";

    public static String PRODUCERID="ProducerId";

    public static String TIMEOUT="timeout";
    public static String TOPIC="topic";
    public static String AK="AccessKey";
    public static String BODY="body";
    public static String MSGHANDLE="msgHandle";
    public static String TIME="time";
    public static void main(String[] args) throws Exception {

        HttpClient httpClient=new HttpClient();
        httpClient.setMaxConnectionsPerDestination(1);
        httpClient.start();
        Properties properties = ConfigUtil.getProps();
        String topic=properties.getProperty("topic"); //请在user.properties配置您的topic
        String url=properties.getProperty("url");//公测集群配置为http://publictest-rest.ons.aliyun.com/
        //MQ的HTTP正式环境近期会上线，敬请期待
        String ak=properties.getProperty("AccessKey");//请在user.properties配置您的accesskey
        String sk=properties.getProperty("SecretKey");//请在user.properties配置您的secretkey
        String pid=properties.getProperty("ProducerId");//请在user.properties配置您的producerId
        String date=String.valueOf(new Date().getTime());
        String sign=null;
        String body="hello ons http";
        String NEWLINE="\n";
        String signString;
        for (int i = 0; i < 10; i++) {
            date=String.valueOf(new Date().getTime());
            Request req=httpClient.POST(url+"message/?topic="+topic+"&time="+date+"&tag=http"+"&key=http");
            ContentProvider content=new StringContentProvider(body);
            req.content(content);
            signString=topic+NEWLINE+pid+NEWLINE+ MD5.getInstance().getMD5String(body)+NEWLINE+date;
            System.out.println(signString);
            sign=AuthUtil.calSignature(signString.getBytes(Charset.forName("UTF-8")), sk);
            req.header(SIGNATURE, sign);
            req.header(AK, ak);
            req.header(PRODUCERID, pid);
            ContentResponse response;
            response=req.send();
            System.out.println("send msg:"+response.getStatus()+response.getContentAsString());
        }
    }
}
