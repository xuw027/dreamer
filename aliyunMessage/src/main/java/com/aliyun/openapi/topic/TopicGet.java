package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTopicGetRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicGetResponse;

import java.util.List;
import java.util.Properties;

/**
 根据用户指定的ONS区域，查询用户指定的Topic，类似于TopicList
 */
public class TopicGet {
    public static void main(String[] args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        Properties props= ConfigUtil.getProps();
        OnsTopicGetRequest request = new OnsTopicGetRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(props.getProperty("topic"));
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(props.getProperty("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        try {
            OnsTopicGetResponse response = iAcsClient.getAcsResponse(request);
            List<OnsTopicGetResponse.PublishInfoDo> publishInfoDoList=response.getData();
            for (OnsTopicGetResponse.PublishInfoDo publishInfoDo:publishInfoDoList){
                System.out.println(publishInfoDo.getId()+"  "+
                        publishInfoDo.getChannelId()+"  "+
                        publishInfoDo.getChannelName()+"  "+
                        publishInfoDo.getOnsRegionId()+"  "+
                        publishInfoDo.getRegionName()+"  "+
                        publishInfoDo.getTopic()+"  "+
                        publishInfoDo.getOwner()+"  "+
                        publishInfoDo.getRelation()+"  "+
                        publishInfoDo.getRelationName()+"  "+
                        publishInfoDo.getStatus()+"  "+
                        publishInfoDo.getStatusName()+"  "+
                        publishInfoDo.getAppkey()+"  "+
                        publishInfoDo.getCreateTime()+"  "+
                        publishInfoDo.getUpdateTime()+"  "+
                        publishInfoDo.getRemark());
            }
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
