package com.glowbyte.practicaltask.entity.json_in;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InClient {
    private BigDecimal id;
    private boolean solvent;

    @Builder
    public InClient(BigDecimal id, boolean solvent) {
        this.id = id;
        this.solvent = solvent;
    }
}
