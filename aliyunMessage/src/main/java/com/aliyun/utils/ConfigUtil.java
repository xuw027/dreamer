package com.aliyun.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    public static String getRegionId(){
        return get("regionId");
    }
    public static String getConsumerId(){
        return get("ConsumerId");
    }
    public static String getTopic(){
        return get("topic");
    }
    public static String getProducerId(){
        return get("ProducerId");
    }
    public static String get(String key){
        Properties props=getProps();
        return props.getProperty(key);
    }
    public static Properties getProps()
    {
        InputStream ins=null;
        Properties properties = new Properties();
        try {
            ins= ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(ins!=null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return properties;
    }
}
