package com.glowbyte.practicaltask.entity.db;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "APPLICATION")
@XmlRootElement(name = "Application")
@XmlAccessorType(XmlAccessType.FIELD)
public class Application {
    @Id
    @Column(name = "APPLICATION_ID")
    @XmlAttribute(name = "Id")
    private BigDecimal applicationId;

    @XmlAttribute(name = "CreditAmount")
    @Column(name = "CREDIT_AMOUNT")
    private BigDecimal creditAmount;

    @XmlAttribute(name = "CreditRate")
    @Column(name = "CREDIT_RATE")
    private BigDecimal creditRate;

    @XmlAttribute(name = "CreditTerm")
    @Column(name = "CREDIT_TERM")
    private BigDecimal creditTerm;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    @XmlElementWrapper(name = "Clients")
    @XmlElement(name = "Client")
    private Set<Client> client;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    @XmlTransient
    private Set<Address> address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    @XmlElementWrapper(name = "Incomes")
    @XmlElement(name = "Income")
    private Set<Income> income;

    @Builder
    public Application(BigDecimal applicationId, BigDecimal creditAmount, BigDecimal creditRate, BigDecimal creditTerm, Set<Client> client, Set<Income> income) {
        this.applicationId = applicationId;
        this.creditAmount = creditAmount;
        this.creditRate = creditRate;
        this.creditTerm = creditTerm;
        this.client = client;
        this.income = income;
    }

    public Application(BigDecimal applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return applicationId.equals(that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId);
    }
}
