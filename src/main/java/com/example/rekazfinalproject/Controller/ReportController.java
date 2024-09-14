package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Model.Report;
import com.example.rekazfinalproject.Service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/get")
    public ResponseEntity getAllReports() {
        return ResponseEntity.status(200).body(reportService.getAllReports());
    }

    @PostMapping("/create-report/{projectId}/{investorId}")
    public ResponseEntity createReport(
            @PathVariable Integer projectId,
            @PathVariable Integer investorId,
            @Valid @RequestBody String content) {

        reportService.createReport(projectId, investorId, content);
        return ResponseEntity.ok("Report Created Successfully!");
    }

    // Update a report by ID
    @PutMapping("/update/{id}")
    public ResponseEntity updateReport(@PathVariable Integer id, @RequestBody Report report) {
         reportService.updateReport(id, report);
        return ResponseEntity.status(200).body("Report Updated Successfully!");
    }

    // Delete a report by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReport(@PathVariable Integer id) {
        reportService.deleteReport(id);
        return ResponseEntity.status(200).body("Report Deleted Successfully!");
    }

    @GetMapping("/owner/{projectId}")
    public ResponseEntity getReportsByProjectId(@PathVariable Integer projectId) {
        List<Report> reports = reportService.getReportsByProjectId(projectId);
        return ResponseEntity.ok(reports);
    }


}