package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTopicCreateRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicCreateResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 根据用户指定的ONS区域，创建Topic，一个用户下面的topic不可以重复,
 * 重复申请会抛出服务器内部异常，在用户端显示为unknown error。
 */
public class TopicCreate {
    public static void main(String[] args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Properties props= ConfigUtil.getProps();
        for (int i=100;i<1000;i++)
        {
            OnsTopicCreateRequest request = new OnsTopicCreateRequest();
            request.setAcceptFormat(FormatType.JSON);
            request.setTopic("topic_"+sdf.format(new Date())+i);
            request.setQps(1000l);
            request.setRemark("topic_"+i);
            request.setStatus(0);
            /**
             *ONSRegionId是指你需要API访问ONS哪个区域的资源。
             *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
             */
            request.setOnsRegionId(props.getProperty("regionId"));
            request.setCluster("taobaodaily");
            request.setPreventCache(System.currentTimeMillis());
            try {
                OnsTopicCreateResponse response = iAcsClient.getAcsResponse(request);
                System.out.println(response.getRequestId());
            }
            catch (ServerException e) {
                e.printStackTrace();
            }
            catch (ClientException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
