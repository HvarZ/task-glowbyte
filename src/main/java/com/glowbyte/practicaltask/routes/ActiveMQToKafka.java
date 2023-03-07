package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.Application;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import javax.xml.bind.JAXBContext;

//@Component
public class ActiveMQToKafka extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Application.class);
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContext(context);

        from("activemq:groovy_test")
                .unmarshal(jaxbDataFormat)
                //.marshal().json(JsonLibrary.Jackson, Applications.class)
                .to("file:C:\\Users\\zakhar.ushakov\\IdeaProjects\\practical-task\\src\\main\\resources\\result");
    }
}