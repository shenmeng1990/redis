package com.alibaba.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @Author shenmeng
 * @Date 2020/7/19
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestJedis2 {

    @Autowired
    private JedisPool jedisPool;
    @Resource
    private JedisCluster cluster;

    @Test
    public void testJedisPool() {
        // 从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("mytest");
        System.out.println(result);
        // 关闭连接
        jedis.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        // 执行JedisCluster对象中的方法，方法和redis一一对应。
        cluster.set("cluster-test", "my jedis cluster test");
        String result = cluster.get("cluster-test");
        System.out.println(result);
    }

}
