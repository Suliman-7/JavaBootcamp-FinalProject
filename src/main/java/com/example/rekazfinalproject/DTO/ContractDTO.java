package com.example.rekazfinalproject.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor


public class ContractDTO {


    private Integer projectId;

    @NotEmpty(message = "Terms should be not null")
    private String Terms;

    @NotEmpty(message = "Start Date should be not null")
    private LocalDate startDate ;

    @NotEmpty(message = "End Date should be not null")
    private LocalDate endDate ;

}
