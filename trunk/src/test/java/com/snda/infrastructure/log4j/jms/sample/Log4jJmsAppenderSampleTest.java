package com.snda.infrastructure.log4j.jms.sample;

import hamcrest.Ensure;

import javax.jms.JMSException;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.googlecode.functionalcollections.FunctionalIterable;
import com.googlecode.functionalcollections.FunctionalIterables;


public class Log4jJmsAppenderSampleTest extends Ensure {
	
	private static Logger logger = LoggerFactory.getLogger(Log4jJmsAppenderSampleTest.class);
	
	private LogConsumer consumer;
	
	@Before
	public void before() throws JMSException {
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
		LoggingEvent loggingEvent = consumer.consume();
		ensureThat(loggingEvent.getLoggerName(), shouldBe(Log4jJmsAppenderSampleTest.class.getName()));
		ensureThat(loggingEvent.getLevel(), shouldBe(Level.INFO));
		ensureThat(loggingEvent.getMessage().toString(), shouldBe("Hi, are you there?"));
	}
	
	@Test
	public void withExceptionStackTrace() throws Exception {	
		logger.info("Test exception", new RuntimeException("level-1", new IllegalArgumentException("level-0")));
		LoggingEvent loggingEvent = consumer.consume();
		ensureThat(loggingEvent.getLoggerName(), shouldBe(Log4jJmsAppenderSampleTest.class.getName()));
		ensureThat(loggingEvent.getLevel(), shouldBe(Level.INFO));
		ensureThat(loggingEvent.getMessage().toString(), shouldBe("Test exception"));
		
		FunctionalIterable<String> throwableStrRep = FunctionalIterables.make(loggingEvent.getThrowableStrRep());
		
		String[] expectedStackTrace = {
			"java.lang.IllegalArgumentException: level-0",
			"java.lang.RuntimeException: level-1"
		};
		
		for (final String expected : expectedStackTrace) {
			ensureThat(throwableStrRep.find(predicateOf(expected)), isNotNull());
		}
	}

	private Predicate<String> predicateOf(final String expected) {
		return new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return input.contains(expected);
			}
		};
	}

}
