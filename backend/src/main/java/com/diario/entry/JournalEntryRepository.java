package com.diario.entry;

import com.diario.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, UUID> {

  @Query("""

  select e from JournalEntry e
  where e.user = :user
    and (:q is null or lower(e.content) like lower(concat('%', :q, '%')))
    and (:year is null or function('year', e.createdAt) = :year)
    and (:month is null or function('month', e.createdAt) = :month)
    and (:day is null or function('day', e.createdAt) = :day)
  order by e.createdAt desc
  """)
  Page<JournalEntry> search(User user, String q, Integer year, Integer month, Integer day, Pageable pageable);

  @Query("""

  select cast(e.createdAt as date) as dt, avg(e.mood) as avgMood
  from JournalEntry e
  where e.user = :user and e.mood is not null
    and (:year is null or function('year', e.createdAt) = :year)
    and (:month is null or function('month', e.createdAt) = :month)
  group by cast(e.createdAt as date)
  order by dt asc
  """)
  List<Object[]> moodDailyAvg(User user, Integer year, Integer month);
}
