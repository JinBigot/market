package com.java;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMqQueueConsumer {
	
	//@Test
	public void testQueueConsumer() throws Exception {
		//创建一个connectionfactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.66.217:61616");
		//通过factory创建一个connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//通过connection创建一个session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个队列名称是test_queue的queue
		Queue queue = session.createQueue("test_queue");
		//创建一个consumer
		MessageConsumer consumer = session.createConsumer(queue);
		//通过consumer设置一个监听器 有消息时调回onmessage方法
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					//处理消息
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		consumer.close();
		session.close();
		connection.close();
	}
}
