package com.example.a1_postings.controller;

import com.example.a1_postings.entities.Item;
import com.example.a1_postings.entities.Posting;
import com.example.a1_postings.service.PostingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    private final PostingService postingService;

    public ApiController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/postings/day")
    public List<Posting> getPostingsForDay(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = startDate.plusDays(1);
        List<Posting> result = postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly);
        return result;
    }

    @GetMapping("/postings/month")
    public List<Posting> getPostingsForMonth(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly);
    }

    @GetMapping("/postings/year")
    public List<Posting> getPostingsForYear(@RequestParam("year") int year, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly);
    }

    @GetMapping("/postings/quarter")
    public List<Posting> getPostingsForQuarter(@RequestParam("year") int year, @RequestParam("quarter") int quarter, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = getStartDateForQuarter(year, quarter);
        LocalDate endDate = getEndDateForQuarter(year, quarter);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly);
    }

    private LocalDate getStartDateForQuarter(int year, int quarter) {
        return LocalDate.of(year, (quarter - 1) * 3 + 1, 1);
    }

    private LocalDate getEndDateForQuarter(int year, int quarter) {
        return LocalDate.of(year, quarter * 3, 1).withDayOfMonth(1).plusMonths(1).minusDays(1);
    }

    @GetMapping("/items/day")
    public List<Item> getItemsForDay(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = startDate.plusDays(1);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly)
                .stream().flatMap(posting -> posting.getItems().stream()).collect(Collectors.toList());
    }

    @GetMapping("/items/month")
    public List<Item> getItemsForMonth(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly)
                .stream().flatMap(posting -> posting.getItems().stream()).collect(Collectors.toList());
    }

    @GetMapping("/items/year")
    public List<Item> getItemsForYear(@RequestParam("year") int year, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly)
                .stream().flatMap(posting -> posting.getItems().stream()).collect(Collectors.toList());
    }

    @GetMapping("/items/quarter")
    public List<Item> getItemsForQuarter(@RequestParam("year") int year, @RequestParam("quarter") int quarter, @RequestParam(value = "authorizedOnly", required = false, defaultValue = "false") boolean authorizedOnly) {
        LocalDate startDate = getStartDateForQuarter(year, quarter);
        LocalDate endDate = getEndDateForQuarter(year, quarter);
        return postingService.getPostingsForPeriod(startDate, endDate, authorizedOnly)
                .stream().flatMap(posting -> posting.getItems().stream()).collect(Collectors.toList());
    }
}
