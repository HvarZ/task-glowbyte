package com.glowbyte.practicaltask.processors;

import com.glowbyte.practicaltask.entity.Application;
import com.glowbyte.practicaltask.entity.Income;
import com.glowbyte.practicaltask.entity.json_out.OutClient;
import com.glowbyte.practicaltask.entity.json_out.OutKafka;
import com.glowbyte.practicaltask.entity.json_out.OutIncome;
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
        Map<BigDecimal, List<OutIncome>> incomeMap = new HashMap<>();

        for (Income income : application.getIncome()) {
            if (incomeMap.containsKey(income.getClientId())) {
                incomeMap.get(income.getClientId()).add(
                        OutIncome.builder()
                                .amount(income.getAmount())
                                .month(income.getMonth())
                                .build()
                );
            } else {
                List<OutIncome> list = new ArrayList<>();
                list.add(OutIncome.builder()
                        .amount(income.getAmount())
                        .month(income.getMonth())
                        .build());
                incomeMap.put(income.getClientId(), list);
            }
        }

        List<OutClient> clients = new ArrayList<>();
        for (Map.Entry<BigDecimal, List<OutIncome>> entry : incomeMap.entrySet()) {
            clients.add(OutClient.builder()
                    .id(entry.getKey())
                    .incomes(entry.getValue())
                    .build());
        }

        OutKafka body = OutKafka.builder()
                .id(application.getApplicationId())
                .monthly_payment(getMonthlyPayment(application))
                .clients(clients)
                .build();

        exchange.getIn().setBody(body);
    }

    private BigDecimal getMonthlyPayment(Application application) {
        double credit = application.getCreditAmount().doubleValue();
        double rate = application.getCreditRate().doubleValue();
        double n = application.getCreditTerm().doubleValue();
        double buffer = Math.pow(1 + (rate / 1200), n);

        return BigDecimal.valueOf(
                credit * ((rate / 1200) * buffer) / (buffer - 1)
        );
    }
}