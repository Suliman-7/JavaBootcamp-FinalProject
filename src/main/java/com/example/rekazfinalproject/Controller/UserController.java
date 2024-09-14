package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    //*** All CRUD Done by Danah ****
    //Done test - get
    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    //done test - Add User
    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body("User added successfully");
    }

    //Done test - Update User
    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @Valid @RequestBody User user){
        userService.updateUser(userId, user);
        return ResponseEntity.status(200).body("User updated successfully");
    }

    //Done test - Delete User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body("User deleted successfully");
    }

    
    @PutMapping("/activate/{adminId}/{userId}")
    public ResponseEntity activateUser(@PathVariable Integer adminId,@PathVariable Integer userId){
        userService.activateUser(adminId,userId);
        return ResponseEntity.status(200).body("User activated successfully");
    }

    // Suliman 
    
    @PutMapping("/discount/{adminId}/{ownerId}")
    public ResponseEntity discountOwner(@PathVariable Integer adminId,@PathVariable Integer ownerId){
        userService.discountOwner(adminId,ownerId);
        return ResponseEntity.status(200).body("discount made to owner successfully");
    }

    
    // Suliman

    @DeleteMapping("/delete-by-word/{word}")
    public ResponseEntity deleteByComment(@PathVariable String word)
    {
        userService.deleteByWord(word);
        return ResponseEntity.status(200).body("Comment deleted");
    }

    // Suliman 
    
    @PutMapping("/answer-question/{adminId}/{questionId}")
    public ResponseEntity addAnswer(@PathVariable Integer adminId,@PathVariable Integer questionId, @RequestBody String answer)
    {
        userService.answerQuestion(adminId,questionId, answer);
        return ResponseEntity.status(200).body(("Question answered successfully"));
    }


}
