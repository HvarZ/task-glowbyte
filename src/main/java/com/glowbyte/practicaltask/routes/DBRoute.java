package com.glowbyte.practicaltask.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DBRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:timer?period=60_000")
                .routeId("db_route")
                .setBody()
                .groovy("resource:classpath:groovy/DbToXML.groovy")
                .marshal().jaxb()
                .to("activemq:groovy_test");
    }
}