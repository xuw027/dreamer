package com.aliyun.openapi.messagemanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessagePushRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessagePushResponse;

/**
 推送消费消息
 根据用户指定的ONS区域，让ONS将消息推送到指定的消费客户端上，因此需要指定推送的目标客户端
 信息。
 */
public class MessagePush {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsMessagePushRequest request = new OnsMessagePushRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setConsumerId(ConfigUtil.getConsumerId());
        //消费端实例的ID，类似于IP@端口号
        request.setClientId("169.254.133.23@9224");
        //这个必须有 要先查询出来
        request.setMsgId("707C8CC3298C055EAA4951EE663C04A6");
        request.setTopic(ConfigUtil.getTopic());
        try {
            OnsMessagePushResponse response = iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
