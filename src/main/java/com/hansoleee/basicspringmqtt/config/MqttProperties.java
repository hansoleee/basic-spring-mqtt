package com.hansoleee.basicspringmqtt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mosquitto")
@Getter
@Setter
public class MqttProperties {

    private String[] publishUrl;
    private String subscribeUrl;
    private String username;
    private String password;
    private int qos;
    private String[] topics;
}
