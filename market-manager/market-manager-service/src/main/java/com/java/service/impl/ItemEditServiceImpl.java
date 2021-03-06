package com.java.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.java.dto.EazyUIStatus;
import com.java.mapper.TbItemMapper;
import com.java.model.TbItem;
import com.java.model.TbItemExample;
import com.java.model.TbItemExample.Criteria;
import com.java.service.ItemEditServiceI;

@Service
public class ItemEditServiceImpl implements ItemEditServiceI {
	@Resource
	private TbItemMapper tbItemMapper;

	@Resource
	private JmsTemplate jmsTemplate;

	@Resource
	private Destination topicEditDestination;

	@Override
	public EazyUIStatus updateItems(Long[] ids, String status) {

		for (Long id : ids) {
			TbItem record = tbItemMapper.selectByPrimaryKey(id);
			TbItemExample example = new TbItemExample();
			Criteria createCriteria = example.createCriteria();
			switch (status) {
			case ("reshelf"):
				record.setStatus((byte) 1);
				break;
			case ("instock"):
				record.setStatus((byte) 2);
				break;
			case ("delete"):
				record.setStatus((byte) 3);
				break;
			}
			// if ("reshelf".equals(status)) {
			// record.setStatus((byte)1);
			// } else if ("instock".equals(status)) {
			// record.setStatus((byte)2);
			// } else {
			// record.setStatus((byte)3);
			// }
			createCriteria.andIdEqualTo(id);
			record.setUpdated(new Date());
			tbItemMapper.updateByExample(record, example);
			// 往activemq中添加商品修改的id
			jmsTemplate.send(topicEditDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(ids + "");
					return textMessage;
				}
			});
		}
		EazyUIStatus eazyUIStatus = new EazyUIStatus();
		eazyUIStatus.setStatus("200");
		return eazyUIStatus;
	}
}
