package com.example.a1_postings.repo;

import com.example.a1_postings.entities.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {

    List<Posting> findByDocDateBetween(LocalDate startDate, LocalDate endDate);

    List<Posting> findByDocDateBetweenAndAuthorizedIsTrue(LocalDate startDate, LocalDate endDate);
}
