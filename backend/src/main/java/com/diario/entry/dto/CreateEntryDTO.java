package com.diario.entry.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEntryDTO(
  @NotBlank String content,
  Integer mood,
  Boolean draft
) {}
