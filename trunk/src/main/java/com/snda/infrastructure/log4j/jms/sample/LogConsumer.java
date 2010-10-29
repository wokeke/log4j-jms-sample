package com.snda.infrastructure.log4j.jms.sample;

public interface LogConsumer {

	Log consume();

	void start();

	void stop();

}
