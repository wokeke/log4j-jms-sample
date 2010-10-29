package com.snda.infrastructure.log4j.jms.sample;

import org.apache.activemq.broker.BrokerService;

public class EmbeddedBroker {

	private BrokerService broker;

	public EmbeddedBroker(String brokerURL) throws Exception {
		broker = new BrokerService();
		broker.setSchedulerSupport(false);
		broker.addConnector(brokerURL);
	}
	
	public void start() throws Exception {
		broker.start();
	}

	public void stop() throws Exception {
		broker.stop();
	}
	
	public static void main(String[] args) throws Exception {
		new EmbeddedBroker(Configs.BROKER_URL).start();
	}
	
}
