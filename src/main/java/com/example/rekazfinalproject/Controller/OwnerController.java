package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    //*** All CRUD Done by Danah ****
    @GetMapping("/get")
    public ResponseEntity getAllOwners(){
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PostMapping("/register")
    public ResponseEntity registerOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.registerOwner(ownerDTO);
        return ResponseEntity.status(200).body("Owner registered successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOwner(@PathVariable Integer id, @Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(id, ownerDTO);
        return ResponseEntity.status(200).body("Owner updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.status(200).body("Owner deleted successfully");
    }


    @PostMapping("/add-project/{ownerId}")
    public ResponseEntity addProject(@PathVariable Integer ownerId , @Valid @RequestBody Project project) {
        ownerService.addProject( ownerId , project );
        return ResponseEntity.status(200).body("project added successfully");
    }

    @GetMapping("/get-my-projects/{ownerId}")
    public ResponseEntity getMyProjects(@PathVariable Integer ownerId) {
        return ResponseEntity.status(200).body(ownerService.getMyProjects(ownerId));
    }

    @PutMapping("/approve-bid/{ownerId}/{bidId}")
    public ResponseEntity approveBid(@PathVariable Integer ownerId, @PathVariable int bidId) {
        ownerService.approveBid(ownerId , bidId);
        return ResponseEntity.status(200).body("Bid approved successfully");
    }

    @PutMapping("/reject-bid/{ownerId}/{bidId}")
    public ResponseEntity rejectBid(@PathVariable int ownerId, @PathVariable int bidId , @RequestBody String comment) {
        ownerService.rejectBid(ownerId , bidId , comment);
        return ResponseEntity.status(200).body("Bid rejected successfully");
    }

    @PutMapping("/add-Question/{ownerId}")
    public ResponseEntity addQuestion(@PathVariable int ownerId , @RequestBody String question) {
        ownerService.ownerAddQuestion(ownerId,question);
        return ResponseEntity.status(200).body("Question added successfully");
    }

}
