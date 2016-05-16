package com.aliyun.openapi.trendtopic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTrendGroupOutputTpsRequest;
import com.aliyuncs.ons.model.v20160503.OnsTrendGroupOutputTpsResponse;

/**
 Topic写入统计
 根据用户指定的ONS区域，查询指定CID订阅组消费指定Topic的消息，在某段时间内的消费TPS或者总量的统计。支持总量和tps两种查询，如果量小，建议不要使用tps查询方式。
 请求参数
 */
public class TrendGroupOutputTps {
    public static void main(String []args) {
        IAcsClient iAcsClient = ACSClient.getAcsClient();
        OnsTrendGroupOutputTpsRequest request = new OnsTrendGroupOutputTpsRequest();
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setConsumerId(ConfigUtil.getConsumerId());
        request.setBeginTime(System.currentTimeMillis() - 4 * 3600 * 1000);
        request.setEndTime(System.currentTimeMillis());
        request.setPeriod(1L);
        request.setType(0);
        try {
            OnsTrendGroupOutputTpsResponse response = iAcsClient.getAcsResponse(request);
            OnsTrendGroupOutputTpsResponse.Data data = response.getData();
            System.out.println(data.getTitle() + "\n" +
                    data.getRecords());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
