package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.json_in.InKafka;
import com.glowbyte.practicaltask.entity.json_out.OutKafka;
import com.glowbyte.practicaltask.processors.JsonInToJsonOut;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FromKafkaToKafkaRoute extends RouteBuilder {
    @Override
    public void configure() {
        JacksonDataFormat jsonDataFormatOut = new JacksonDataFormat(OutKafka.class);
        JacksonDataFormat jsonDataFormatIn = new JacksonDataFormat(InKafka.class);

        from("kafka:topic_1")
                .routeId("Second Route")
                .unmarshal(jsonDataFormatOut)
                .process(new JsonInToJsonOut())
                .marshal(jsonDataFormatIn)
                .to("kafka:topic_2");
    }
}
