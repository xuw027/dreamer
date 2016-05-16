package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTopicStatusRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicStatusResponse;

/**
 查询指定topic的消息状态
 根据用户指定的ONS区域，查询用户所拥有的所有Topic的状态，包括当前的Topic上存在的消息数量，
 更新时间等信息。
 */
public class TopicStatusDetail {
    public static void main(String[] args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsTopicStatusRequest request = new OnsTopicStatusRequest();
        request.setAcceptFormat(FormatType.JSON);
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.get("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setTopic(ConfigUtil.get("topic"));
        try {
            OnsTopicStatusResponse response = iAcsClient.getAcsResponse(request);
            OnsTopicStatusResponse.Data data=response.getData();
            Long totalCount =data.getTotalCount();
            Long lastTimeStamp =data.getLastTimeStamp();
            System.out.println(response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
