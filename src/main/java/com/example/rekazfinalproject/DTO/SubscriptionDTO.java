package com.example.rekazfinalproject.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SubscriptionDTO {

    @NotNull(message = "Subscription duration should be not empty ")
    private int duration ;

    @NotEmpty(message = "Subscription type should be not empty ")
    @Pattern(regexp = "^(Standard|Premium)$")
    private String type ;
}
