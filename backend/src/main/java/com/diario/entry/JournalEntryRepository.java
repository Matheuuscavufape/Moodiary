package com.diario.entry;

import com.diario.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, UUID> {

  @Query("""
    select e from JournalEntry e
    where e.user = :user
      and (:q is null or lower(e.content) like lower(concat('%', :q, '%')))
      and (:from is null or e.createdAt >= :from)
      and (:to   is null or e.createdAt <  :to)
    order by e.createdAt desc
  """)
  Page<JournalEntry> search(
      @Param("user") User user,
      @Param("q") String q,
      @Param("from") Instant from,
      @Param("to") Instant to,
      Pageable pageable
  );

  @Query("""
    select cast(e.createdAt as date) as dt, avg(e.mood) as avgMood
    from JournalEntry e
    where e.user = :user and e.mood is not null
      and (:from is null or e.createdAt >= :from)
      and (:to   is null or e.createdAt <  :to)
    group by cast(e.createdAt as date)
    order by dt asc
  """)
  List<Object[]> moodDailyAvg(
      @Param("user") User user,
      @Param("from") Instant from,
      @Param("to") Instant to
  );
}