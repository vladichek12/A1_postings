package com.example.a1_postings;

import com.example.a1_postings.service.FileService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FileServiceRunner implements ApplicationRunner {

    private final FileService fileService;

    public FileServiceRunner(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String loginFilePath = "src/main/resources/data/logins.csv";
        String postingFilePath = "src/main/resources/data/postings.csv";
        fileService.processFiles(loginFilePath, postingFilePath);
    }
}

