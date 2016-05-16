package com.aliyun.openapi.consumermanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsConsumerStatusRequest;
import com.aliyuncs.ons.model.v20160503.OnsConsumerStatusResponse;

import java.util.List;

/**
 查询消费状态
 根据用户指定的ONS区域，查询指定消费集群的各个节点的消费状态，包含订阅关系信息，
 消费状态统计等信息。
 */
public class ConsumerStatus {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsConsumerStatusRequest request = new OnsConsumerStatusRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.get("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setConsumerId(ConfigUtil.get("ConsumerId"));
        request.setDetail(true);
        request.setNeedJstack(false);
        try {
            OnsConsumerStatusResponse response=iAcsClient.getAcsResponse(request);
            OnsConsumerStatusResponse.Data data =response.getData();
            List<OnsConsumerStatusResponse.Data.ConnectionDo> connectionDoList = data.getConnectionSet();
            List<OnsConsumerStatusResponse.Data.DetailInTopicDo> detailInTopicDoList =data.getDetailInTopicList();
            List<OnsConsumerStatusResponse.Data.ConsumerConnectionInfoDo> consumerConnectionInfoDoList =data.getConsumerConnectionInfoList();
            System.out.print(data.getOnline()+"  "+data.getTotalDiff()+"  "+data.getConsumeTps()+"  "+
                    data.getLastTimestamp()+"  "+data.getDelayTime()+"  "+data.getConsumeModel()+
                    "  "+data.getSubscriptionSame()+"  "+data.getRebalanceOK());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
