package com.diario.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
  @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "E-mail inválido")
  String email,
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
           message = "Senha fraca: min 8, maiúscula, minúscula, número e símbolo")
  String password,
  @NotBlank(message = "Nome completo obrigatório")
  String fullName
) {}
