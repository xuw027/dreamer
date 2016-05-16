package com.aliyun.openapi.publishmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsPublishGetRequest;
import com.aliyuncs.ons.model.v20160503.OnsPublishGetResponse;

import java.util.List;

/**
 查询发布信息
 根据Producer_ID和Topic查询发布信息
 */
public class PublishGet {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsPublishGetRequest request = new OnsPublishGetRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setProducerId(ConfigUtil.getProducerId());
        try {
            OnsPublishGetResponse response=iAcsClient.getAcsResponse(request);
            List<OnsPublishGetResponse.PublishInfoDo> publishInfoDoList =response.getData();
            for (OnsPublishGetResponse.PublishInfoDo publishInfoDo:publishInfoDoList){
                System.out.println(publishInfoDo.getId()+"  "+
                        publishInfoDo.getChannelId()+"  "+
                        publishInfoDo.getChannelName()+"  "+
                        publishInfoDo.getOnsRegionId()+"  "+
                        publishInfoDo.getRegionName()+"  "+
                        publishInfoDo.getOwner()+"  "+
                        publishInfoDo.getProducerId()+"  "+
                        publishInfoDo.getTopic()+"  "+
                        publishInfoDo.getStatus()+"  "+
                        publishInfoDo.getStatusName()+"  "+
                        publishInfoDo.getCreateTime()+"  "+
                        publishInfoDo.getUpdateTime());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
