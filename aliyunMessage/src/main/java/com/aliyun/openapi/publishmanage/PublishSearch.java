package com.aliyun.openapi.publishmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsPublishSearchRequest;
import com.aliyuncs.ons.model.v20160503.OnsPublishSearchResponse;

import java.util.List;

/**
 搜索发布信息
 根据关键词搜索当前用户所拥有的发布关系
 */
public class PublishSearch {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsPublishSearchRequest request = new OnsPublishSearchRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setSearch(ConfigUtil.getProducerId());
        try {
            OnsPublishSearchResponse response=iAcsClient.getAcsResponse(request);
            List<OnsPublishSearchResponse.PublishInfoDo> publishInfoDoList =response.getData();
            for (OnsPublishSearchResponse.PublishInfoDo publishInfoDo:publishInfoDoList){
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
