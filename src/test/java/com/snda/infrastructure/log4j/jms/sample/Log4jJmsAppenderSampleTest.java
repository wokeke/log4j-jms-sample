package com.snda.infrastructure.log4j.jms.sample;

import hamcrest.Ensure;

import javax.jms.JMSException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4jJmsAppenderSampleTest extends Ensure {
	
	private static Logger logger = LoggerFactory.getLogger(Log4jJmsAppenderSampleTest.class);
	
	private LogConsumer consumer;
	
	@Before
	public void before() throws JMSException {
		// Initialize Consumer
		consumer = new JmsLogConsumer(Configs.BROKER_URL, Configs.LOG_TOPIC_NAME);
		consumer.start();
	}
	
	@After
	public void after() {
		consumer.stop();
	}
	
	@Test
	public void messageOnly() throws Exception {	
		logger.info("Hi, are you there?");
		ensureThat(consumer.consume().message(), shouldBe("Hi, are you there?"));
	}
	
	
}
