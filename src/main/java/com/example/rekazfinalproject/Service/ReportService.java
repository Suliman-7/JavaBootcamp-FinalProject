package com.example.rekazfinalproject.Service;


import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.Report;
import com.example.rekazfinalproject.Repository.InvestorRepository;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import com.example.rekazfinalproject.Repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final InvestorRepository investorRepository;
    private final ProjectRepository projectRepository;


    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }



    public void createReport(Integer projectId, Integer investorId, String content) {
        Investor investor = investorRepository.findInvestorById(investorId);
        if (investor == null) {
            throw new ApiException("not found investor");
        }


        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ApiException("not found project");
        }
        Report report = new Report();
        report.setCreatedAt(LocalDate.now());
        report.setContent(content);
        report.setInvestor(investor);
        report.setProject(project);
        reportRepository.save(report);

    }

    // Update a report
    public void updateReport(Integer id, Report report) {
        Report r = reportRepository.findReportById(id);
        if (r == null) {
            throw new ApiException("not found report");
        }
        r.setCreatedAt(report.getCreatedAt());
        r.setContent(report.getContent());
        reportRepository.save(r);
    }

    // Delete report
    public void deleteReport(Integer id) {
        Report report = reportRepository.findReportById(id);
        if (report == null) {
            throw new ApiException("not found report");
        }
        reportRepository.delete(report);
    }

    public List<Report> getReportsByProjectId(Integer projectId) {
        return reportRepository.findByProjectId(projectId);
    }
}