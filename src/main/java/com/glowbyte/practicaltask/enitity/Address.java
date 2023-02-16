package com.glowbyte.practicaltask.enitity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ADDRESS_ID")
    private long addressId;

    @OneToOne
    @Column(name = "CLIENT_ID")
    private long clientId;

    @OneToMany
    @Column(name = "APPLICATION_ID")
    private long applicationId;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "HOUSE")
    private String house;

    @Column(name = "BUILDING")
    private String building;

    @Column(name = "FLAT")
    private String flat;

    public Address(long clientId, long applicationId, String country, String city, String street, String house, String building, String flat) {
        this.clientId = clientId;
        this.applicationId = applicationId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
    }
}
