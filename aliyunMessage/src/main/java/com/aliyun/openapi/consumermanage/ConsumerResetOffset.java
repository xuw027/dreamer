package com.aliyun.openapi.consumermanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsConsumerResetOffsetRequest;
import com.aliyuncs.ons.model.v20160503.OnsConsumerResetOffsetResponse;

/**
 重置消费位点
 根据用户指定的订阅组，将当前的订阅组消费位点重置到指定时间戳。一般用于清理堆积消息，
 或者回溯消费。有2种方式，一种是清理所有消息，一种是清理消费进度到指定的时间。
 */
public class ConsumerResetOffset {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsConsumerResetOffsetRequest request = new OnsConsumerResetOffsetRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，
         * 不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.get("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setConsumerId(ConfigUtil.get("ConsumerId"));
        request.setTopic(ConfigUtil.get("topic"));
        //设置0代表清除所有消息，设置1代表清理到指定时间
        request.setType(1);
        request.setResetTimestamp(System.currentTimeMillis());
        try {
            OnsConsumerResetOffsetResponse response=iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
