package com.itbsky.job;

import com.itbsky.constant.RedisConstant;
import com.itbsky.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * 包名:com.itbsky.job
 * 作者:龙在江湖
 * 日期:2019/9/26 10:27
 */
public class MyJob {

    @Autowired
    private JedisPool jedisPool;

    public void doAbc() {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()) + ":你好世界!");
        Jedis jedis = jedisPool.getResource();
        Set<String> need2Delete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        QiNiuUtil.removeFiles(need2Delete.toArray(new String[]{}));
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //jedis.close();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-job.xml");
    }
}
