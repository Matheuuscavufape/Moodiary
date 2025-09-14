package com.diario.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
  @Id @GeneratedValue
  private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private String role = "ROLE_USER";

  @CreationTimestamp
  private Instant createdAt;

  public UUID getId() { return id; }
  public String getEmail() { return email; }
  public String getFullName() { return fullName; }
  public String getPasswordHash() { return passwordHash; }
  public String getRole() { return role; }
  public Instant getCreatedAt() { return createdAt; }

  public void setEmail(String email) { this.email = email; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public void setRole(String role) { this.role = role; }
}
