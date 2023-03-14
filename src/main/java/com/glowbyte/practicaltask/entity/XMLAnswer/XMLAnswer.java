package com.glowbyte.practicaltask.entity.XMLAnswer;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLAnswer {
    @XmlElement(name = "Application")
    private Application application;

    @XmlElement(name = "Decision")
    private Decision decision;

    @Builder
    public XMLAnswer(Application application, Decision decision) {
        this.application = application;
        this.decision = decision;
    }
}
