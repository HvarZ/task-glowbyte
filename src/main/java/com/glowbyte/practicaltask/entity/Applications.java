package com.glowbyte.practicaltask.entity;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

@XmlRootElement(name = "Applications")
public class Applications {
    @XmlElement(name = "Application")
    private final Set<Application> applications;

    public Applications() {
        applications = new HashSet<>();
    }

    @XmlTransient
    public Set<Application> getApplications() {
        return applications;
    }
}
