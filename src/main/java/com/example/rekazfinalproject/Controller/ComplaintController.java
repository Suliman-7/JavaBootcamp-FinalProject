package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Complaint;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/complaint")
public class ComplaintController {
    private final ComplaintService complaintService;
    @GetMapping("/get-all-complaints")
    public ResponseEntity getAllComplaint(){
        return ResponseEntity.status(200).body(complaintService.getAllComplaint());
    }

    @PostMapping("/send-complaint/investor-id")
    public ResponseEntity investorAddComplaint(@AuthenticationPrincipal User user, @Valid @RequestBody Complaint complaint){
        complaintService.investorSendComplaint(user.getId(),complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully send"));
    }
    @PostMapping("/send-complaint/owner-id")
    public ResponseEntity ownerAddComplaint(@AuthenticationPrincipal User user, @Valid @RequestBody Complaint complaint) {
        complaintService.ownerSendComplaint(user.getId(), complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully send"));
    }

    @PutMapping("/update-complaint/{compid}")
    public ResponseEntity updateComplaint (@AuthenticationPrincipal User user ,@PathVariable Integer compid ,@Valid @RequestBody Complaint complaint){
        complaintService.updateComplaint(user.getId(),compid,complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }

    @DeleteMapping("/delete-complaint/{compid}")
    public ResponseEntity deleteComplaint(@AuthenticationPrincipal User user, @PathVariable Integer compid){
        complaintService.deleteComplaint(user.getId(),compid);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully deleted"));
    }
    @PutMapping("/update-complaint-to-inprogress/{complaint}")
    public ResponseEntity startProcessingComplaint (@AuthenticationPrincipal User user ,@PathVariable Integer complaint ){
        complaintService.startProcessingComplaint(user.getId(),complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }
    @PutMapping("/update-complaint-to-resolved/{complaint}")
    public ResponseEntity resolveComplaint (@AuthenticationPrincipal User user ,@PathVariable Integer complaint ){
        complaintService.resolveComplaint(user.getId(),complaint);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint successfully updated"));
    }
}
