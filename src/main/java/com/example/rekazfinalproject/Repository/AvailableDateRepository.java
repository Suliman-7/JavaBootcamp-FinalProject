package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Integer> {

    // AvailableDate findAvailableDateById(int id);
}
