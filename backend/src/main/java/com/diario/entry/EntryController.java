package com.diario.entry;

import com.diario.entry.dto.CreateEntryDTO;
import com.diario.entry.dto.EntryDTO;
import com.diario.entry.dto.UpdateEntryDTO;
import com.diario.user.User;
import com.diario.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/entries")
public class EntryController {
  @Autowired private EntryService service;
  @Autowired private UserRepository users;

  private User currentUser(UserDetails ud) { return users.findByEmail(ud.getUsername()).orElseThrow(); }

  @PostMapping
  public ResponseEntity<EntryDTO> create(@AuthenticationPrincipal UserDetails ud, @Valid @RequestBody CreateEntryDTO dto) {
    return ResponseEntity.status(201).body(service.create(currentUser(ud), dto));
  }

  @GetMapping
  public ResponseEntity<Page<EntryDTO>> list(
      @AuthenticationPrincipal UserDetails ud,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Integer day,
      @RequestParam(required = false) Integer month,
      @RequestParam(required = false) Integer year,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(service.search(currentUser(ud), q, year, month, day, page, size));
  }

  @GetMapping("/{id}")
  public EntryDTO get(@AuthenticationPrincipal UserDetails ud, @PathVariable UUID id) {
    return service.get(currentUser(ud), id);
  }

  @PutMapping("/{id}")
  public EntryDTO update(@AuthenticationPrincipal UserDetails ud, @PathVariable UUID id, @Valid @RequestBody UpdateEntryDTO dto) {
    return service.update(currentUser(ud), id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails ud, @PathVariable UUID id) {
    service.delete(currentUser(ud), id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/mood-summary")
  public List<Map<String, Object>> moodSummary(@AuthenticationPrincipal UserDetails ud,
      @RequestParam Integer year, @RequestParam Integer month) {
    return service.moodSummary(currentUser(ud), year, month);
  }
}
