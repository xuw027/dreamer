package com.aliyun.openapi.messagemanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessageTraceRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessageTraceResponse;

import java.util.List;

/**
 查询消息轨迹
 根据用户指定的MsgID以及Topic查询该消息的状态，主要是消费状态以及是否有异常
 */
public class MessageTrace {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsMessageTraceRequest request = new OnsMessageTraceRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setMsgId("707C8CC3298C055EAA4951EE663C04A6");
        try {
            OnsMessageTraceResponse response=iAcsClient.getAcsResponse(request);
            List<OnsMessageTraceResponse.MessageTrack> trackList =response.getData();
            for (OnsMessageTraceResponse.MessageTrack track:trackList){
                System.out.println(track.getConsumerGroup()+"  "+track.getTrackType()+"  "+track.getExceptionDesc());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
