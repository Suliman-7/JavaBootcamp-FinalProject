package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Service.AvailableDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/availabledates")


public class AvailableDateController {

    private final AvailableDateService availableDateService;

    @GetMapping("/get-all-available-dates")
    public ResponseEntity getAllAvailableDates(){
        return ResponseEntity.status(200).body(availableDateService.getAllAvailableDate());
    }

    @GetMapping("/get-investor-available-dates/{investorId}")
    public ResponseEntity getInvestorAvailableDates(@PathVariable Integer investorId){
        return ResponseEntity.status(200).body(availableDateService.getInvestorAvailableDates(investorId));
    }
}
