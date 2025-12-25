package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculacionResponse {
  private String vinculationTime;
  private int currentAge;
  // Fields from request to echo back as per usual patterns or requirements
  private String names;
  private String lastNames;
}
