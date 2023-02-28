package com.glowbyte.practicaltask.groovy

import com.glowbyte.practicaltask.entity.Address
import com.glowbyte.practicaltask.entity.Application
import com.glowbyte.practicaltask.entity.Client
import com.glowbyte.practicaltask.entity.Income

import java.sql.ResultSet

class DBBuilder {
    static Address buildAddress(ResultSet resultSet) {
        return Address.builder()
                .addressId(resultSet.getBigDecimal("address_id"))
                .country(resultSet.getString("country"))
                .city(resultSet.getString("city"))
                .street(resultSet.getString("house"))
                .building(resultSet.getString("building"))
                .flat(resultSet.getString("flat"))
                .build()
    }


    static Client buildClient(ResultSet resultSet, Map<BigDecimal, Set<Address>> clientAddresses) {
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

    static Income buildIncome(ResultSet resultSet) {
        return Income.builder()
                .clientId(resultSet.getBigDecimal("client_id"))
                .month(resultSet.getDate("month"))
                .amount(resultSet.getBigDecimal("amount"))
                .build()
    }

    static Application buildApplication(ResultSet resultSet, Map<BigDecimal, Set<Client>> applicationClient, Map<BigDecimal, Set<Income>> applicationIncome) {
        return Application.builder()
                .applicationId(resultSet.getBigDecimal("application_id"))
                .creditAmount(resultSet.getBigDecimal("credit_amount"))
                .creditRate(resultSet.getBigDecimal("credit_rate"))
                .creditTerm(resultSet.getBigDecimal("credit_term"))
                .client(applicationClient.get(resultSet.getBigDecimal("application_id")))
                .income(applicationIncome.get(resultSet.getBigDecimal("application_id")))
                .build()
    }

}
