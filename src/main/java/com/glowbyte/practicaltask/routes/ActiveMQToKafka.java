package com.glowbyte.practicaltask.routes;

import com.glowbyte.practicaltask.entity.Applications;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class ActiveMQToKafka extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Applications.class);
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        xmlDataFormat.setContext(context);

        from("activemq:groovy_test_queue")
                .unmarshal().jaxb()
                .marshal().json(JsonLibrary.Jackson, Applications.class)
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody().getClass());
                })
                .log(">>>>>>>>> ${body}")
                .to("kafka:test-topic");
    }
}
