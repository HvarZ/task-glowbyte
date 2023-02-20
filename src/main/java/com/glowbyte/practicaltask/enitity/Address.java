package com.glowbyte.practicaltask.enitity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ADDRESS_ID")
    private long addressId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @OneToMany
    @Column(name = "APPLICATION_ID")
    private Set<Application> applicationId;

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

    public Address(Set<Application> applicationId, String country, String city, String street, String house, String building, String flat) {
        this.applicationId = applicationId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
    }
}
