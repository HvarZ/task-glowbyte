package com.glowbyte.practicaltask.entity.json_out;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OutClient {
    private BigDecimal id;
    private List<OutIncome> incomes;

    @Builder
    public OutClient(BigDecimal id, List<OutIncome> incomes) {
        this.id = id;
        this.incomes = incomes;
    }
}
