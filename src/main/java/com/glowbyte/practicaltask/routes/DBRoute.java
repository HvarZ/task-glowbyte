package com.glowbyte.practicaltask.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import com.glowbyte.practicaltask.processor.XMLMarshaller;


@Component
public class DBRoute extends RouteBuilder {
    @Autowired
    DataSource dataSource;
    @Override
    public void configure() throws Exception {
        from("timer:timer?period=60_000")
        .to("sql:select  a.*, " +
                "        c.client_id, c.firstname, c.surname, c.lastname, c.birthdate, c.birthplace, " +
                "        addr.address_id, addr.country, addr.city, addr.street, addr.house, addr.building, addr.flat, " +
                "        i.income_id, i.client_id, i.month, i.amount " +
                "from Application a " +
                "    left outer join " +
                "        Income i " +
                "    on (a.application_id = i.application_id) " +
                "    left outer join " +
                "        Client c " +
                "            left outer join " +
                "                Address addr " +
                "            on (c.client_id = addr.client_id) " +
                "    on (a.application_id = c.application_id)" +
                "order by application_id;")
        .routeId("test route")
        .log("<<<<<<<<<<<<< ${body}")
        .process(new XMLMarshaller())
        .to("activemq:marshall-test");
    }
}
