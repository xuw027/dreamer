package com.aliyun.openapi.consumermanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsConsumerTimeSpanRequest;
import com.aliyuncs.ons.model.v20160503.OnsConsumerTimeSpanResponse;

/**
 查询消费进度
 根据用户指定的ONS区域，查询指定消费集群消费指定Topic的消费进度，
 主要是各个队列目前存储的消息的首尾信息，包含位点和时间，以及各个队列的最新消费时间
 */
public class ConsumerTimeSpan {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsConsumerTimeSpanRequest request = new OnsConsumerTimeSpanRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setConsumerId(ConfigUtil.getConsumerId());
        request.setTopic(ConfigUtil.getTopic());
        try {
            OnsConsumerTimeSpanResponse response=iAcsClient.getAcsResponse(request);
            OnsConsumerTimeSpanResponse.Data data =response.getData();
            System.out.println(data.getTopic()+"\n"+
                    data.getConsumeTimeStamp()+"\n"+
                    data.getMaxTimeStamp()+"\n"+
                    data.getMinTimeStamp());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
