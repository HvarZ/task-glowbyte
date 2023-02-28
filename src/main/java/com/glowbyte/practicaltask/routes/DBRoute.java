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
        from("timer:timer?period=60_000")
                .routeId("db_route")
                .setBody().groovy("resource:classpath:com.glowbyte.practicaltask.groovy_db.db_to_xml")
                .marshal().jaxb()
                .log(">>>>>>>>>>>> ${body}")
                .to("log:log");

    }
}
