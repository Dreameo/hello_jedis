package com.yfh;

import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisTest {
    public static void main(String[] args) {
        // 创建jedis对象, 传入两个参数，主机和端口
        Jedis jedis = new Jedis("121.199.41.235",3379);
        String redispwd = jedis.auth("redispwd");
        System.out.println(redispwd);
        System.out.println("jedis.ping() = " + jedis.ping());

        // 测试
//        String ping = jedis.ping(); // 有值就是ping通了
//        System.out.println(ping);


    }

    @Test
    public void test1(){
        // 创建jedis对象, 传入两个参数，主机和端口
        Jedis jedis = new Jedis("121.199.41.235",6379);
        jedis.set("name", "xiaoming");
        jedis.set("age","28");

        jedis.mset("k1", "v2", "k2","v2", "k3", "v3");
        System.out.println(jedis.mget("k1"));


        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
