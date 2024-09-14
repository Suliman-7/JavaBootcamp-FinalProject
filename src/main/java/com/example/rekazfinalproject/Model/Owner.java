package com.example.rekazfinalproject.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Owner {

    //*** All Done by Danah ****

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Commercial Register must not be null")
    @Positive(message = "Commercial Register must be a positive number")
    private int commercialRegister;

    @NotEmpty(message = "Scope of Work must not be blank")
    @Size(min = 5, max = 100, message = "Scope of Work must be between 5 and 100 characters")
    private String scopeOfWork;

    @PositiveOrZero(message = "Number of Projects must be zero or positive")
    private int numOfProject;

    private LocalDate createdAt;

    private double discountPercentage = 0 ;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Complaint> complaints;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "owner")
    private Set<Project> projects;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Consultation> consultations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Property> properties;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Rating> ratings;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "owner")
    private Subscription subscription;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Question> questions;

    
//    @OneToMany(cascade = CascadeType.ALL , mappedBy = "owner")
//    private Set<Project> projects;


}
