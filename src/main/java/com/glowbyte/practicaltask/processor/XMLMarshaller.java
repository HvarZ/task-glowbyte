package com.glowbyte.practicaltask.processor;

import com.glowbyte.practicaltask.entity.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

public class XMLMarshaller implements Processor {
    @Override
    public void process(Exchange exchange) throws JAXBException {
        List<LinkedCaseInsensitiveMap<Object>> applications = (ArrayList<LinkedCaseInsensitiveMap<Object>>) exchange.getIn().getBody();
        Applications result = new Applications();

        Map<BigDecimal, Application> applicationLevel = new HashMap<>();
        for (LinkedCaseInsensitiveMap<Object> application : applications) {
            BigDecimal key = (BigDecimal) application.get("application_id");
            Application bufferApplication;
            if (applicationLevel.containsKey(key)) {
                bufferApplication = applicationLevel.get(key);
                Client clientBuffer = buildClient(application);
                Income income = buildIncome(application);
                if (bufferApplication.getClient() != null && !bufferApplication.getClient().contains(new Client((BigDecimal) application.get("client_id")))) {
                    bufferApplication.getClient().add(clientBuffer);
                } else if (bufferApplication.getClient() == null) {
                    Set<Client> set = new HashSet<>();
                    set.add(clientBuffer);
                    bufferApplication.setClient(set);
                } else {
                    Address address = buildAddress(application);
                    if (clientBuffer.getAddress() == null) {
                        Set<Address> set = new HashSet<>();
                        set.add(address);
                        clientBuffer.setAddress(set);
                    } else {
                        clientBuffer.getAddress().add(address);
                    }
                }

                if (bufferApplication.getIncome() != null && !bufferApplication.getIncome().contains(new Income((BigDecimal) application.get("income_id")))) {
                    bufferApplication.getIncome().add(income);
                } else if (bufferApplication.getIncome() == null) {
                    Set<Income> set = new HashSet<>();
                    set.add(income);
                    bufferApplication.setIncome(set);
                }

            } else {
                bufferApplication = buildApp(application);

                if (application.get("client_id") != null) {
                    Client bufferClient = buildClient(application);
                    if (application.get("address_id") != null) {
                        Address addressBuffer = buildAddress(application);
                        Set<Address> set = new HashSet<>();
                        set.add(addressBuffer);
                        bufferClient.setAddress(set);
                    }

                    Set<Client> set = new HashSet<>();
                    set.add(bufferClient);
                    bufferApplication.setClient(set);
                }

                if (application.get("income_id") != null) {
                    Income incomeBuffer = buildIncome(application);
                    Set<Income> set = new HashSet<>();
                    set.add(incomeBuffer);
                    bufferApplication.setIncome(set);
                }
                applicationLevel.put(bufferApplication.getApplicationId(), bufferApplication);
            }

            result.getApplications().add(bufferApplication);
        }

        JAXBContext context = JAXBContext.newInstance(Applications.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(result, System.out);
        //exchange.getIn().setBody(body);
    }

    private Application buildApp(LinkedCaseInsensitiveMap<Object> application) {
        return Application.builder()
                .applicationId((BigDecimal) application.get("application_id"))
                .creditAmount((BigDecimal) application.get("credit_amount"))
                .creditRate((BigDecimal) application.get("credit_rate"))
                .creditTerm((BigDecimal) application.get("credit_term"))
                .build();
    }

    private Client buildClient(LinkedCaseInsensitiveMap<Object> application) {
        return Client.builder()
                .clientId((BigDecimal) application.get("client_id"))
                .firstname((String) application.get("firstname"))
                .surname((String) application.get("surname"))
                .lastname((String) application.get("lastname"))
                .birthdate((Date) application.get("birthdate"))
                .birthplace((String) application.get("birthplace"))
                .build();
    }

    private Address buildAddress(LinkedCaseInsensitiveMap<Object> application) {
        return Address.builder()
                .addressId((BigDecimal) application.get("address_id"))
                .country((String) application.get("country"))
                .city((String) application.get("city"))
                .street((String) application.get("house"))
                .house((String) application.get("house"))
                .building((String) application.get("building"))
                .build();
    }

    private Income buildIncome(LinkedCaseInsensitiveMap<Object> application) {
        return Income.builder()
                .incomeId((BigDecimal) application.get("income_id"))
                .month((Date) application.get("month"))
                .amount((BigDecimal) application.get("amount"))
                .build();
    }

}
