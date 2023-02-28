package com.glowbyte.practicaltask.groovy_db

import com.glowbyte.practicaltask.PracticalTaskApplication
import com.glowbyte.practicaltask.entity.Address
import com.glowbyte.practicaltask.entity.Application
import com.glowbyte.practicaltask.entity.Applications
import com.glowbyte.practicaltask.entity.Client
import com.glowbyte.practicaltask.entity.Income
import groovy.sql.Sql
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import javax.sql.DataSource
import java.sql.ResultSet


def context = new AnnotationConfigApplicationContext(PracticalTaskApplication.class)
DataSource dataSource = context.getBean("dataSource")

def sql = new Sql(dataSource)

Map<BigDecimal, Set<Address>> clientAddresses = new HashMap<>()
Map<BigDecimal, Set<Income>> applicationIncomes = new HashMap<>()
Map<BigDecimal, Set<Client>> applicationClient = new HashMap<>()

Applications result = new Applications()

// Сбор адрессов для клиентов
sql.query('select * from address') {resultSet ->
    while (resultSet.next()) {
        BigDecimal key = resultSet.getBigDecimal("client_id")
        if (clientAddresses.containsKey(key)) {
            clientAddresses.get(key).add(
                buildAddress(resultSet)
            )
        } else {
            Set<Address> addresses = new HashSet<>()
            addresses.add(buildAddress(resultSet))
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
                    buildClient(resultSet, clientAddresses)
            )
        } else {
            Set<Client> clients = new HashSet<>();
            clients.add(
                    buildClient(resultSet, clientAddresses)
            )
            applicationClient.put(key, clients)
        }
    }
}

// Сбор дохода для приложений
sql.query('select * from income;') {resultSet ->
    while (resultSet.next()) {
        BigDecimal key = resultSet.getBigDecimal("application_id")
        if (applicationIncomes.containsKey(key)) {
            applicationIncomes.get(key).add(
                    buildIncome(resultSet)
            )
        } else {
            Set<Income> incomes = new HashSet<>()
            incomes.add(
                    buildIncome(resultSet)
            )
            applicationIncomes.put(key, incomes)
        }
    }
}

// Сбор в result
sql.query('select * from application') {resultSet ->
    while (resultSet.next()) {
        result.getApplications().add(
                buildApplication(resultSet, applicationClient, applicationIncomes)
        )
    }
}


private static Address buildAddress(ResultSet resultSet) {
    return Address.builder()
            .addressId(resultSet.getBigDecimal("address_id"))
            .country(resultSet.getString("country"))
            .city(resultSet.getString("city"))
            .street(resultSet.getString("house"))
            .building(resultSet.getString("building"))
            .flat(resultSet.getString("flat"))
            .build()
}


private static Client buildClient(ResultSet resultSet, Map<BigDecimal, Set<Address>> clientAddresses) {
    return Client.builder()
            .clientId(resultSet.getBigDecimal("client_id"))
            .firstname(resultSet.getString("firstname"))
            .surname(resultSet.getString("lastname"))
            .lastname(resultSet.getString("lastname"))
            .birthdate(resultSet.getDate("birthdate"))
            .birthplace(resultSet.getString("birthplace"))
            .address(clientAddresses.get(resultSet.getBigDecimal("client_id")))
            .build()
}

private static Income buildIncome(ResultSet resultSet) {
    return Income.builder()
            .clientId(resultSet.getBigDecimal("client_id"))
            .month(resultSet.getDate("month"))
            .amount(resultSet.getBigDecimal("amount"))
            .build()
}

private static Application buildApplication(ResultSet resultSet, Map<BigDecimal, Set<Client>> applicationClient, Map<BigDecimal, Set<Income>> applicationIncome) {
    return Application.builder()
            .applicationId(resultSet.getBigDecimal("application_id"))
            .creditAmount(resultSet.getBigDecimal("credit_amount"))
            .creditRate(resultSet.getBigDecimal("credit_rate"))
            .creditTerm(resultSet.getBigDecimal("credit_term"))
            .client(applicationClient.get(resultSet.getBigDecimal("application_id")))
            .income(applicationIncome.get(resultSet.getBigDecimal("application_id")))
            .build()
}
