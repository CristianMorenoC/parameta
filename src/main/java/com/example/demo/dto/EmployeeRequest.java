package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
  @NotBlank(message = "Nombres son obligatorios")
  private String names;

  @NotBlank(message = "Apellidos son obligatorios")
  private String lastNames;

  @NotBlank(message = "Tipo de documento es obligatorio")
  private String documentType;

  @NotBlank(message = "Número de documento es obligatorio")
  private String documentNumber;

  @NotNull(message = "Fecha de nacimiento es obligatoria")
  @Past(message = "La fecha de nacimiento debe ser en el pasado")
  private LocalDate birthDate;

  @NotNull(message = "Fecha de vinculación es obligatoria")
  private LocalDate vinculationDate;

  @NotBlank(message = "Cargo es obligatorio")
  private String position;

  @NotNull(message = "Salario es obligatorio")
  private Double salary;
}
