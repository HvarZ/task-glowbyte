package com.glowbyte.practicaltask.entity.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class KafkaIncome {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
    private Date month;
    private BigDecimal amount;

    @Builder
    public KafkaIncome(Date month, BigDecimal amount) {
        this.month = month;
        this.amount = amount;
    }
}
