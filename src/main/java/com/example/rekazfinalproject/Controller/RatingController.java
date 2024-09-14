package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Rating;
import com.example.rekazfinalproject.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
  @GetMapping("/get")
    public ResponseEntity getAllRating(){
        return ResponseEntity.status(200).body(ratingService.getAllRating());
    }

    @PostMapping("/add/{ownerId}/{investorId}")
    public ResponseEntity addRating(@PathVariable Integer ownerId,@PathVariable Integer investorId,@Valid @RequestBody Rating rating){
        ratingService.addNewRating(ownerId,investorId,rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRating(@PathVariable Integer id , @Valid @RequestBody Rating rating){
        ratingService.updateRating(id, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully updated"));
    }

    @DeleteMapping("/delete/{userId}/{ratingId}")
    public ResponseEntity deleteRating(@PathVariable Integer userId,@PathVariable Integer ratingId){
        ratingService.deleteRating(userId,ratingId);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully deleted"));
    }
    //average rating
    @GetMapping("/get-average/{investor}")
    public ResponseEntity getAverageRating(@PathVariable Integer investor){
        return ResponseEntity.status(200).body(new ApiResponse("Average Rating: " +ratingService.getAverageRating(investor)));
    }
}
