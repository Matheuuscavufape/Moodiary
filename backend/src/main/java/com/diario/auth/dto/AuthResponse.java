package com.diario.auth.dto;

import java.util.UUID;

public record AuthResponse(String token, UUID id, String email, String fullName) {}
