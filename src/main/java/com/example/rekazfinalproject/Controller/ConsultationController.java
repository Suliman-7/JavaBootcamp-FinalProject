package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Consultation;
import com.example.rekazfinalproject.Service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consultation")
public class ConsultationController {
    private final ConsultationService consultationService;

    
    @GetMapping("/get/{id}")
    public ResponseEntity getAllConsultations(@PathVariable Integer id){
        return ResponseEntity.status(200).body(consultationService.getAllConsultations(id));
    }

    @PostMapping("/book-consultation/{ownerId}/{investorId}")
    public ResponseEntity addConsultation(@PathVariable Integer ownerId,@PathVariable Integer investorId,@Valid @RequestBody Consultation consultation){
        consultationService.bookConsultation(ownerId,investorId,consultation);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateConsultation (@PathVariable Integer id , @Valid @RequestBody Consultation consultation){
        consultationService.updateConsultation(id, consultation);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteConsultation(@PathVariable Integer id){
        consultationService.deleteConsultation(id);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully deleted"));
    }
    @PutMapping("/extend-duration/{ownerId}/{conId}/{duration}")
    public ResponseEntity extendConsultationDuration(@PathVariable Integer ownerId,@PathVariable Integer conId,@PathVariable double duration){
        consultationService.extendConsultationDuration(ownerId,conId,duration);
        return ResponseEntity.status(200).body(new ApiResponse("The duration has been updated successfully."));
    }
    @PutMapping("/canceled/owner-id/{ownerid}/con-id/{conid}")
    public ResponseEntity ownerCanceledConsultation(@PathVariable Integer ownerid,@PathVariable Integer conid){
        consultationService.ownerCanceledConsultation(ownerid,conid);
        return ResponseEntity.status(200).body(new ApiResponse("Owner canceled consultation "));
    }
    @PutMapping("/canceled/investor-id/{investorid}/con-id/{conid}")
    public ResponseEntity  InvestorCanceledConsultation(@PathVariable Integer investorid,@PathVariable Integer conid){
        consultationService.investorCanceledConsultation(investorid,conid);
        return ResponseEntity.status(200).body(new ApiResponse("Investor canceled consultation"));
    }

    @GetMapping("/get-available-consultation/{owner}/{investor}")
    public ResponseEntity getAvailableConsultationDates(@PathVariable Integer owner,@PathVariable Integer investor){
        return ResponseEntity.status(200).body(consultationService.getAvailableConsultationDatesForInvestor(owner,investor));
    }
    @GetMapping("/get-owner-consultation/{owner}")
    public ResponseEntity listConsultationForOwner(@PathVariable Integer owner){
        return ResponseEntity.status(200).body(consultationService.listConsultationForOwner(owner));
    }

    @GetMapping("/get-investor-consultation/{investor}")
    public ResponseEntity listConsultationForInvestor(@PathVariable Integer investor){
        return ResponseEntity.status(200).body(consultationService.listConsultationsForInvestor(investor));
    }
}
