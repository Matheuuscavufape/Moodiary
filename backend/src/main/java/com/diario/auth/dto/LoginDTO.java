package com.diario.auth.dto;

public record LoginDTO(
  @jakarta.validation.constraints.NotBlank String email,
  @jakarta.validation.constraints.NotBlank String password
) {}
