package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.DTO.SubscriptionDTO;
import com.example.rekazfinalproject.Model.Subscription;
import com.example.rekazfinalproject.Service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor

public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/get-all-subscriptions")
    public ResponseEntity getAllSubscriptions(){
        return ResponseEntity.status(200).body(subscriptionService.getAllSubscriptions());
    }

    @PostMapping("/add-subscription/{ownerId}")
    public ResponseEntity addSubscription(@PathVariable int ownerId , @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        subscriptionService.addSubscription(ownerId, subscriptionDTO);
        return ResponseEntity.status(200).body("subscription added successfully");
    }

    @PutMapping("/update-subscriptions/{subscriptionId}")
    public ResponseEntity updateSubscription(@PathVariable int subscriptionId , @Valid @RequestBody  Subscription subscription) {
        subscriptionService.updateSubscription(subscriptionId, subscription);
        return ResponseEntity.status(200).body("subscription updated successfully");
    }

    @DeleteMapping("/delete-subscription/{subscriptionId}")
    public ResponseEntity deleteSubscription(@PathVariable int subscriptionId , @Valid @RequestBody  Subscription subscription) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.status(200).body("subscription deleted successfully");
    }

}
