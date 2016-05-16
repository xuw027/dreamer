package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsRegionListRequest;
import com.aliyuncs.ons.model.v20160503.OnsRegionListResponse;
import com.aliyuncs.ons.model.v20160503.OnsTopicListRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicListResponse;

import java.util.List;
import java.util.Properties;

/**
 * 获取当前集群的Topic列表
 */
public class TopicList {
    public static void main(String[] args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsTopicListRequest request =new OnsTopicListRequest();

        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        Properties props= ConfigUtil.getProps();
        request.setOnsRegionId(props.getProperty("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        //request.setTopic("XXXXXXXXXXXXX");
        try {
            OnsTopicListResponse response=iAcsClient.getAcsResponse(request);
            List<OnsTopicListResponse.PublishInfoDo> publishInfoDoList=response.getData();
            for(OnsTopicListResponse.PublishInfoDo publishInfoDo:publishInfoDoList){
                System.out.println(publishInfoDo.getTopic()+"     "+ publishInfoDo.getOwner());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
