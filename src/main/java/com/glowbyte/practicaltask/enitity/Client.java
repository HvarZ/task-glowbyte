package com.glowbyte.practicaltask.enitity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    @XmlAttribute(name = "Id")
    private long clientId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICATION_ID")
    @XmlTransient
    private Application applications;

    @Column(name = "FIRSTNAME")
    @XmlAttribute(name = "FirstName")
    private String firstname;


    @Column(name = "SURNAME")
    @XmlAttribute(name = "SurName")
    private String surname;

    @Column(name = "LASTNAME")
    @XmlAttribute(name = "LastName")
    private String lastname;

    @Column(name = "BIRTHDATE")
    @XmlAttribute(name = "BirthDate")
    private Date birthdate;

    @Column(name = "BIRTHPLACE")
    @XmlAttribute(name = "BirthPlace")
    private String birthplace;

    @OneToMany(mappedBy = "client")
    @XmlElement(name = "Addresses")
    private Set<Address> address;

    @OneToMany
    @XmlTransient
    private Set<Income> income;


    public Client(String firstname, String surname, String lastname, Date birthdate, String birthplace) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }
}
