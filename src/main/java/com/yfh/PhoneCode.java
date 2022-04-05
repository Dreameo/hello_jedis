package com.yfh;

import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {

    public static void main(String[] args) {
//        String code = getCode(); // 1
//        System.out.println(code);

        verifyCode("18172745452");
//        verify("18172745452", "143924");
    }


    // 3.进行验证码校验
    public static void verify(String phone, String code) {
        // 连接redis
        Jedis jedis = new Jedis("121.199.41.235", 3379);
        jedis.auth("redispwd");

        String redisCode = jedis.get("Verify:" + phone + ":code");
        System.out.println("redisCode = " + redisCode);
        System.out.println("code = " + code);
        if (code.equals(redisCode)) {
            System.out.println("匹配成功");
        } else
            System.out.println("匹配失败");
        jedis.close();
    }


    // 2. 让每个手机每天只能发送三次，验证码放在redis中，设置过期时间
    public static void verifyCode(String phone) {
        // 连接redis
        Jedis jedis = new Jedis("121.199.41.235", 3379);
        jedis.auth("redispwd");

        // 记录手机号码获取验证码的次数
        String countKey = "Verify:" + phone + ":count";
        String codeKey = "Verify:" + phone + ":code";

        if (jedis.get(countKey) == null) { // 没有发送过请求
            jedis.set(countKey, "1");

        } else if (Integer.parseInt(jedis.get(countKey)) <= 3) { // 如果获取的次数小于等于2
            jedis.incr(countKey); // 增加次数
        } else {
            System.out.println("今日已经发了三次验证码了，不能发了!");
            jedis.close();
            return;
        }
        jedis.setex(codeKey, 24 * 60 * 60, getCode());
        jedis.close();

    }


    // 1、生成6位数字验证码
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

}
