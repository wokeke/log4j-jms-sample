package com.snda.infrastructure.log4j.jms.sample;

public class Log {

	private final String message;

	public static Log of(String message) {
		return new Log(message);
	}
	
	private Log(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}



}
