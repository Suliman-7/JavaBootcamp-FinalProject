package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Rating;
import com.example.rekazfinalproject.Repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    public void addNewRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public void updateRating(Integer id,Rating rating) {
        Rating rating1 = ratingRepository.findRatingById(id);

        if (rating1 == null) {
            throw new ApiException("Rating not found");
        }
        rating1.setComment(rating.getComment());
        rating1.setScore(rating.getScore());
        ratingRepository.save(rating1);
    }


    public void deleteRating(Integer id) {
        Rating rating1 = ratingRepository.findRatingById(id);
        if (rating1 == null) {
            throw new ApiException("Rating not found");
        }
        ratingRepository.delete(rating1);
    }
}
