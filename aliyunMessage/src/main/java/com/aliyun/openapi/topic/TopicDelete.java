package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ons.model.v20160503.OnsTopicDeleteRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicDeleteResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 根据用户指定的ONS区域，找到用户指定的Topic，删除。
 */
public class TopicDelete {
    public static void main(String[]args){

        final IAcsClient iAcsClient= ACSClient.getAcsClient();
        final Properties props= ConfigUtil.getProps();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        for (int i=200;i<500;i++){
             OnsTopicDeleteRequest request =new OnsTopicDeleteRequest();
            request.setCluster("taobaodaily");
            request.setPreventCache(System.currentTimeMillis());
            System.out.println("count:" + i);
            request.setOnsRegionId(props.getProperty("regionId"));
            request.setTopic("topic_" + sdf.format(new Date()) + i);
            try {
                OnsTopicDeleteResponse response = iAcsClient.getAcsResponse(request);
                System.out.println(response.getRequestId());
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
