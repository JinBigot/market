package com.java.search.listenner;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.java.search.service.SearchItemServiceI;

public class ItemAddListener implements MessageListener {

	@Resource
	private SearchItemServiceI searchItemServiceI;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				Long itemId = Long.parseLong(text);
				searchItemServiceI.addSolrByItemId(itemId);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
