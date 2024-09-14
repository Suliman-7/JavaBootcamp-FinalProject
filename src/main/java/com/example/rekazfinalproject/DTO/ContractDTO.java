package com.example.rekazfinalproject.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor


public class ContractDTO {

    private Integer projectId;

    private String Terms;

    private LocalDate startDate ;

    private LocalDate endDate ;

    private String status ;
}
