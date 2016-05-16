package com.aliyun.openapi;


import com.aliyun.utils.ConfigUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Properties;

public class ACSClient {
    public static IAcsClient getAcsClient()
    {
        Properties pros= ConfigUtil.getProps();
        String regionId = "cn-qingdao";
        String accessKey = pros.getProperty("AccessKey");
        String secretKey = pros.getProperty("SecretKey");
        String endPointName ="cn-qingdao";
        String productName ="Ons";
        String domain ="ons.cn-qingdao.aliyuncs.com";

        /**
         *根据自己所在的区域选择Region后,设置对应的接入点
         */
        try {
            DefaultProfile.addEndpoint(endPointName, regionId, productName, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IClientProfile profile= DefaultProfile.getProfile(regionId,accessKey,secretKey);
        IAcsClient iAcsClient= new DefaultAcsClient(profile);
        return iAcsClient;
    }
    public static void main(String[] args) {
        Properties pros= ConfigUtil.getProps();
        String regionId = "cn-qingdao";
        String accessKey = pros.getProperty("AccessKey");
        String secretKey = pros.getProperty("SecretKey");
        String endPointName ="cn-qingdao";
        String productName ="Ons";
        String domain ="ons.cn-qingdao-publictest.aliyuncs.com";

        /**
         *根据自己所在的区域选择Region后,设置对应的接入点
         */
        try {
            DefaultProfile.addEndpoint(endPointName, regionId, productName, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IClientProfile profile= DefaultProfile.getProfile(regionId,accessKey,secretKey);
        IAcsClient iAcsClient= new DefaultAcsClient(profile);
    }
}
