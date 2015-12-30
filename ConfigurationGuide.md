#A simple configuration guide of how you use [Log4j](http://logging.apache.org/log4j/1.2/) JMSAppender with [Apache ActiveMQ](http://activemq.apache.org/)

# Steps #

## Step1 ##

Add the following dependencies to your pom.xml

```
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.6.1</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.6.1</version>
			<scope>compile</scope>
			<exclusions>
				<exclusion>
					<groupId>javax.mail</groupId>
					<artifactId>mail</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.mail</groupId>
					<artifactId>mail</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.jms</groupId>
					<artifactId>jms</artifactId>
				</exclusion>
				<exclusion>
					<groupId>com.sun.jdmk</groupId>
					<artifactId>jmxtools</artifactId>
				</exclusion>
				<exclusion>
					<groupId>com.sun.jmx</groupId>
					<artifactId>jmxri</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.apache.activemq</groupId>
			<artifactId>activemq-all</artifactId>
			<version>5.4.1</version>
			<scope>compile</scope>
		</dependency>

```

Please note we are using [slf4j](http://www.slf4j.org/), you can also use [commons-logging](http://commons.apache.org/logging/), but the former one is recommended.  Another important thing is <font color='red'>don't change the dependency of activemq-all to activemq-core</font>, I've tested them many times that only the former one will work well.

## Step2 ##

Put this [jndi.properties](http://code.google.com/p/log4j-jms-sample/source/browse/trunk/src/main/resources/jndi.properties) to your classpath.

## Step3 ##

Put this [log4j.xml](http://code.google.com/p/log4j-jms-sample/source/browse/trunk/src/main/resources/log4j.xml) in your classpath, change the configuration values according to your environment.

Please not we are not using log4j.properties because log4j.xml has a higher priority.

## Step4 ##

Run a broker at tcp://localhost:61616, for convenience we have provided an [EmbeddedBroker](http://code.google.com/p/log4j-jms-sample/source/browse/trunk/src/main/java/com/snda/infrastructure/log4j/jms/sample/EmbeddedBroker.java), please feel free to use it,  alternatively you can run a standalone broker according to the [official reference](http://activemq.apache.org/getting-started.html#GettingStarted-StartingActiveMQ).

## Step5 ##

Simply send a log and it's will be sent to activemq server, here is an example:

```
private static Logger logger = LoggerFactory.getLogger(Log4jJmsAppenderSampleTest.class);

in your method :

logger.info("Hi, are you there?");
```

## Step6 ##
To verify whether the log has been sent successfully, you can [checkout](http://code.google.com/p/log4j-jms-sample/source/checkout) the source code and run [the test case](http://code.google.com/p/log4j-jms-sample/source/browse/trunk/src/test/java/com/snda/infrastructure/log4j/jms/sample/Log4jJmsAppenderSampleTest.java).

That's it, enjoy!


References

1. http://activemq.apache.org/how-do-i-use-log4j-jms-appender-with-activemq.html