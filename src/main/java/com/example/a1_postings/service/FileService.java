package com.example.a1_postings.service;

import com.example.a1_postings.entities.Item;
import com.example.a1_postings.entities.Login;
import com.example.a1_postings.entities.Posting;
import com.example.a1_postings.repo.ItemRepository;
import com.example.a1_postings.repo.LoginRepository;
import com.example.a1_postings.repo.PostingRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileService {

    private final LoginRepository loginRepository;
    private final PostingRepository postingRepository;
    private final ItemRepository itemRepository;

    public FileService(LoginRepository loginRepository, PostingRepository postingRepository, ItemRepository itemRepository) {
        this.loginRepository = loginRepository;
        this.postingRepository = postingRepository;
        this.itemRepository = itemRepository;
    }

    public void processFiles(String loginFilePath, String postingFilePath) throws IOException {
        List<Login> logins = null;
        try {
            logins = readLoginsFromFile(loginFilePath);
            for(Login login : logins){
                loginRepository.save(login);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        List<Posting> postings = readPostingsFromFile(postingFilePath);

        for (Posting posting : postings) {
            boolean authorized = isPostingAuthorized(posting.getUserName(), logins);
            posting.setAuthorized(authorized);
            postingRepository.save(posting);
            for (Item item : posting.getItems()) {
                item.setPosting(posting);
                itemRepository.save(item);
            }
        }
    }

    private List<Login> readLoginsFromFile(String filePath) throws IOException, CsvValidationException {
        List<Login> logins = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Login login = new Login();
                login.setApplication(line[0]);
                login.setAppAccountName(line[1]);
                login.setActive(Boolean.parseBoolean(line[2]));
                login.setJobTitle(line[3]);
                login.setDepartment(line[4]);
                logins.add(login);
            }
        }
        return logins;
    }

    private List<Posting> readPostingsFromFile(String filePath) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Posting> postings = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(parser).build()) {
            reader.readNext(); // skip headers
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                line[2] = line[2].replaceAll("\\s+", ""); // Remove all whitespace characters, including tabs
                line[3] = line[3].replaceAll("\\s+", "");
                Posting posting = new Posting();
                posting.setMatDoc(line[0]);
                posting.setDocDate(LocalDate.parse(line[2],formatter));
                posting.setPstngDate(LocalDate.parse(line[3],formatter));
                posting.setUserName(line[9].trim());
                postings.add(posting);

                Item item = new Item();
                item.setMaterialDescription(line[4].trim());
                item.setQuantity(Integer.parseInt(line[5].trim()));
                item.setBun(line[6].trim());
                line[7] = line[7].replaceAll(",",".");
                item.setAmountLc(Double.parseDouble(line[7].trim()));
                item.setCrcy(line[8].trim());
                posting.setItems(Collections.singletonList(item));
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return postings;
    }

    private boolean isPostingAuthorized(String userName, List<Login> logins) {
        for (Login login : logins) {
            if (login.getAppAccountName().equals(userName) && login.getActive()) {
                return true;
            }
        }
        return false;
    }
}

