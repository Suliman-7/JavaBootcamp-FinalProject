package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Land title cannot be empty")
    @Column(name = "land_title", columnDefinition = "varchar(100) not null")
    private String landTitle;

    @NotEmpty(message = "Location cannot be empty")
    @Column(name = "location", columnDefinition = "varchar(255) not null")
    private String location;

    @NotNull(message = "Land area is required")
    @Column(name = "land_area", columnDefinition = "decimal(10, 2) not null")
    private double landArea; // In square meters or another unit

    @NotNull(message = "Value of land is required")
    @Column(name = "land_value", columnDefinition = "decimal(15, 2) not null")
    private double landValue; // Market value

    @NotEmpty(message = "Owner name cannot be empty")
    @Column(name = "owner_name", columnDefinition = "varchar(100) not null")
    private String ownerName;

    @NotEmpty(message = "Project description should be not null")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @NotNull(message = "Ownership start date is required")
    @Column(name = "ownership_start_date", columnDefinition = "date not null")
    private LocalDate ownershipStartDate;

    @Column(name = "ownership_end_date", columnDefinition = "date")
    private LocalDate ownershipEndDate;




    @ManyToOne
    @JsonIgnore
    private Owner owner;


    //project one to one
    @OneToOne
    @MapsId
    @JsonIgnore
    private Project project;

}
