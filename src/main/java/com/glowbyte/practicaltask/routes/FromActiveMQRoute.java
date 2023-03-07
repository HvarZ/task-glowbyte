package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.Application;
import com.glowbyte.practicaltask.processors.DBFiller;
import com.glowbyte.practicaltask.repository.ApplicationRepo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class FromActiveMQRoute extends RouteBuilder {
    @Autowired
    ApplicationRepo applicationRepo;

    @Override
    public void configure() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Application.class);
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContext(context);

        from("activemq:test_1")
                .routeId("First Route")
                .unmarshal(jaxbDataFormat)
                .process(new DBFiller(applicationRepo))
                .marshal().jaxb()
                .log("\n${body}");
    }
}
