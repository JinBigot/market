package com.java;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMqTopicProductor {

	//@Test
	public void testTopicProductor() {
		// 创建一个connectionfactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.66.217:61616");
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("text-topic");
			producer = session.createProducer(topic);
			TextMessage textMessage = session.createTextMessage("qqq");
			producer.send(textMessage);

		} catch (JMSException e) {
			// 链接创建失败
			e.printStackTrace();
		}
		try {
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
