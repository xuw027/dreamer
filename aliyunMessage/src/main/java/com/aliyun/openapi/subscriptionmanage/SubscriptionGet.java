package com.aliyun.openapi.subscriptionmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsSubscriptionGetRequest;
import com.aliyuncs.ons.model.v20160503.OnsSubscriptionGetResponse;

import java.util.List;

/**
 * Created by xuw on 2016/5/16.
 */
public class SubscriptionGet {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsSubscriptionGetRequest request = new OnsSubscriptionGetRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setConsumerId(ConfigUtil.getConsumerId());
        try {
            OnsSubscriptionGetResponse response=iAcsClient.getAcsResponse(request);
            List<OnsSubscriptionGetResponse.SubscribeInfoDo> subscribeInfoDoList=response.getData();
            for(OnsSubscriptionGetResponse.SubscribeInfoDo subscribeInfoDo:subscribeInfoDoList){
                System.out.println(subscribeInfoDo.getId()+"  "+
                        subscribeInfoDo.getChannelId()+"  "+
                        subscribeInfoDo.getChannelName()+"  "+
                        subscribeInfoDo.getOnsRegionId()+"  "+
                        subscribeInfoDo.getRegionName()+"  "+
                        subscribeInfoDo.getOwner()+"  "+
                        subscribeInfoDo.getConsumerId()+"  "+
                        subscribeInfoDo.getTopic()+"  "+
                        subscribeInfoDo.getStatus()+"  "+
                        subscribeInfoDo.getStatusName()+" "+
                        subscribeInfoDo.getCreateTime()+"  "+
                        subscribeInfoDo.getUpdateTime());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
