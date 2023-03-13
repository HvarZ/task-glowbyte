package com.glowbyte.practicaltask.processors;

import com.glowbyte.practicaltask.entity.Application;
import com.glowbyte.practicaltask.entity.Income;
import com.glowbyte.practicaltask.entity.json.KafkaClient;
import com.glowbyte.practicaltask.entity.json.InKafka;
import com.glowbyte.practicaltask.entity.json.KafkaIncome;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlToJsonPOJO implements Processor {
    @Override
    public void process(Exchange exchange) {
        Application application = (Application) exchange.getIn().getBody();
        Map<BigDecimal, List<KafkaIncome>> incomeMap = new HashMap<>();

        for (Income income : application.getIncome()) {
            if (incomeMap.containsKey(income.getClientId())) {
                incomeMap.get(income.getClientId()).add(
                        KafkaIncome.builder()
                                .amount(income.getAmount())
                                .month(income.getMonth())
                                .build()
                );
            } else {
                List<KafkaIncome> list = new ArrayList<>();
                list.add(KafkaIncome.builder()
                            .amount(income.getAmount())
                            .month(income.getMonth())
                            .build());
                incomeMap.put(income.getClientId(), list);
            }
        }

        List<KafkaClient> clients = new ArrayList<>();
        for (Map.Entry<BigDecimal, List<KafkaIncome>> entry : incomeMap.entrySet()) {
            clients.add(KafkaClient.builder()
                    .id(entry.getKey())
                    .incomes(entry.getValue())
                    .build());
        }

        InKafka body = InKafka.builder()
                .id(application.getApplicationId())
                .monthly_payment(new BigDecimal("123"))
                .clients(clients)
                .build();

        exchange.getIn().setBody(body);
    }
}