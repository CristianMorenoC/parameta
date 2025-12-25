package com.example.demo.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "message"
})
@XmlRootElement(name = "VinculacionResponse", namespace = "http://example.com/service")
public class VinculacionSoapResponse {

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String status;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String message;
}
