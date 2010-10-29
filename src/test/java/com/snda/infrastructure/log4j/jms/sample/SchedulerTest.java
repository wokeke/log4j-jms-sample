package com.snda.infrastructure.log4j.jms.sample;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SchedulerTest {
	
	private static Logger logger = LoggerFactory.getLogger(SchedulerTest.class);
	
	@Test
	public void test() throws InterruptedException {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				logger.info("-> " + logger);
			}
		}, 2, 2, TimeUnit.SECONDS);
		
		TimeUnit.SECONDS.sleep(1000000L);
	}
	
}
