package com.aliyun.openapi.consumermanage;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsConsumerGetConnectionRequest;
import com.aliyuncs.ons.model.v20160503.OnsConsumerGetConnectionResponse;

import java.util.List;

/**
 查询Consumer连接
 根据用户指定的ONS区域，查询指定Consumer集群的连接信息，如果该集群没有上线，则会抛出BIZ_CONSUMER_NOT_ONLINE异常
 */
public class ConsumerGetConnection {
    public static void main(String []args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsConsumerGetConnectionRequest request = new OnsConsumerGetConnectionRequest();
        /**
         *ONSRegionId是指你需要API访问ONS哪个区域的资源。
         *该值必须要根据OnsRegionList方法获取的列表来选择和配置，因为OnsRegionId是变动的，不能够写固定值
         */
        request.setOnsRegionId(ConfigUtil.get("regionId"));
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setConsumerId(ConfigUtil.get("ConsumerId"));
        try {
            OnsConsumerGetConnectionResponse response=iAcsClient.getAcsResponse(request);
            List<OnsConsumerGetConnectionResponse.Data.ConnectionDo> connectionDoList=response.getData().getConnectionList();
            for(OnsConsumerGetConnectionResponse.Data.ConnectionDo connectionDo:connectionDoList){
                System.out.println(connectionDo.getClientId()+"  "+
                        connectionDo.getClientAddr()+"  "+
                        connectionDo.getLanguage()+"  "+
                        connectionDo.getVersion());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
