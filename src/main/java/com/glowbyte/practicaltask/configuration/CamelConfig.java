package com.glowbyte.practicaltask.configuration;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class CamelConfig {
    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");

        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:postgresql://localhost:5433/glowbyte_db?user=glowbyte&password=password"
        );

        camelContext.getRegistry().bind("glowbyte_db", dataSource);

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer:base?period=60000")
                        .routeId("Database route")
                        .setBody(simple("select * from APPLICATION"))
                        .to("jdbc:glowbyte_db")
                        .log(">>> ${body}");
            }
        });

        camelContext.start();
        Thread.sleep(4_000);
        camelContext.close();

        return camelContext;
    }
}
