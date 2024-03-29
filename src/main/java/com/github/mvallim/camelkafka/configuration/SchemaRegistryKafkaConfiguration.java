package com.github.mvallim.camelkafka.configuration;

import java.util.Objects;
import java.util.Properties;

import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.spi.UriParam;

class SchemaRegistryKafkaConfiguration extends KafkaConfiguration {

	@UriParam(label = "confluent")
	private String schemaRegistryURL;

	@UriParam(label = "confluent")
	private boolean autoRegisterSchemas;

	@UriParam(label = "confluent")
	private String valueSubjectNameStrategy;

	@UriParam(label = "confluent")
	private String keySubjectNameStrategy;

	@UriParam(label = "confluent,consumer")
	private boolean specificAvroReader;

	@UriParam(label = "producer")
	private String acks;

	public String getSchemaRegistryURL() {
		return schemaRegistryURL;
	}

	public void setSchemaRegistryURL(final String schemaRegistryURL) {
		this.schemaRegistryURL = schemaRegistryURL;
	}

	public boolean isSpecificAvroReader() {
		return specificAvroReader;
	}

	public void setSpecificAvroReader(final boolean specificAvroReader) {
		this.specificAvroReader = specificAvroReader;
	}

	public void setAcks(final String acks) {
		this.acks = acks;
	}

	public String getAcks() {
		return acks;
	}

	public boolean isAutoRegisterSchemas() {
		return autoRegisterSchemas;
	}

	public void setAutoRegisterSchemas(final boolean autoRegisterSchemas) {
		this.autoRegisterSchemas = autoRegisterSchemas;
	}

	public String getValueSubjectNameStrategy() {
		return valueSubjectNameStrategy;
	}

	public void setValueSubjectNameStrategy(final String valueSubjectNameStrategy) {
		this.valueSubjectNameStrategy = valueSubjectNameStrategy;
	}

	public String getKeySubjectNameStrategy() {
		return keySubjectNameStrategy;
	}

	public void setKeySubjectNameStrategy(final String keySubjectNameStrategy) {
		this.keySubjectNameStrategy = keySubjectNameStrategy;
	}

	@Override
	public Properties createConsumerProperties() {
		final Properties properties = super.createConsumerProperties();
		addPropertyIfNotNull(properties, "schema.registry.url", getSchemaRegistryURL());
		addPropertyIfNotNull(properties, "specific.avro.reader", isSpecificAvroReader());
		addPropertyIfNotNull(properties, "auto.register.schemas", isAutoRegisterSchemas());
		addPropertyIfNotNull(properties, "value.subject.name.strategy", getValueSubjectNameStrategy());
		addPropertyIfNotNull(properties, "key.subject.name.strategy", getKeySubjectNameStrategy());
		return properties;
	}

	@Override
	public Properties createProducerProperties() {
		final Properties properties = super.createProducerProperties();
		addPropertyIfNotNull(properties, "schema.registry.url", getSchemaRegistryURL());
		addPropertyIfNotNull(properties, "acks", getAcks());
		addPropertyIfNotNull(properties, "auto.register.schemas", isAutoRegisterSchemas());
		addPropertyIfNotNull(properties, "value.subject.name.strategy", getValueSubjectNameStrategy());
		addPropertyIfNotNull(properties, "key.subject.name.strategy", getKeySubjectNameStrategy());
		return properties;
	}

	private static <T> void addPropertyIfNotNull(final Properties properties, final String key, final T value) {
		if (Objects.nonNull(value)) {
			properties.put(key, value);
		}
	}

}
