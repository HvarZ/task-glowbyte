package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "APPLICATION")
public class Application {
    @Id
    @GeneratedValue
    @Column(name = "APPLICATION_ID")
    private long applicationId;

    @Column(name = "CREDIT_AMOUNT")
    private float creditAmount;

    @Column(name = "CREDIT_RATE")
    private float creditRate;

    @Column(name = "CREDIT_TERM")
    private int creditTerm;

    @ManyToOne
    private Client client;

    @OneToMany
    private Set<Income> income;

    public Application(float creditAmount, float creditRate, int creditTerm) {
        this.creditAmount = creditAmount;
        this.creditRate = creditRate;
        this.creditTerm = creditTerm;
    }
}
