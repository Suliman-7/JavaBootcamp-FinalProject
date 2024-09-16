package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.DTO.SubscriptionDTO;
import com.example.rekazfinalproject.Model.Subscription;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/subscribe")
    public ResponseEntity subscribeOwner(@AuthenticationPrincipal User user , @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        subscriptionService.subscribeOwner(user.getOwner().getId(), subscriptionDTO);
        return ResponseEntity.status(200).body("subscription made successfully");
    }

    @PutMapping("/update-subscription/{subscriptionId}")
    public ResponseEntity updateSubscription( @AuthenticationPrincipal User user , @PathVariable int subscriptionId , @Valid @RequestBody  Subscription subscription) {
        subscriptionService.updateSubscription( user.getId() , subscriptionId, subscription);
        return ResponseEntity.status(200).body("subscription updated successfully");
    }

    @DeleteMapping("/delete-subscription/{subscriptionId}")
    public ResponseEntity deleteSubscription( @AuthenticationPrincipal User user , @PathVariable int subscriptionId ) {
        subscriptionService.deleteSubscription(user.getId(),subscriptionId);
        return ResponseEntity.status(200).body("subscription deleted successfully");
    }

}
