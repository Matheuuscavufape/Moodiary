package com.diario.auth;

import com.diario.auth.dto.AuthResponse;
import com.diario.auth.dto.LoginDTO;
import com.diario.auth.dto.RegisterDTO;
import com.diario.security.JwtService;
import com.diario.user.User;
import com.diario.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authManager;
  private final JwtService jwtService;

  public AuthController(UserRepository users,
                        PasswordEncoder encoder,
                        AuthenticationManager authManager,
                        JwtService jwtService) {
    this.users = users;
    this.encoder = encoder;
    this.authManager = authManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {

    if (users.existsByEmail(dto.email())) {
      return ResponseEntity.status(409).body(Map.of("message", "E-mail já cadastrado."));
    }
    try {
      User u = new User();
      u.setEmail(dto.email());
      u.setFullName(dto.fullName());
      u.setPasswordHash(encoder.encode(dto.password()));
      users.save(u);

      return ResponseEntity
          .created(URI.create("/auth/login"))
          .body(Map.of("message", "Conta criada! Faça login para continuar."));
    } catch (DataIntegrityViolationException e) {

      return ResponseEntity.status(409).body(Map.of("message", "E-mail já cadastrado."));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO dto) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
    );
    String token = jwtService.generate((UserDetails) auth.getPrincipal());
    User u = users.findByEmail(dto.email()).orElseThrow(); 
    return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getEmail(), u.getFullName()));
  }
}