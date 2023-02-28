package com.glowbyte.practicaltask.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5433/glowbyte_db")
                .username("glowbyte")
                .password("password")
                .build();
    }
}
