package com.example.demo.service;

import com.example.demo.client.SoapClient;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.VinculacionResponse;
import com.example.demo.dto.soap.VinculacionSoapRequest;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final SoapClient soapClient;

  public VinculacionResponse processVinculation(EmployeeRequest request) {
    validateEmployee(request);

    int age = calculateAge(request.getBirthDate());
    if (age < 18) {
      throw new BusinessException("El empleado debe ser mayor de edad (18 años).");
    }

    // HU-03: Integrate SOAP Client
    VinculacionSoapRequest soapRequest = VinculacionSoapRequest.builder()
        .names(request.getNames())
        .lastNames(request.getLastNames())
        .documentType(request.getDocumentType())
        .documentNumber(request.getDocumentNumber())
        .position(request.getPosition())
        .salary(request.getSalary())
        .build();

    soapClient.callLegacySystem(soapRequest);

    String vinculationTime = calculateVinculationTime(request.getVinculationDate());

    return VinculacionResponse.builder()
        .names(request.getNames())
        .lastNames(request.getLastNames())
        .currentAge(age)
        .vinculationTime(vinculationTime)
        .build();
  }

  private void validateEmployee(EmployeeRequest request) {
    if (request.getVinculationDate().isAfter(LocalDate.now())) {
      throw new BusinessException("La fecha de vinculación no puede ser futura.");
    }
    if (request.getBirthDate().isAfter(LocalDate.now())) {
      throw new BusinessException("La fecha de nacimiento no puede ser futura.");
    }
  }

  private int calculateAge(LocalDate birthDate) {
    return Period.between(birthDate, LocalDate.now()).getYears();
  }

  private String calculateVinculationTime(LocalDate vinculationDate) {
    Period period = Period.between(vinculationDate, LocalDate.now());
    return String.format("%d años, %d meses, %d días",
        period.getYears(), period.getMonths(), period.getDays());
  }
}
