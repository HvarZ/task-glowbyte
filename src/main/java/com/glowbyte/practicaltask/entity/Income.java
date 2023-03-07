package com.glowbyte.practicaltask.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "INCOME")
@XmlAccessorType(XmlAccessType.FIELD)
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INCOME_SEQUENCE")
    @SequenceGenerator(name = "INCOME_SEQUENCE", sequenceName = "INCOME_SEQUENCE", allocationSize = 1)
    @Column(name = "INCOME_ID")
    @XmlTransient
    private BigDecimal incomeId;

    @Column(name = "CLIENT_ID")
    @XmlAttribute(name = "CLientId")
    private BigDecimal clientId;

    @Column(name = "MONTH")
    @XmlAttribute(name = "Month")
    private Date month;

    @Column(name = "AMOUNT")
    @XmlAttribute(name = "Amount")
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    @XmlTransient
    private Application application;

    @Builder
    public Income(BigDecimal clientId, Date month, BigDecimal amount) {
        this.clientId = clientId;
        this.month = month;
        this.amount = amount;
    }

    public Income(BigDecimal incomeId) {
        this.incomeId = incomeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Income income = (Income) o;

        if (!Objects.equals(incomeId, income.incomeId)) return false;
        if (!clientId.equals(income.clientId)) return false;
        return month.equals(income.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeId);
    }
}
