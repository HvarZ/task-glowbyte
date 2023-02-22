package com.glowbyte.practicaltask.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    @XmlAttribute(name = "Id")
    private BigDecimal clientId;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICATION_ID")
    @XmlTransient
    private Application applications;

    @OneToMany(mappedBy = "client")
    @XmlElementWrapper(name = "Addresses")
    private Set<Address> address;

    public Client(String firstname, String surname, String lastname, Date birthdate, String birthplace) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }

    @Builder
    public Client(BigDecimal clientId, String firstname, String surname, String lastname, Date birthdate, String birthplace) {
        this.clientId = clientId;
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }

    public Client(BigDecimal clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId.equals(client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
