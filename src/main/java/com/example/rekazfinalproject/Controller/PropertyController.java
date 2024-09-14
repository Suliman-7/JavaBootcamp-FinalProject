package com.example.rekazfinalproject.Controller;
import com.example.rekazfinalproject.DTO.PropertyDTO;
import com.example.rekazfinalproject.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    @GetMapping("/get")
    public ResponseEntity getAllProperty(){
        return ResponseEntity.status(200).body(propertyService.getAllProperty());
    }

    @PostMapping("/add")
    public ResponseEntity addProperty(@Valid @RequestBody PropertyDTO propertyDTO) {
        propertyService.addProperty(propertyDTO);
        return ResponseEntity.status(200).body("Property add successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProperty(@PathVariable Integer id, @Valid @RequestBody PropertyDTO propertyDTO) {
        propertyService.updateProperty(id, propertyDTO);
        return ResponseEntity.status(200).body("Property updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProperty(@PathVariable Integer id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.status(200).body("Property deleted successfully");
    }

}
