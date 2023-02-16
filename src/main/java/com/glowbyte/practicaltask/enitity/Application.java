package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "APPLICATION")
public class Application {
    @Id
    @GeneratedValue
    @Column
    private long applicationId;

    @Column(name = "CREDIT_AMOUNT")
    private float creditAmount;

    @Column(name = "CREDIT_RATE")
    private float creditRate;

    @Column(name = "CREDIT_TERM")
    private int creditTerm;

    public Application(float creditAmount, float creditRate, int creditTerm) {
        this.creditAmount = creditAmount;
        this.creditRate = creditRate;
        this.creditTerm = creditTerm;
    }
}
