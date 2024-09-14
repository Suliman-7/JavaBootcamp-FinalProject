package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    //*** All CRUD Done by Danah ****

    //Get
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Add User
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //Update User
    public void updateUser(Integer id ,User user) {
        User u = userRepository.findUserById(id);
        if(u == null) {
            throw new ApiException("User not found");
        }
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setEmail(user.getEmail());
        u.setRole(user.getRole());
        userRepository.save(u);
    }

    //Delete User
    public void deleteUser(Integer id) {
        User u = userRepository.findUserById(id);
        if(u == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(u);
    }

    //Danah
    //Approve CR to activate account
    public void activateUser(Integer adminId,Integer userId) {
        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can activate users");
        }

        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }

        u.setActive(true);
        userRepository.save(u);
    }

    // Suliman
    public void discountOwner(Integer adminId , int ownerId){
        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can discount owner users");
        }
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }

        if(owner.getProjects().size() > 10) {
            owner.setDiscountPercentage(20);
            ownerRepository.save(owner);
        }

        if(owner.getProjects().size() >= 5 && owner.getProjects().size() < 10) {
            owner.setDiscountPercentage(15);
            ownerRepository.save(owner);
        }



        if(owner.getCreatedAt().datesUntil(LocalDate.now()).count() >=  180 && owner.getProjects().isEmpty() ) {
            owner.setDiscountPercentage(10);
            ownerRepository.save(owner);
        }
    }

    // Suliman

        public void deleteByWord(String comment)
    {
        if(ratingRepository.findReviewByCommentContains(comment).isEmpty())
        {
            throw new ApiException("No comment found");
        }
        ratingRepository.deleteAll(ratingRepository.findReviewByCommentContains(comment));
    }

    // Suliman 
    
    public void answerQuestion(Integer adminId,Integer questionId,String answer)
    {
        Question question = questionRepository.findQuestionById(questionId);
        if(question == null)
        {
            throw new ApiException("Question not found");
        }
        User adminUser = userRepository.findUserById(adminId);

        if(adminUser == null)
        {
            throw new ApiException("Admin not found");
        }

        question.setAnswer(answer);
        questionRepository.save(question);
    }



}
