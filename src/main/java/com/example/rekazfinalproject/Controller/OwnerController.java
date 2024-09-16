package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    //*** All CRUD Done by Danah ****
    @GetMapping("/get-all-owners")
    public ResponseEntity getAllOwners(){
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PostMapping("/register-owner")
    public ResponseEntity registerOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.registerOwner(ownerDTO);
        return ResponseEntity.status(200).body("Owner registered successfully");
    }

    @PutMapping("/update-owner")
    public ResponseEntity updateOwner(@AuthenticationPrincipal User user, @Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(user.getOwner().getId(), ownerDTO);
        return ResponseEntity.status(200).body("Owner updated successfully");
    }

    @DeleteMapping("/delete-owner")
    public ResponseEntity deleteOwner(@AuthenticationPrincipal User user) {
        ownerService.deleteOwner(user.getOwner().getId());
        return ResponseEntity.status(200).body("Owner deleted successfully");
    }


    @GetMapping("/get-my-projects")
    public ResponseEntity getMyProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(ownerService.getMyProjects(user.getOwner().getId()));
    }



    @PutMapping("/approve-bid/{bidId}")
    public ResponseEntity approveBid(@AuthenticationPrincipal User user, @PathVariable int bidId) {
        ownerService.approveBid(user.getOwner().getId() , bidId);
        return ResponseEntity.status(200).body("Bid approved successfully");
    }

    @PutMapping("/reject-bid/{bidId}")
    public ResponseEntity rejectBid(@AuthenticationPrincipal User user, @PathVariable int bidId , @RequestBody String comment) {
        ownerService.rejectBid(user.getOwner().getId() , bidId , comment);
        return ResponseEntity.status(200).body("Bid rejected successfully");
    }


}
