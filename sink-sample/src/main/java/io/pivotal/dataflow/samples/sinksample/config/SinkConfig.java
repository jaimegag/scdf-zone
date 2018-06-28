package io.pivotal.dataflow.samples.sinksample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@EnableBinding(Sink.class)
public class SinkConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void httpSink(@Payload Map<String, String> payload) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(payload);
        String response = restTemplate.postForObject("https://species-app.apps.pcfgcp.jagapps.co/species", httpEntity, String.class);
        System.out.println("Response [" + response + "]");
    }
}
