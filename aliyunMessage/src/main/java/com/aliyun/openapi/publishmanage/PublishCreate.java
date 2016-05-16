package com.aliyun.openapi.publishmanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsPublishCreateRequest;
import com.aliyuncs.ons.model.v20160503.OnsPublishCreateResponse;

/**
 注册发布信息
 根据用户指定的ONS区域，注册发布信息，需要指定Topic和PID，因此，需要首先创建Topic，否则提示没有权限。多次调用会抛出已存在发布关系的异常。
 */
public class PublishCreate {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsPublishCreateRequest request = new OnsPublishCreateRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setProducerId("PID_"+Math.round(Math.random()*10000));
        try {
            OnsPublishCreateResponse response=iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
