package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {


    Investor findInvestorById(Integer id);
}
