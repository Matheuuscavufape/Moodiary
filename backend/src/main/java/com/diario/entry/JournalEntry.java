package com.diario.entry;

import com.diario.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "journal_entries")
public class JournalEntry {
  @Id @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false)
  private User user;

  @Column(nullable = false, columnDefinition = "text")
  private String content;

  private Integer mood; // 1..5 (opcional)

  @Column(nullable = false)
  private boolean draft;

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  public UUID getId() { return id; }
  public User getUser() { return user; }
  public String getContent() { return content; }
  public Integer getMood() { return mood; }
  public boolean isDraft() { return draft; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }

  public void setUser(User user) { this.user = user; }
  public void setContent(String content) { this.content = content; }
  public void setMood(Integer mood) { this.mood = mood; }
  public void setDraft(boolean draft) { this.draft = draft; }
}
