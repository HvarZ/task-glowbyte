package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.json_in.InKafka;
import com.glowbyte.practicaltask.processors.AnswerJsonToAnswerXML;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FromKafkaToDBAndActiveMQ extends RouteBuilder {
    @Override
    public void configure() {
        from("kafka:topic_2?groupId=from_topic_2")
                .unmarshal(new JacksonDataFormat(InKafka.class))
                .process(new AnswerJsonToAnswerXML())
                .marshal().jaxb()
                .to("activemq:answer");
    }
}
