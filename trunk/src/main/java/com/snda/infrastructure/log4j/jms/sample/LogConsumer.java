package com.snda.infrastructure.log4j.jms.sample;

import org.apache.log4j.spi.LoggingEvent;

public interface LogConsumer {

	LoggingEvent consume();

	void start();

	void stop();

}
