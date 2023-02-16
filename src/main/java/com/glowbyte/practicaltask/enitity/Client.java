package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private long clientId;

    @OneToMany
    @Column(name = "APPLICATION_ID")
    private long applicationId;

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


    public Client(long applicationId, String firstname, String surname, String lastname, Date birthdate, String birthplace) {
        this.applicationId = applicationId;
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }
}
