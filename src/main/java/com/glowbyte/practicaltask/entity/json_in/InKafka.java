package com.glowbyte.practicaltask.entity.json_in;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class InKafka {
    private BigDecimal id;
    private List<InClient> clients;

    @Builder
    public InKafka(BigDecimal id, List<InClient> clients) {
        this.id = id;
        this.clients = clients;
    }
}
