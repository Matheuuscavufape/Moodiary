package com.diario.entry;

import com.diario.entry.dto.CreateEntryDTO;
import com.diario.entry.dto.EntryDTO;
import com.diario.entry.dto.UpdateEntryDTO;
import com.diario.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;   // <<< ADICIONE ESTE IMPORT
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EntryService {
  @Autowired private JournalEntryRepository repo;

  public EntryDTO create(User user, CreateEntryDTO dto) {
    JournalEntry e = new JournalEntry();
    e.setUser(user);
    e.setContent(dto.content().trim());
    e.setMood(dto.mood());
    e.setDraft(Boolean.TRUE.equals(dto.draft()));

    repo.save(e);
    return toDTO(e);
  }

  public Page<EntryDTO> search(User user, String q, Integer year, Integer month, Integer day, int page, int size) {
    return repo.search(user, emptyToNull(q), year, month, day, PageRequest.of(page, size)).map(this::toDTO);
  }

  public EntryDTO get(User user, UUID id) {
    JournalEntry e = repo.findById(id).filter(x -> x.getUser().getId().equals(user.getId())).orElseThrow();
    return toDTO(e);
  }

  public EntryDTO update(User user, UUID id, UpdateEntryDTO dto) {
    JournalEntry e = repo.findById(id).filter(x -> x.getUser().getId().equals(user.getId())).orElseThrow();
    e.setContent(dto.content().trim());
    e.setMood(dto.mood());
    e.setDraft(Boolean.TRUE.equals(dto.draft()));


    repo.save(e);
    return toDTO(e);
  }

  public void delete(User user, UUID id) {
    JournalEntry e = repo.findById(id).filter(x -> x.getUser().getId().equals(user.getId())).orElseThrow();
    repo.delete(e);
  }

  public List<Map<String, Object>> moodSummary(User user, Integer year, Integer month) {
    return repo.moodDailyAvg(user, year, month).stream().map(row -> {
      Map<String, Object> m = new java.util.HashMap<>();
      m.put("date", row[0].toString());
      m.put("mood", ((Number) row[1]).doubleValue());
      return m;
    }).toList();
  }

  private String emptyToNull(String q) { return (q == null || q.isBlank()) ? null : q; }

  private EntryDTO toDTO(JournalEntry e) {
    return new EntryDTO(e.getId(), e.getContent(), e.getMood(), e.isDraft(), e.getCreatedAt(), e.getUpdatedAt());
  }
}