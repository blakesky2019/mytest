package com.itbsky.controller;

import com.itbsky.constant.MessageConstant;
import com.itbsky.constant.RedisConstant;
import com.itbsky.constant.RedisMessageConstant;
import com.itbsky.entity.Result;
import com.itbsky.utils.SMSUtils;
import com.itbsky.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/28 10:55
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone ){
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        return sendValidateCode(telephone,key);
    }


    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        return sendValidateCode(telephone,key);
    }

    public Result sendValidateCode(String telephone,String key) {
        Jedis jedis = jedisPool.getResource();

        //判断是否已经发送过验证码
        if (null != jedis.get(key)) {
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }

        Integer code = ValidateCodeUtils.generateValidateCode(6);

        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code+"");
            jedis.setex(key,5*60,code+"");

            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }

        return new Result(true,MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
