package com.example.a1_postings.service;

import com.example.a1_postings.repo.ItemRepository;
import com.example.a1_postings.repo.LoginRepository;
import com.example.a1_postings.repo.PostingRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class CleanService {
    private final LoginRepository loginRepository;
    private final PostingRepository postingRepository;
    private final ItemRepository itemRepository;

    public CleanService(LoginRepository loginRepository, PostingRepository postingRepository, ItemRepository itemRepository) {
        this.loginRepository = loginRepository;
        this.postingRepository = postingRepository;
        this.itemRepository = itemRepository;
    }

    @PreDestroy
    public void cleanUp() {
        itemRepository.deleteAll();
        loginRepository.deleteAll();
        postingRepository.deleteAll();
    }
}
