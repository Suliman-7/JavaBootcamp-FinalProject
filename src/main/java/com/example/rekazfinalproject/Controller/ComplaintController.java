package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Complaint;
import com.example.rekazfinalproject.Service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/complaint")
public class ComplaintController {
    private final ComplaintService complaintService;
     @GetMapping("/get")
    public ResponseEntity getAllComplaint(){
        return ResponseEntity.status(200).body(complaintService.getAllComplaint());
    }

    @PostMapping("/send-complaint/investor-id/{investorId}")
    public ResponseEntity investorAddComplaint(@PathVariable Integer investorId,@Valid @RequestBody Complaint complaint){
        complaintService.investorSendComplaint(investorId,complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully send"));
    }
@PostMapping("/send-complaint/owner-id/{owner}")
public ResponseEntity ownerAddComplaint(@PathVariable Integer owner, @Valid @RequestBody Complaint complaint) {
    complaintService.ownerSendComplaint(owner, complaint);
    return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully send"));
}

    @PutMapping("/update/{id}")
    public ResponseEntity updateComplaint (@PathVariable Integer id , @Valid @RequestBody Complaint complaint){
        complaintService.updateComplaint(id, complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComplaint(@PathVariable Integer id){
        complaintService.deleteComplaint(id);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully deleted"));
    }
    @PutMapping("/update-complaint-to-inprogress/{admin}/{complaint}")
    public ResponseEntity startProcessingComplaint (@PathVariable Integer admin ,@PathVariable Integer complaint ){
        complaintService.startProcessingComplaint(admin,complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }
    @PutMapping("/update-complaint-to-resolved/{admin}/{complaint}")
    public ResponseEntity resolveComplaint (@PathVariable Integer admin ,@PathVariable Integer complaint ){
        complaintService.resolveComplaint(admin,complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }
}
