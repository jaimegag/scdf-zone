package io.pivotal.dataflow.samples.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;
import java.util.Map;

@EnableBinding(Processor.class)
public class ProcessorConfig {
    
    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Map<String, Object> addState(@Payload Map<String, Object> payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", payload.getOrDefault("id", "-1"));
        map.put("county", payload.getOrDefault("county", "-1") + " (NY)");
        map.put("category", payload.getOrDefault("category", "-1"));
        map.put("taxonomy_g", payload.getOrDefault("taxonomy_sg", "-1"));
        map.put("taxonomy_sg", payload.getOrDefault("taxonomy_sg", "-1"));
        map.put("sci_name", payload.getOrDefault("sci_name", "-1"));
        map.put("common_name", payload.getOrDefault("common_name", "-1"));
        return map;
    }
}
