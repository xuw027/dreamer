package com.aliyun.openapi.publishmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsPublishDeleteRequest;
import com.aliyuncs.ons.model.v20160503.OnsPublishDeleteResponse;

/**
 删除发布信息
 根据Producer_ID和Topic删除创建过的发布关系，首先该发布关系需要存在。
 */
public class PublishDelete {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsPublishDeleteRequest request = new OnsPublishDeleteRequest();
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
            OnsPublishDeleteResponse response=iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
