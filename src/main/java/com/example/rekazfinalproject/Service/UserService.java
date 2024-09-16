package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.Question;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.QuestionRepository;
import com.example.rekazfinalproject.Repository.RatingRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;

    //*** All CRUD Done by Danah ****

    //Get
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Add User
    public void addUser(User user) {
         String hash = new BCryptPasswordEncoder().encode(user.getPassword());
         user.setPassword(hash);
         userRepository.save(user);
    }

    //Update User
    public void updateUser(Integer id ,User user) {
        User u = userRepository.findUserById(id);
        if(u == null) {
            throw new ApiException("User not found");
        }
        u.setUsername(user.getUsername());
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        u.setPassword(hash);
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
    public void activateUser( Integer adminId , Integer userId) {
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
    public void discountOwner( Integer adminId , Integer ownerId){

        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can activate users");
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

        public void deleteByWord( Integer adminId , String comment ) {

            User adminUser = userRepository.findUserById(adminId);
            if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
                throw new ApiException("Only admins can activate users");
            }


        if(ratingRepository.findReviewByCommentContains(comment).isEmpty())
        {
            throw new ApiException("No comment found");
        }
        ratingRepository.deleteAll(ratingRepository.findReviewByCommentContains(comment));
    }

    // Suliman 
    
    public void answerQuestion(Integer adminId, Integer questionId,String answer)
    {
        User adminUser = userRepository.findUserById(adminId);
        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            throw new ApiException("Only admins can activate users");
        }

        Question question = questionRepository.findQuestionById(questionId);
        if(question == null)
        {
            throw new ApiException("Question not found");
        }

        question.setAnswer(answer);
        questionRepository.save(question);
    }

    public List<String> reportOwners(){
        List<String> tList = new ArrayList<>();
        List<Owner> allOwners = ownerRepository.findAll();
        if(allOwners.isEmpty()){
            throw new ApiException("No owner found");
        }
        for(Owner owner : allOwners) {
            tList.add(" ===== Owners                                              ====== ");
            tList.add(" ===== Owner : " + owner.getId() + "                                           ===== ");
            tList.add(" ===== Owner Name : " + owner.getUser().getUsername() + "                                 ===== ");
            tList.add(" ===== Owner Commercial Register : " + owner.getCommercialRegister() + "                   ===== ");
            if(owner.getProjects().isEmpty()) {
                tList.add(" ===== Has No Project ====== ");
                break;
            }
            tList.add(" ===== Has " + owner.getProjects().size() + " Project             ===== ");
            tList.add(" ===== Owner Projects :              ====== ");
            for(Project project : owner.getProjects()) {
                tList.add(" ===== Project : " + project.getId() + "       ===== ");
                tList.add(" ===== Project Title : " + project.getTitle() + "    ===== ");
                tList.add(" ===== Project Description : " + project.getDescription() + " ===== ");
                tList.add(" ===== Project Budget : " + project.getBudget() + " $          ===== ");
            }
        }
        return tList;
    }



}
