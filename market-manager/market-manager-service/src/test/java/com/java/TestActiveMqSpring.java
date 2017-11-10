package com.java;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestActiveMqSpring {

	//@Test
	public void testActiveSpring () {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-active.xml");
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		Queue queue = (Queue) applicationContext.getBean("queueDestination");
		jmsTemplate.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("abcdefg hijklmn opq rst uvwxyz");
				return textMessage;
			}
		});
	}
}
