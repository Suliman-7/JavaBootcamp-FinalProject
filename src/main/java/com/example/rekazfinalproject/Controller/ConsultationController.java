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

    @GetMapping("/get")
    public ResponseEntity getAllConsultations(){
        return ResponseEntity.status(200).body(consultationService.getAllConsultations());
    }

    @PostMapping("/add")
    public ResponseEntity addConsultation(@Valid @RequestBody Consultation consultation){
        consultationService.addNewConsultation(consultation);
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

}
