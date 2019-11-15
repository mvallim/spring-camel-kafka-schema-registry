package com.github.mvallim.camelkafka.configuration;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.schemaregistry.serializer.WrapperKafkaAvroDeserializer;
import org.springframework.schemaregistry.serializer.WrapperKafkaAvroSerializer;

import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;

@Configuration
class AppConfiguration {

	@Bean
	public KafkaConfiguration kafkaConfiguration() {
		final SchemaRegistryKafkaConfiguration configuration = new SchemaRegistryKafkaConfiguration();
		configuration.setBrokers("localhost:9092");
		configuration.setSchemaRegistryURL("http://localhost:8081");
		configuration.setSpecificAvroReader(true);
		configuration.setAutoRegisterSchemas(true);
		configuration.setTopic("test");
		configuration.setAutoCommitEnable(false);
		configuration.setMaxPollIntervalMs(3000L);
		configuration.setAcks("all");
		configuration.setGroupId("group-id");
		configuration.setValueSubjectNameStrategy(TopicRecordNameStrategy.class.getCanonicalName());
		configuration.setKeyDeserializer(StringDeserializer.class.getCanonicalName());
		configuration.setValueDeserializer(WrapperKafkaAvroDeserializer.class.getCanonicalName());
		configuration.setKeySerializerClass(StringSerializer.class.getCanonicalName());
		configuration.setSerializerClass(WrapperKafkaAvroSerializer.class.getCanonicalName());
		return configuration;
	}

	@Bean
	public KafkaEndpoint kafkaEndpoint(final KafkaConfiguration configuration) {
		final KafkaEndpoint kafkaEndpoint = new KafkaEndpoint();
		kafkaEndpoint.setConfiguration(configuration);
		return kafkaEndpoint;
	}

}
