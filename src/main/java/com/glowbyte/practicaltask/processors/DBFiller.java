package com.glowbyte.practicaltask.processors;

import com.glowbyte.practicaltask.entity.Address;
import com.glowbyte.practicaltask.entity.Application;
import com.glowbyte.practicaltask.entity.Client;
import com.glowbyte.practicaltask.entity.Income;
import com.glowbyte.practicaltask.repository.ApplicationRepo;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DBFiller implements Processor {
    private final ApplicationRepo applicationRepo;
    @Override
    public void process(Exchange exchange) {
        Application application = (Application) exchange.getIn().getBody();
        Set<Address> addressSet = new HashSet<>();
        for (Client client : application.getClient()) {
            for (Address address : client.getAddress()) {
                address.setApplication(application);
                address.setClient(client);
                addressSet.add(address);
            }
            client.setApplication(application);
        }
        for (Income income : application.getIncome()) {
            income.setApplication(application);
        }
        application.setAddress(addressSet);
        applicationRepo.save(application);
    }

    public DBFiller(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }
}
