package com.snda.infrastructure.log4j.jms.sample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.spi.LoggingEvent;

import com.google.common.base.Throwables;

public class JmsLogConsumer implements LogConsumer, MessageListener {

	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private BlockingQueue<LoggingEvent> buffer = new LinkedBlockingQueue<LoggingEvent>();

	public JmsLogConsumer(String url, String logTopicName) throws JMSException {
		initialize(url, logTopicName);	
	}

	private void initialize(String url, String logTopicName) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		this.connection = factory.createConnection();
		this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		this.consumer = this.session.createConsumer(session.createTopic(logTopicName));
		this.consumer.setMessageListener(this);
	}

	@Override
	public LoggingEvent consume() {
		try {
			return this.buffer.take();
		} catch (InterruptedException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public void start() {
		try {
			this.connection.start();
		} catch (JMSException e) {
			Throwables.propagate(e);
		}
	}

	@Override
	public void stop() {
		try {
			this.consumer.close();
			this.session.close();
			this.connection.close();
		} catch (JMSException e) {
			Throwables.propagate(e);
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			LoggingEvent event = (LoggingEvent) ((ObjectMessage) message).getObject();
			this.buffer.put(event);
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

}
