package com.github.mvallim.camelkafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.schemaregistry.EmbeddedSchemaRegistryServer;

@Configuration
@Profile("LOCAL")
class SchemaRegistryEmbeddedConfiguration {

	@Value("${brokers:1}")
	private Integer brokers;

	@Value("${broker-port:9092}")
	private Integer brokerPort;

	@Value("${schema-registry-port:8081}")
	private Integer schemaRegistryPort;

	@Value("${topic-partitions:20}")
	private Integer partitions;

	@Value("${topics:null}")
	private String[] topics;

	@Bean
	public EmbeddedKafkaBroker embeddedKafkaBroker() {
		final EmbeddedKafkaBroker broker = new EmbeddedKafkaBroker(brokers, true, partitions, topics);

		final int[] ports = new int[brokers];

		for (int i = 0; i < brokers; i++) {
			ports[i] = brokerPort + i;
		}

		broker.kafkaPorts(ports);

		return broker;
	}

	@Bean
	public EmbeddedSchemaRegistryServer embeddedSchemaRegistryServer(final EmbeddedKafkaBroker embeddedKafkaBroker) {
		return new EmbeddedSchemaRegistryServer(schemaRegistryPort, embeddedKafkaBroker.getZookeeperConnectionString());
	}

}
