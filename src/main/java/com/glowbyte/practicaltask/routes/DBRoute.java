package com.glowbyte.practicaltask.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;


import static org.apache.camel.language.groovy.GroovyLanguage.groovy;


@Component
public class DBRoute extends RouteBuilder {
    DataFormat jaxb = new JaxbDataFormat("com.glowbyte.practicaltask.entity");
    @Override
    public void configure() throws Exception {
        from("timer:timer?period=60_000")
                .routeId("db_route")
                .setBody((groovy("""                          
                        import com.glowbyte.practicaltask.config.DBConfig
                        import com.glowbyte.practicaltask.entity.Address
                        import com.glowbyte.practicaltask.entity.Applications
                        import com.glowbyte.practicaltask.entity.Client
                        import com.glowbyte.practicaltask.entity.Income
                        import com.glowbyte.practicaltask.groovy.DBBuilder
                        import groovy.sql.Sql
                        import org.springframework.context.annotation.AnnotationConfigApplicationContext
                                                
                        import javax.sql.DataSource
                        import javax.xml.bind.JAXBContext
                        import javax.xml.bind.Marshaller
                                                
                                                
                        def context = new AnnotationConfigApplicationContext(DBConfig.class)
                        DataSource dataSource = context.getBean("dataSource")
                                                
                        def sql = new Sql(dataSource)
                                                
                        Map<BigDecimal, Set<Address>> clientAddresses = new HashMap<>()
                        Map<BigDecimal, Set<Income>> applicationIncomes = new HashMap<>()
                        Map<BigDecimal, Set<Client>> applicationClient = new HashMap<>()
                                                
                        Applications applications = new Applications()
                                                
                        // Сбор адрессов для клиентов
                        sql.query('select * from address') { resultSet ->
                            while (resultSet.next()) {
                                BigDecimal key = resultSet.getBigDecimal("client_id")
                                if (clientAddresses.containsKey(key)) {
                                    clientAddresses.get(key).add(
                                        DBBuilder.buildAddress(resultSet)
                                    )
                                } else {
                                    Set<Address> addresses = new HashSet<>()
                                    addresses.add(DBBuilder.buildAddress(resultSet))
                                    clientAddresses.put(
                                            key,
                                            addresses
                                    )
                                }
                            }
                        }
                                                
                        // сбор клиентов для приложений
                        sql.query('select * from client') { resultSet ->
                            while(resultSet.next()) {
                                BigDecimal key = resultSet.getBigDecimal("application_id")
                                if (applicationClient.containsKey(key)) {
                                    applicationClient.get(key).add(
                                            DBBuilder.buildClient(resultSet, clientAddresses)
                                    )
                                } else {
                                    Set<Client> clients = new HashSet<>();
                                    clients.add(
                                            DBBuilder.buildClient(resultSet, clientAddresses)
                                    )
                                    applicationClient.put(key, clients)
                                }
                            }
                        }
                                                
                        // Сбор дохода для приложений
                        sql.query('select * from income;') { resultSet ->
                            while (resultSet.next()) {
                                BigDecimal key = resultSet.getBigDecimal("application_id")
                                if (applicationIncomes.containsKey(key)) {
                                    applicationIncomes.get(key).add(
                                            DBBuilder.buildIncome(resultSet)
                                    )
                                } else {
                                    Set<Income> incomes = new HashSet<>()
                                    incomes.add(
                                            DBBuilder.buildIncome(resultSet)
                                    )
                                    applicationIncomes.put(key, incomes)
                                }
                            }
                        }
                                                
                        // Сбор в applications
                        sql.query('select * from application') { resultSet ->
                            while (resultSet.next()) {
                                applications.getApplications().add(
                                        DBBuilder.buildApplication(resultSet, applicationClient, applicationIncomes)
                                )
                            }
                        }
                                                
                        JAXBContext jaxb = JAXBContext.newInstance(Applications.class)
                                                
                        Marshaller marshaller = jaxb.createMarshaller();
                                                
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE)
                                                
                        Writer writer = new StringWriter()
                        marshaller.marshal(applications, writer)
                                                
                        def result = writer.toString()
                        """)))
                .to("activemq:groovy_test_queue");
    }
}