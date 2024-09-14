package com.example.rekazfinalproject.DTO;

import com.example.rekazfinalproject.Model.Project;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PropertyDTO {

    @NotEmpty(message = "Land title cannot be empty")
    private String landTitle;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Land area is required")
    private double landArea; // In square meters or another unit

    @NotNull(message = "Value of land is required")
    private double landValue; // Market value

    @NotEmpty(message = "Owner name cannot be empty")
    private String ownerName;

    @NotEmpty(message = "Project description should be not null")
    private String description;

    @NotNull(message = "Ownership start date is required")
    private LocalDate ownershipStartDate;


    private LocalDate ownershipEndDate;

    @NotEmpty(message = "Project title should be not null")
    private String title;

    @NotNull(message = "Project budget should be not null")
    private double budget;

    @NotEmpty(message = "Project type should be not null")
    private String projectType;


    private LocalDate publication_date;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Project.ProjectStatus status;
}
