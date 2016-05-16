package com.aliyun.openapi.consumermanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsConsumerAccumulateRequest;
import com.aliyuncs.ons.model.v20160503.OnsConsumerAccumulateResponse;

/**
 查询消费堆积
 根据用户指定的ONS区域，查询指定Consumer的堆积情况，包含统计信息等
 */
public class ConsumerAccumulate {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsConsumerAccumulateRequest request = new OnsConsumerAccumulateRequest();
        //      request.setCluster("taobaodaily");
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.get("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setDetail(true);
        request.setConsumerId(ConfigUtil.get("ConsumerId"));
        try {
            OnsConsumerAccumulateResponse response=iAcsClient.getAcsResponse(request);
            OnsConsumerAccumulateResponse.Data data =response.getData();
            System.out.println(data.getOnline()+"  "+data.getTotalDiff()+"  "+data.getConsumeTps()+"  "
                    +data.getDelayTime()+"  "+data.getLastTimestamp());
            for (OnsConsumerAccumulateResponse.Data.DetailInTopicDo detailInTopicDo:data.getDetailInTopicList()){
                System.out.println(detailInTopicDo.getTopic()+"  "
                        +detailInTopicDo.getTotalDiff()+"  "
                        +detailInTopicDo.getLastTimestamp()+"  "+detailInTopicDo.getDelayTime());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
