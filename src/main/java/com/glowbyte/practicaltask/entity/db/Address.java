package com.glowbyte.practicaltask.entity.db;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQUENCE")
    @SequenceGenerator(name = "ADDRESS_SEQUENCE", sequenceName = "ADDRESS_SEQUENCE", allocationSize = 1)
    @Column(name = "ADDRESS_ID")
    @XmlTransient
    private BigDecimal addressId;
    @Column(name = "COUNTRY")
    @XmlAttribute(name = "Country")
    private String country;

    @Column(name = "CITY")
    @XmlAttribute(name = "City")
    private String city;

    @Column(name = "STREET")
    @XmlAttribute(name = "Street")
    private String street;

    @Column(name = "HOUSE")
    @XmlAttribute(name = "House")
    private String house;

    @Column(name = "BUILDING")
    @XmlAttribute(name = "Building")
    private String building;

    @Column(name = "FLAT")
    @XmlAttribute(name = "Flat")
    private String flat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICATION_ID")
    @XmlTransient
    private Application application;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    @XmlTransient
    private Client client;


    @Builder
    public Address(BigDecimal addressId, String country, String city, String street, String house, String building, String flat) {
        this.addressId = addressId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
    }
}
