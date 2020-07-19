package com.alibaba.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author shenmeng
 * @Date 2020/7/19
 **/
public class TestJedis {

    @Test
    public void testJedis() {
        //创建一个Jedis的连接
        Jedis jedis = new Jedis("redisos", 6379);
        //如果redis有设置密码，则需要
        jedis.auth("root");
        //执行redis命令
        jedis.set("mytest", "hello world, this is jedis client!");
        //从redis中取值
        String result = jedis.get("mytest");
        //打印结果
        System.out.println(result);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        //创建一连接池对象
        JedisPool jedisPool = new JedisPool("redisos", 6379);
        //从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        jedis.auth("root");
        String result = jedis.get("mytest") ;
        System.out.println(result);
        //关闭连接
        jedis.close();
        //关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        //创建一连接，JedisCluster对象,在系统中是单例存在
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("redisos1", 7001));
        nodes.add(new HostAndPort("redisos2", 7002));
        nodes.add(new HostAndPort("redisos3", 7003));
        JedisCluster cluster = new JedisCluster(nodes);
        //执行JedisCluster对象中的方法，方法和redis一一对应。
        cluster.set("cluster-test", "my jedis cluster test");
        String result = cluster.get("cluster-test");
        System.out.println(result);
        //程序结束时需要关闭JedisCluster对象
        cluster.close();
    }

}
