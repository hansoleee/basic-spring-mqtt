package com.hansoleee.basicspringmqtt.config;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@RequiredArgsConstructor
public class MqttPublisherConfig {

    public static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
    private final MqttProperties mqttProperties;

    @Bean
    public MessageChannel mqttPublishChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttPublishChannel")
    public MessageHandler mqttPublishHandler(
            DefaultMqttPahoClientFactory clientFactory
    ) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MQTT_CLIENT_ID, clientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(mqttProperties.getQos());
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttPublishChannel")
    public interface PublishGateway {
        void send(@Header(MqttHeaders.TOPIC) String topic, String payload);
    }
}
