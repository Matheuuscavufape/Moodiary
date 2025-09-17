package com.diario.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
  @jakarta.validation.constraints.NotBlank
  @jakarta.validation.constraints.Email(message = "E-mail inválido")
  String email,

  @jakarta.validation.constraints.NotBlank
  @jakarta.validation.constraints.Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
    message = "Senha fraca: min 8, maiúscula, minúscula, número e símbolo"
  )
  String password,

  @jakarta.validation.constraints.NotBlank(message = "Nome completo obrigatório")
  String fullName
) {}
