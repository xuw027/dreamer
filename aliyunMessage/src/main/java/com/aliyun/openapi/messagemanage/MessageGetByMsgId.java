package com.aliyun.openapi.messagemanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessageGetByMsgIdRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessageGetByMsgIdResponse;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 根据MsgID查询消息
 根据MsgID查询消息状态
 */
public class MessageGetByMsgId {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsMessageGetByMsgIdRequest request = new OnsMessageGetByMsgIdRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setMsgId("707C8CC3298C055EAA4951EE663C04A6");
        request.setTopic(ConfigUtil.getTopic());
        try {
            OnsMessageGetByMsgIdResponse response = iAcsClient.getAcsResponse(request);
            OnsMessageGetByMsgIdResponse.Data data =response.getData();
            byte[] msgbody= Base64.decode(data.getBody());
            String message= new String(msgbody);
            System.out.println(data.getTopic()+"  "+
                    message+"  "+
                    data.getFlag()+" "+
                    data.getBornHost()+"  "+
                    data.getStoreSize()+"  "+
                    data.getStoreHost()+"  "+
                    data.getStoreTimestamp()+"  "+
                    data.getReconsumeTimes());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
    }
}
