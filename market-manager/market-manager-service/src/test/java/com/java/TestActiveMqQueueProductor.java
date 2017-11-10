package com.java;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMqQueueProductor {

	//@Test
	public void testQueueProductor() throws Exception {
		//创建一个connection工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.66.217:61616");
		//创建一个新的connection连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//通过connection创建一个session对象
		//前一个参数是 是否开启事物 
		//第二个参数确认模式，表示消息的应答模式 有自动和手动 默认自动
		// 当第一个参数 事物开启的时候，第二个参数没有意义
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个队列名称是test_queue 的queue
		Queue queue = session.createQueue("test_queue");
		//通过session创建一个producer，参数是创建的队列名称
		MessageProducer producer = session.createProducer(queue);
		//通过session创建一个textmessage对象
		TextMessage textMessage = session.createTextMessage("1234qwerasdf");
		//将创建的对象通过produce发送
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
}
