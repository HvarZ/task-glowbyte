package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.Applications;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class ActiveMQToKafka extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Applications.class);
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContext(context);

        from("activemq:groovy_test")
                .unmarshal(jaxbDataFormat)
                //.marshal().json(JsonLibrary.Jackson, Applications.class)
                .to("file:C:\\Users\\zakhar.ushakov\\IdeaProjects\\practical-task\\src\\main\\resources\\result");
    }
}
