package com.java.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.redis.JedisClientCluster;
import com.java.redis.JedisClientPool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	//@Test
	public void testSelf() {
		Jedis jedis = new Jedis("192.168.66.227", 6379);
		jedis.set("name", "Bigot or someone");
		System.out.println(jedis.get("name"));
		jedis.close();
	}

	//@Test
	public void testPool() {
		JedisPool jedisPool = new JedisPool("192.168.66.227", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("class", "java94");
		System.out.println(jedis.get("class"));
		// 关闭当前的jedis链接
		jedis.close();
		// 关闭jedis连接池
		jedisPool.close();
	}

	//@Test
	public void testculster() {
		Set<HostAndPort> hostAndPorts = new HashSet<>();
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7001));
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7002));
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7003));
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7004));
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7005));
		hostAndPorts.add(new HostAndPort("192.168.66.217", 7006));
		JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
		jedisCluster.set("name", "Bigot or someone cluster2");
		System.out.println(jedisCluster.get("name"));
		jedisCluster.close();
	}

	//@Test
	public void testJedisSpring() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		JedisClientPool jedisClientPool = applicationContext.getBean(JedisClientPool.class);
		jedisClientPool.set("address", "wanho");
		System.out.println(jedisClientPool.get("address"));
	}
	
	//@Test
	public void testJedisSpringCluster() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		JedisClientCluster jedisClientCluster = applicationContext.getBean(JedisClientCluster.class);
		jedisClientCluster.set("address", "wanho,jiangsu");
		System.out.println(jedisClientCluster.get("address"));
	}
}
