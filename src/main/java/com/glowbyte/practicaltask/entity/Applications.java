package com.glowbyte.practicaltask.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Applications")
public class Applications {
    @XmlElement(name = "Application")
    private final List<Application> applications;

    public Applications() {
        applications = new ArrayList<>();
    }

    @XmlTransient
    public List<Application> getApplications() {
        return applications;
    }
}
