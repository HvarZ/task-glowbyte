package com.glowbyte.practicaltask.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class DBRoute extends RouteBuilder {
    @Autowired
    DataSource dataSource;
    @Override
    public void configure() throws Exception {
        from("timer:timer?period=60000")
                .routeId("From DB to ActiveMQ")
                .to("sql:select * from APPLICATION;")
                .split().body()
                .log(">>>>>>>>>> ${body}")
                .process(exchange -> {

                })
                .marshal().jaxb()
                .to("activemq:my-active-mq");
    }
}
