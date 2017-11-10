package com.java;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestActiveMqTopicConsumer {
	//@Test
	public void testTopicConsumer() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.66.217:61616");
		Connection connection=null;
		Session session =null;
		MessageConsumer consumer =null;
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("text-topic");
			consumer = session.createConsumer(topic);
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String string = textMessage.getText();
						System.out.println(string);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			System.in.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			session.close();
			consumer.close();
			connection.close();
			
		} catch (JMSException  e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
