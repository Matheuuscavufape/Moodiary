package com.diario.auth;

import com.diario.auth.dto.AuthResponse;
import com.diario.auth.dto.LoginDTO;
import com.diario.auth.dto.RegisterDTO;
import com.diario.security.JwtService;
import com.diario.user.User;
import com.diario.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired private UserRepository users;
  @Autowired private PasswordEncoder encoder;
  @Autowired private AuthenticationManager authManager;
  @Autowired private JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
    if (users.existsByEmail(dto.email())) {
      return ResponseEntity.status(409).body("Este e-mail já está cadastrado.");
    }
    User u = new User();
    u.setEmail(dto.email());
    u.setFullName(dto.fullName());
    u.setPasswordHash(encoder.encode(dto.password()));
    users.save(u);
    return ResponseEntity.status(201).body("Conta criada! Faça login para continuar.");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
    try {
      Authentication auth = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
      String token = jwtService.generate((UserDetails) auth.getPrincipal());
      User u = users.findByEmail(dto.email()).orElseThrow();
      return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getEmail(), u.getFullName()));
    } catch (Exception e) {
      return ResponseEntity.status(401).body("E-mail ou senha incorretos.");
    }
  }
}
