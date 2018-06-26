package io.pivotal.dataflow.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableBinding(Processor.class)
public class SampleConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleConverterApplication.class, args);
    }

//    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
//    public String verifyData(Species payload) {
//        if (payload == null) {
//            return null;
//        }
//        if (payload.getCommon_name() != null) {
//            payload.setCommon_name(payload.getCommon_name() + " - edited");
//        }
//        return payload.toString();
//    }

//    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
//    public LinkedCaseInsensitiveMap verifyData(LinkedCaseInsensitiveMap payload) {
//        if (payload == null) {
//            return null;
//        }
//        System.out.println("Input payload keys [" + payload.keySet().toString() + "]");
//        System.out.println("Input payload values [" + payload.values().toString() + "]");
//        String common_name = (String)payload.get("common_name");
//        if (common_name != null) {
//            payload.put("common_name", common_name + " - edited");
//        }
//        return payload;
//    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object verifyData(List<Map<String, Object>> payload) {
        if (payload == null) {
            return null;
        }
        Map<String, Object> firstFromList = payload.get(0);
        System.out.println("Input payload 0 keySet" + firstFromList.keySet());
        System.out.println("Input payload 0 values" + firstFromList.values());
        return payload;
    }


}
