package com.example.demo.service;

import com.example.demo.client.SoapClient;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.VinculacionResponse;
import com.example.demo.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private SoapClient soapClient;

  @InjectMocks
  private EmployeeService employeeService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void processVinculation_HappyPath_ShouldReturnResponse() {
    EmployeeRequest request = EmployeeRequest.builder()
        .names("John")
        .lastNames("Doe")
        .birthDate(LocalDate.now().minusYears(25))
        .vinculationDate(LocalDate.now().minusYears(2))
        .documentType("CC")
        .documentNumber("123456")
        .position("Developer")
        .salary(5000.0)
        .build();

    VinculacionResponse response = employeeService.processVinculation(request);

    assertNotNull(response);
    assertEquals(25, response.getCurrentAge());
    assertEquals("2 años, 0 meses, 0 días", response.getVinculationTime());
    assertEquals("John", response.getNames());
  }

  @Test
  void processVinculation_Exactly18Years_ShouldReturnResponse() {
    EmployeeRequest request = EmployeeRequest.builder()
        .names("Jane")
        .lastNames("Doe")
        .birthDate(LocalDate.now().minusYears(18))
        .vinculationDate(LocalDate.now().minusMonths(6))
        .build();

    VinculacionResponse response = employeeService.processVinculation(request);

    assertEquals(18, response.getCurrentAge());
    assertTrue(response.getVinculationTime().contains("6 meses"));
  }

  @Test
  void processVinculation_Underage_ShouldThrowException() {
    EmployeeRequest request = EmployeeRequest.builder()
        .birthDate(LocalDate.now().minusYears(17))
        .vinculationDate(LocalDate.now().minusMonths(1))
        .build();

    BusinessException exception = assertThrows(BusinessException.class,
        () -> employeeService.processVinculation(request));

    assertEquals("El empleado debe ser mayor de edad (18 años).", exception.getMessage());
  }

  @Test
  void processVinculation_FutureVinculationDate_ShouldThrowException() {
    EmployeeRequest request = EmployeeRequest.builder()
        .birthDate(LocalDate.now().minusYears(25))
        .vinculationDate(LocalDate.now().plusDays(1))
        .build();

    BusinessException exception = assertThrows(BusinessException.class,
        () -> employeeService.processVinculation(request));

    assertEquals("La fecha de vinculación no puede ser futura.", exception.getMessage());
  }
}
