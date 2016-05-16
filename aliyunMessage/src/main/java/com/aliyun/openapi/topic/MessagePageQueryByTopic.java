package com.aliyun.openapi.topic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessagePageQueryByTopicRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessagePageQueryByTopicResponse;

/**
 根据Topic查询消息
 根据Topic查询指定时间段内所有存在的消息，通过分页的形式返回给客户端，
 该接口仅限客户端1.1.1-SNAPSHOT版本及以上版本支持。
 首先，传入Topic，起止时间，以及每页的大小，进行分页查询，如果有消息，
 默认返回第一页的消息以及总页数和查询TaskId。
 根据返回的结果中的查询TaskID，进行后续取消息，取消息时传入TaskID以及当前页数
 */
public class MessagePageQueryByTopic {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsMessagePageQueryByTopicRequest request = new OnsMessagePageQueryByTopicRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setBeginTime(System.currentTimeMillis()-24*3600*1000);
        request.setEndTime(System.currentTimeMillis());
        request.setCurrentPage(1);
        request.setPageSize(20);
        try {
            OnsMessagePageQueryByTopicResponse response = iAcsClient.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
