package com.snda.infrastructure.log4j.jms.sample;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;

import com.google.common.base.Throwables;


public class ActiveMQExample {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Configs.BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = new ActiveMQTopic("test-destination");
	
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("Received message : " + textMessage.getText());
				} catch (JMSException e) {
					Throwables.propagate(e);
				}
			}
		});
		
		MessageProducer producer = session.createProducer(destination);
		
		for (int i = 0; i < 100; i++) {
			ActiveMQTextMessage message = new ActiveMQTextMessage();
			message.setText(String.format("Message %d", i));
			producer.send(message);		
		}
	
		session.close();
		consumer.close();
		connection.close();
	}
}
