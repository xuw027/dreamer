package com.aliyun.openapi.region;


import com.aliyun.openapi.ACSClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20160503.OnsRegionListRequest;
import com.aliyuncs.ons.model.v20160503.OnsRegionListResponse;

import java.util.List;

/**
 * 获取用户的Region信息
 根据用户指定的ONS区域，查询用户所涉及的所有的ONS的region信息。该region信息用于在发送其他请求时使用。
 因为对ONS的其他操作都是要基于region来查询
 */
public class RegionList {
    public static void main(String[] args) {

        IAcsClient iAcsClient= ACSClient.getAcsClient();
        OnsRegionListRequest request = new OnsRegionListRequest();

        request.setAcceptFormat(FormatType.JSON);
        request.setPreventCache(System.currentTimeMillis());
        try {
            OnsRegionListResponse response = iAcsClient.getAcsResponse(request);
            List<OnsRegionListResponse.RegionDo> regionDoList=response.getData();
            for (OnsRegionListResponse.RegionDo regionDo:regionDoList){
                System.out.println(regionDo.getId()+"  "+
                        regionDo.getOnsRegionId()+"  "+
                        regionDo.getRegionName()+"  "+
                        regionDo.getChannelId()+"  "+
                        regionDo.getChannelName()+"  "+
                        regionDo.getCreateTime()+"  "+
                        regionDo.getUpdateTime());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
