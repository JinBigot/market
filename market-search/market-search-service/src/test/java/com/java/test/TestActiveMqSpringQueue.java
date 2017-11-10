package com.java.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiveMqSpringQueue {

	//@Test
	public void testActiveMqSpring() throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-active.xml");
		System.in.read();
	}
}
