package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final InvestorRepository investorRepository;
    //Shahad
//Admin
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    //investor
    public List<Rating> getMyRating(Integer investorId) {
        Investor investor = investorRepository.findInvestorById(investorId);
        if(investor==null){
            throw new ApiException("Investor not found");
        }
        return ratingRepository.findRatingByInvestor(investor);
    }

    //  shahad
    //Owner
    public void addNewRating(Integer ownerId, Integer investorId, Rating rating) {
        User investorUser = userRepository.findUserById(investorId);
        if (investorUser == null) {
            throw new ApiException("Investor not found");
        }
        if (investorUser.isActive() == false) {
            throw new ApiException("investor is not active");
        }
        User ownerUser = userRepository.findUserById(ownerId);
        if (ownerUser == null) {
            throw new ApiException("Owner not found");
        }
        if (ownerUser.isActive() == false) {
            throw new ApiException("owner is not active");
        }
        Contract contract = contractRepository.findContractByOwnerIdAndInvestorId(ownerId, investorId);
        if (contract.getOwner().getId() != ownerId || contract.getInvestor().getId() != investorId) {
            throw new ApiException("Owner and Investor dont have connection");
        }
        rating.setInvestor(investorUser.getInvestor());
        rating.setOwner(ownerUser.getOwner());
        ratingRepository.save(rating);
    }

    //Shahad
    //مدري مين على اساس انه ادمن
    public void updateRating(Integer adminId,Integer ratingId, Rating rating) {
        Rating rating1 = ratingRepository.findRatingById(ratingId);
        if (rating1 == null) {
            throw new ApiException("Rating not found");
        }
        User adminUser=userRepository.findUserById(adminId);
        if(adminUser==null||!adminUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new ApiException("You do not have the authority");
        }
        rating1.setComment(rating.getComment());
        rating1.setScore(rating.getScore());
        ratingRepository.save(rating1);
    }

    //Admin just can delete the rating
    //Shahad
    public void deleteRating(Integer userId, Integer ratingId) {
        User user = userRepository.findUserById(userId);
        Rating rating1 = ratingRepository.findRatingById(ratingId);
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ApiException("You do not have the authority");
        }
        if (rating1 == null) {
            throw new ApiException("Rating not found");
        }
        ratingRepository.delete(rating1);
    }

    //Shahad
    //Average rating investor
    public Double getAverageRating(Integer investorId) {
        Double averageRating = ratingRepository.findAverageRatingByInvestorId(investorId);
        if (averageRating == null) {
            throw new ApiException("No ratings available for this investor");
        }
        return averageRating;
    }

}
