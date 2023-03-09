package com.glowbyte.practicaltask.entity.json;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InKafka {
    private BigDecimal id;
    private BigDecimal monthly_payment;
    private List<KafkaClient> clients;

    @Builder
    public InKafka(BigDecimal id, BigDecimal monthly_payment, List<KafkaClient> clients) {
        this.id = id;
        this.monthly_payment = monthly_payment;
        this.clients = clients;
    }
}
