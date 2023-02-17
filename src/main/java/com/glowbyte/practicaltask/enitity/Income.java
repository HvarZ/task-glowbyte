package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "INCOME")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "INCOME_ID")
    private long incomeId;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    private Application application;

    @Column(name = "MONTH")
    private Date month;

    @Column(name = "AMOUNT")
    private float amount;

    public Income(Date month, float amount) {
        this.month = month;
        this.amount = amount;
    }
}
