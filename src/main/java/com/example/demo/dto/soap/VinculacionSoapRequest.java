package com.example.demo.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "names",
    "lastNames",
    "documentType",
    "documentNumber",
    "position",
    "salary"
})
@XmlRootElement(name = "VinculacionRequest", namespace = "http://example.com/service")
public class VinculacionSoapRequest {

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String names;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String lastNames;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String documentType;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String documentNumber;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private String position;

  @XmlElement(namespace = "http://example.com/service", required = true)
  private Double salary;
}
