package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.InvestorDTO;
import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Service.AvailableDateService;
import com.example.rekazfinalproject.Service.BidService;
import com.example.rekazfinalproject.Service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/investor")
@RequiredArgsConstructor
public class InvestorController {

    //*** All CRUD Done by Danah ****
    private final InvestorService investorService;
    private final AvailableDateService availableDateService;

    @GetMapping("/get")
    public ResponseEntity getAllInvestor(){
        return ResponseEntity.status(200).body(investorService.getAllInvestor());
    }

    @PostMapping("/register")
    public ResponseEntity registerInvestor(@Valid @RequestBody InvestorDTO investorDTO) {
        investorService.registerInvestor(investorDTO);
        return ResponseEntity.status(200).body("Investor registered successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateInvestor(@PathVariable Integer id, @Valid @RequestBody InvestorDTO investorDTO) {
        investorService.updateInvestor(id, investorDTO);
        return ResponseEntity.status(200).body("Investor updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteInvestor(@PathVariable Integer id) {
        investorService.deleteInvestor(id);
        return ResponseEntity.status(200).body("Investor deleted successfully");
    }


    @PostMapping("/add-bid/{investorId}/{projectId}")
    public ResponseEntity addBid(@PathVariable int investorId  , @PathVariable int projectId , @Valid @RequestBody Bid bid) {
        investorService.addBid(investorId,projectId,bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid added successfully"));
    }

    @PutMapping("/edit-bid/{investorId}/{bidId}")
    public ResponseEntity editBid(@PathVariable int investorId  , @PathVariable int bidId , @Valid @RequestBody Bid bid){
        investorService.editBid(investorId,bidId,bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid edited successfully"));
    }

    @PostMapping("/add-available-date/{investorId}")
    public ResponseEntity addAvailableDate( @PathVariable int investorId , @Valid @RequestBody AvailableDate availableDate) {
        availableDateService.addAvailableDate(investorId,availableDate);
        return ResponseEntity.status(200).body(new ApiResponse("Available date added successfully"));
    }

}
