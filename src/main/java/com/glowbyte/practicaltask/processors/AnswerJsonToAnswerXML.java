package com.glowbyte.practicaltask.processors;

import com.glowbyte.practicaltask.entity.XMLAnswer.Application;
import com.glowbyte.practicaltask.entity.XMLAnswer.Decision;
import com.glowbyte.practicaltask.entity.XMLAnswer.XMLAnswer;
import com.glowbyte.practicaltask.entity.json_in.InClient;
import com.glowbyte.practicaltask.entity.json_in.InKafka;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AnswerJsonToAnswerXML implements Processor {
    @Override
    public void process(Exchange exchange) {
        InKafka answer = (InKafka) exchange.getIn().getBody();
        Decision decision = Decision.APPROVED;

        for (InClient client : answer.getClients()) {
            if (!client.isSolvent()) {
                decision = Decision.DECLINE;
                break;
            }
        }

        exchange.getIn().setBody(
                XMLAnswer.builder()
                        .application(new Application(answer.getId()))
                        .decision(decision)
                        .build()
        );
    }
}
