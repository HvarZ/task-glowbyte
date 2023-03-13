package com.glowbyte.practicaltask.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileToActiveMQ extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:test_data/activemq?noop=true")
        .routeId("File to ActiveMQ route")
        .to("activemq:test_1");
    }
}
