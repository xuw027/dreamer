package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTopicSearchRequest;
import com.aliyuncs.ons.model.v20160503.OnsTopicSearchResponse;

import java.util.List;
import java.util.Properties;

/**
 * 在用户指定的ONS区域，搜索符合条件的Topic信息并返回。
 */
public class TopicGet {
    public static void main(String[] args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        Properties props= ConfigUtil.getProps();
        OnsTopicSearchRequest request = new OnsTopicSearchRequest();
        request.setAcceptFormat(FormatType.JSON);
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(props.getProperty("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setSearch(props.getProperty("topic"));
        try {
            OnsTopicSearchResponse response = iAcsClient.getAcsResponse(request);
            List<OnsTopicSearchResponse.PublishInfoDo> publishInfoDoList=response.getData();
            for(OnsTopicSearchResponse.PublishInfoDo publishInfoDo:publishInfoDoList){
                System.out.println(publishInfoDo.getTopic()+"    "+publishInfoDo.getOwner());
            }
            System.out.println(response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
