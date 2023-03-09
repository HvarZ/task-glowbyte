package com.glowbyte.practicaltask.entity.json;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class KafkaClient {
    private BigDecimal id;
    private List<KafkaIncome> incomes;

    @Builder
    public KafkaClient(BigDecimal id, List<KafkaIncome> incomes) {
        this.id = id;
        this.incomes = incomes;
    }
}
