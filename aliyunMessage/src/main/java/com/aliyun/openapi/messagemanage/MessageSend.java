package com.aliyun.openapi.messagemanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessageSendRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessageSendResponse;

/**
 发送消息
 根据用户指定的ONS区域，发送一条消息，发消息时至少需要指定Topic、发送集群名PID以及消息体。
 发消息之前，发布关系和Topic必须存在，否则提示没有权限。
 */
public class MessageSend {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsMessageSendRequest request = new OnsMessageSendRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
                request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setProducerId(ConfigUtil.getProducerId());
        request.setMessage("Hello world".toString());
        try {
            OnsMessageSendResponse response=iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId()+"  "+response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
