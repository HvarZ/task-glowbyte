package com.glowbyte.practicaltask.entity.XMLAnswer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Application {
    @XmlAttribute(name = "Id")
    private BigDecimal id;

    public Application(BigDecimal id) {
        this.id = id;
    }
}
