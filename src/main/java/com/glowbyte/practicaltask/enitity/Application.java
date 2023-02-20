package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@ToString
@Table(name = "APPLICATION")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Application {
    @Id
    @GeneratedValue
    @Column(name = "APPLICATION_ID")
    @XmlAttribute(name = "Id")
    private long applicationId;

    @XmlAttribute(name = "CreditAmount")
    @Column(name = "CREDIT_AMOUNT")
    private float creditAmount;

    @XmlAttribute(name = "CreditRate")
    @Column(name = "CREDIT_RATE")
    private float creditRate;

    @XmlAttribute(name = "CreditRate")
    @Column(name = "CREDIT_TERM")
    private int creditTerm;

    @OneToMany
    @XmlElement(name = "Clients")
    private Set<Client> client;

    @OneToMany
    @XmlElement(name = "Incomes")
    private Set<Income> income;

    public Application(float creditAmount, float creditRate, int creditTerm) {
        this.creditAmount = creditAmount;
        this.creditRate = creditRate;
        this.creditTerm = creditTerm;
    }
}
