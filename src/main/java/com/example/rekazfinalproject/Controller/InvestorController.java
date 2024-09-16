package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.InvestorDTO;
import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.AvailableDateService;
import com.example.rekazfinalproject.Service.BidService;
import com.example.rekazfinalproject.Service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity updateInvestor(@AuthenticationPrincipal User user,@PathVariable Integer id, @Valid @RequestBody InvestorDTO investorDTO) {
        investorService.updateInvestor(user.getId(),id, investorDTO);
        return ResponseEntity.status(200).body("Investor updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteInvestor(@AuthenticationPrincipal User user,@PathVariable Integer id) {
        investorService.deleteInvestor(user.getId(),id);
        return ResponseEntity.status(200).body("Investor deleted successfully");
    }


    @PostMapping("/add-bid/{projectId}")
    public ResponseEntity addBid(@AuthenticationPrincipal User user  , @PathVariable int projectId , @Valid @RequestBody Bid bid) {
        investorService.addBid(user.getId(),projectId,bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid added successfully"));
    }

    @PutMapping("/edit-bid/{bidId}")
    public ResponseEntity editBid(@AuthenticationPrincipal User user  , @PathVariable int bidId , @Valid @RequestBody Bid bid){
        investorService.editBid(user.getId(),bidId,bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid edited successfully"));
    }

    @PostMapping("/add-available-date")
    public ResponseEntity addAvailableDate( @AuthenticationPrincipal User user , @Valid @RequestBody AvailableDate availableDate) {
        availableDateService.addAvailableDate(user.getId(),availableDate);
        return ResponseEntity.status(200).body(new ApiResponse("Available date added successfully"));
    }

    // Suliman

    @GetMapping("/get-my-projects")
    public ResponseEntity getMyProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(investorService.getMyProjects(user.getId()));
    }

    // Suliman

    @GetMapping("get-owner-projects")
    public ResponseEntity getOwnerProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(investorService.getOwnerProject(user.getId()));
    }

    // Suliman
    @GetMapping("/show-highest-investors-rate")
    public ResponseEntity showHighestRate() {
        return ResponseEntity.status(200).body(investorService.showHighestInvestorsRate());
    }

    @GetMapping("/get-investor-by-owner")
    public ResponseEntity lisInvestorCompanyByOwner(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(investorService.listInvestorCompanyByOwner(user.getId()));
    }

}
