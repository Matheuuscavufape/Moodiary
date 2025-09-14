package com.diario.entry.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEntryDTO(
  @NotBlank String content,
  Integer mood,
  Boolean draft
) {}
