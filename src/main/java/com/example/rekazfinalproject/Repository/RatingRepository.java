package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
   Rating findRatingById(Integer id);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.investor.id=?1")
    Double findAverageRatingByInvestorId( Integer investorId);

}
