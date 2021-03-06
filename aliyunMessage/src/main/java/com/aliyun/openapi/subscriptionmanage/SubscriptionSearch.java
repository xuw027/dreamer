package com.aliyun.openapi.subscriptionmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsSubscriptionSearchRequest;
import com.aliyuncs.ons.model.v20160503.OnsSubscriptionSearchResponse;

import java.util.List;

/**
 * Created by xuw on 2016/5/16.
 */
public class SubscriptionSearch {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsSubscriptionSearchRequest request = new OnsSubscriptionSearchRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setSearch(ConfigUtil.getConsumerId());
        try {
            OnsSubscriptionSearchResponse response=iAcsClient.getAcsResponse(request);
            List<OnsSubscriptionSearchResponse.SubscribeInfoDo> subscribeInfoDoList=response.getData();
            for(OnsSubscriptionSearchResponse.SubscribeInfoDo subscribeInfoDo:subscribeInfoDoList){
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
