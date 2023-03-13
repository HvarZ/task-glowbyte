package com.glowbyte.practicaltask.entity.json_out;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OutKafka {
    private BigDecimal id;
    private BigDecimal monthly_payment;
    private List<OutClient> clients;

    @Builder
    public OutKafka(BigDecimal id, BigDecimal monthly_payment, List<OutClient> clients) {
        this.id = id;
        this.monthly_payment = monthly_payment;
        this.clients = clients;
    }
}
