package com.example.a1_postings.service;

import com.example.a1_postings.entities.Posting;
import com.example.a1_postings.repo.PostingRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostingService{

    private final PostingRepository postingRepository;

    public PostingService(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public List<Posting> getPostingsForPeriod(LocalDate startDate, LocalDate endDate, boolean authorizedOnly) {
        if (authorizedOnly) {
            return postingRepository.findByDocDateBetweenAndAuthorizedIsTrue(startDate, endDate);
        } else {
            List<Posting> result = postingRepository.findByDocDateBetween(startDate, endDate);
            return result;
        }
    }


}
