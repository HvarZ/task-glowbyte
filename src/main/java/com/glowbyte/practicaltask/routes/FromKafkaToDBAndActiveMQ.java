package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.processors.AnswerJsonToAnswerXML;
import org.apache.camel.builder.RouteBuilder;

public class FromKafkaToDBAndActiveMQ extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:topic_2?groupId=from_topic_2")
                .process(new AnswerJsonToAnswerXML())
                .to("activemq:answer");
    }
}
