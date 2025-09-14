package com.diario.entry.dto;

import java.time.Instant;
import java.util.UUID;

public record EntryDTO(
  UUID id,
  String content,
  Integer mood,
  boolean draft,
  Instant createdAt,
  Instant updatedAt
) {}
