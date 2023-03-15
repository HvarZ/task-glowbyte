package com.glowbyte.practicaltask.entity.json_in;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class InClient {
    private BigDecimal id;
    private boolean solvent;

    @Builder
    public InClient(BigDecimal id, boolean solvent) {
        this.id = id;
        this.solvent = solvent;
    }
}
