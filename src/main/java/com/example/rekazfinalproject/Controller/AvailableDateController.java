package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Service.AvailableDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/availabledate")

    // All CRUD by suliman 


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

    @PutMapping("/update-available-date/{id}")
    public ResponseEntity updateAvailableDate(@PathVariable Integer id, @Valid @RequestBody AvailableDate availableDate){
        availableDateService.updateAvailableDate(id, availableDate);
        return ResponseEntity.status(200).body("Available Date updated successfully");
    }

    @DeleteMapping("/delete-available-date/{id}")
    public ResponseEntity deleteAvailableDate(@PathVariable Integer id){
        availableDateService.deleteAvailableDate(id);
        return ResponseEntity.status(200).body("Available Date deleted successfully");
    }
}
