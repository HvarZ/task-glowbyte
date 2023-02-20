package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "INCOME")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "INCOME_ID")
    @XmlAttribute(name = "Income CLientId")
    private long incomeId;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    @XmlTransient
    private Client client;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    @XmlTransient
    private Application application;

    @Column(name = "MONTH")
    @XmlAttribute(name = "Month")
    private Date month;

    @Column(name = "AMOUNT")
    @XmlAttribute(name = "Amount")
    private float amount;

    public Income(Date month, float amount) {
        this.month = month;
        this.amount = amount;
    }
}
