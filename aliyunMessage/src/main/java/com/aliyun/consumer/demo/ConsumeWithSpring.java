package com.aliyun.consumer.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumeWithSpring {
    public static void main(String[] args) {

        /**
         * ������Bean������consumer.xml��,��ͨ��ApplicationContext��ȡ����ֱ��ע�뵽������(��������Controller)��.
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        System.out.println("Consumer Started");
    }
}