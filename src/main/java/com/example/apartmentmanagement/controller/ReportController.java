package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entities.Report;
import com.example.apartmentmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public Report createReport(@RequestBody String requestBody) {
        try {
            Report report = objectMapper.readValue(requestBody, Report.class);
            return reportService.createReport(report);
        } catch (Exception e) {
            throw new RuntimeException("Error processing request body: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public Report updateReport(@PathVariable Long id, @RequestBody String requestBody) {
        try {
            Report newReportData = objectMapper.readValue(requestBody, Report.class);
            return reportService.updateReport(id, newReportData);
        } catch (Exception e) {
            throw new RuntimeException("Error processing request body: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }
}