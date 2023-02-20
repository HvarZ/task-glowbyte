package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private long clientId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICATION_ID")
    private Application applications;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "BIRTHDATE")
    private Date birthdate;

    @Column(name = "BIRTHPLACE")
    private String birthplace;

    @OneToMany(mappedBy = "client")
    private Set<Address> address;

    @OneToMany
    private Set<Income> income;


    public Client(String firstname, String surname, String lastname, Date birthdate, String birthplace) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }
}
