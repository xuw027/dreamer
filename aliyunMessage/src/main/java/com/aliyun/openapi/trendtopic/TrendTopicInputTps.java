package com.aliyun.openapi.trendtopic;

import com.aliyun.openapi.ACSClient;
import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsTrendTopicInputTpsRequest;
import com.aliyuncs.ons.model.v20160503.OnsTrendTopicInputTpsResponse;

/**
 * Created by xuw on 2016/5/16.
 */
public class TrendTopicInputTps {
    public static void main(String []args) {
        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsTrendTopicInputTpsRequest request =new OnsTrendTopicInputTpsRequest();
        /**
         * OnsRegionId	String	是	当前查询ONS所在区域，可以通过OnsRegionList方法获取
         OnsPlatform	String	否	该请求来源，默认是从POP平台
         PreventCache	Long	是	用于CSRF校验，设置为系统当前时间即可
         Topic	String	是	需要查询的topic名称
         BeginTime	Long	是	查询区间起始毫秒时间戳
         EndTime	Long	是	查询区间终止毫秒时间戳
         * Period	Long	是	采样周期，单位分钟，支持（1,5,10）
         Type	Integer	是	查询的类型（0代表总量，1代表TPS）
         */
        request.setOnsRegionId(ConfigUtil.getRegionId());
        request.setPreventCache(System.currentTimeMillis());
        request.setAcceptFormat(FormatType.JSON);
        request.setTopic(ConfigUtil.getTopic());
        request.setBeginTime(System.currentTimeMillis()-400*3600*1000);
        request.setEndTime(System.currentTimeMillis());

        request.setPeriod(1L);
        request.setType(0);
        try {
            OnsTrendTopicInputTpsResponse response =iAcsClient.getAcsResponse(request);
            OnsTrendTopicInputTpsResponse.Data data =response.getData();
            System.out.println(data.getTitle()+"\n"+
                    data.getRecords());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
