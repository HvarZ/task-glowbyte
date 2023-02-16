package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "INCOME")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "INCOME_ID")
    private long incomeId;

    @ManyToOne
    @Column(name = "CLIENT_ID")
    private long clientId;

    @OneToOne
    @Column(name = "APPLICATION_ID")
    private long applicationId;

    @Column(name = "MONTH")
    private Date month;

    @Column(name = "AMOUNT")
    private float amount;

    public Income(long clientId, long applicationId, Date month, float amount) {
        this.clientId = clientId;
        this.applicationId = applicationId;
        this.month = month;
        this.amount = amount;
    }
}
