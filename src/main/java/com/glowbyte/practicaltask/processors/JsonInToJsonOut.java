package com.glowbyte.practicaltask.processors;

import com.glowbyte.practicaltask.entity.json_in.InClient;
import com.glowbyte.practicaltask.entity.json_in.InKafka;
import com.glowbyte.practicaltask.entity.json_out.OutIncome;
import com.glowbyte.practicaltask.entity.json_out.OutKafka;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonInToJsonOut implements Processor {
    @Override
    public void process(Exchange exchange) {
        OutKafka message = (OutKafka) exchange.getIn().getBody();
        List<BigDecimal> averageIncomes = new ArrayList<>(message.getClients().size());

        // Получаем средний доход по каждому клиенту
        for (int i = 0; i < message.getClients().size(); ++i) {
            List<OutIncome> incomes = message.getClients().get(i).getIncomes();
            double average = 0;
            for (OutIncome income : incomes) {
                average += income.getAmount().doubleValue();
            }
            average = average / incomes.size();
            averageIncomes.add(new BigDecimal(average));
        }

        // Получаем средний размер платежа на одного человека
        double averageMonthlyPayment = message.getMonthly_payment().doubleValue() / averageIncomes.size();

        // Получаем результат по каждому клиенту
        List<InClient> clients = new ArrayList<>();
        for (int i = 0; i < averageIncomes.size(); ++i) {
            clients.add(InClient.builder()
                    .id(message.getClients().get(i).getId())
                    .solvent(averageMonthlyPayment < averageIncomes.get(i).doubleValue())
                    .build());
        }

        InKafka result = InKafka.builder()
                .id(message.getId())
                .clients(clients)
                .build();

        exchange.getIn().setBody(result);
    }
}
