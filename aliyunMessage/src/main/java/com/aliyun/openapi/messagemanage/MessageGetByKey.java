package com.aliyun.openapi.messagemanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsMessageGetByKeyRequest;
import com.aliyuncs.ons.model.v20160503.OnsMessageGetByKeyResponse;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.util.List;

/**
 根据Key查询消息
 根据MsgKey和Topic查询消息，由于业务方的key可能不唯一,所以查询结果可能为多条
 */
public class MessageGetByKey {
    public static void main(String []args) {
        IAcsClient iAcsClient = ACSClient.getAcsClient();
        OnsMessageGetByKeyRequest request = new OnsMessageGetByKeyRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setKey("aa");
        try {
            OnsMessageGetByKeyResponse response = iAcsClient.getAcsResponse(request);
            List<OnsMessageGetByKeyResponse.OnsRestMessageDo> onsRestMessageDoList = response.getData();
            for (OnsMessageGetByKeyResponse.OnsRestMessageDo onsRestMessageDo : onsRestMessageDoList) {
                byte[] messageBody = Base64.decode(onsRestMessageDo.getBody());
                String message = new String(messageBody);
                System.out.println(onsRestMessageDo.getTopic() + "  " + message + "  " +
                        onsRestMessageDo.getFlag() + " " +
                        onsRestMessageDo.getBornHost() + "  " +
                        onsRestMessageDo.getStoreSize() + "  " +
                        onsRestMessageDo.getStoreHost() + "  " +
                        onsRestMessageDo.getStoreTimestamp() + "  " +
                        onsRestMessageDo.getReconsumeTimes());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
    }
}
