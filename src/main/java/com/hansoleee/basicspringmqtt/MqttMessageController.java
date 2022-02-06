package com.hansoleee.basicspringmqtt;

import com.hansoleee.basicspringmqtt.config.MqttPublisherConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MqttMessageController {

    private final MqttPublisherConfig.PublishGateway publishGateway;

    @PostMapping
    public void publish(@RequestBody MqttMessageRequest messageRequest) {
        publishGateway.send("spring-test", messageRequest.getMessage());
    }
}
