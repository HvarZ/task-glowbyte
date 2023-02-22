package com.glowbyte.practicaltask.routes;


import com.glowbyte.practicaltask.entity.Application;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;

import com.glowbyte.practicaltask.processor.XMLMarshaller;


@Component
public class DBRoute extends RouteBuilder {
    @Autowired
    DataSource dataSource;
    @Override
    public void configure() throws Exception {
//        JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
//        Application buffer = new Application();
//
//        from("timer:timer?period=60_000")
//                .routeId("From DB to ActiveMQ")
//                .to("sql:select * from APPLICATION;")
//                .split().body()
//                .process(exchange -> {
//                    Map<String, BigDecimal> map = (LinkedCaseInsensitiveMap<BigDecimal>) exchange.getIn().getBody();
//                    buffer.setApplicationId(map.get("application_id"));
//                    buffer.setCreditAmount(map.get("credit_amount"));
//                    buffer.setCreditRate(map.get("credit_rate"));
//                    buffer.setCreditTerm(map.get("credit_term"));
//                })
//                .to("sql:select * from CLIENT where application_id = 1;")
//                .process(exchange -> {
//                    List<LinkedCaseInsensitiveMap<Object>> clients = (ArrayList<LinkedCaseInsensitiveMap<Object>>) exchange.getIn().getBody();
//
//
//                    Set<Client> bufferSetApplication = new HashSet<>();
//
//                    for (LinkedCaseInsensitiveMap<Object> client : clients) {
//                        bufferSetApplication.add(new Client(
//                                (BigDecimal)client.get("client_id"),
//                                (String) client.get("firstname"),
//                                (String) client.get("surname"),
//                                (String) client.get("lastname"),
//                                (Date) client.get("birthdate"),
//                                (String) client.get("birthplace")
//                        ));
//                    }
//
//                    buffer.setClient(bufferSetApplication);
//
//                    StringWriter writer = new StringWriter();
//
//                    Marshaller marshaller = jaxbContext.createMarshaller();
//                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//                    marshaller.marshal(buffer, writer);
//                    System.out.println(writer);
//                })
//                .to("log:logger");;


        JAXBContext context = JAXBContext.newInstance(Application.class);

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
                        "    on (a.application_id = c.application_id);")
                .routeId("test route")
                .log("<<<<<<<<<<<<< ${body}")
                .process(XMLMarshaller())
                .to("activemq:jpa-test");
    }
}
