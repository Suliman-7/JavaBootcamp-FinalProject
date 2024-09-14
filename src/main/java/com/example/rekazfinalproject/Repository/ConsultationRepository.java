package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
    Consultation findConsultationById(Integer id);

}
