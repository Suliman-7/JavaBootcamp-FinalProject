package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
    Consultation findConsultationById(Integer id);

    @Query("SELECT CASE WHEN DATEDIFF(c.consultationDate, :currentDate) > 0 THEN true ELSE false END " +
            "FROM Consultation c " +
            "WHERE c.id = :id AND c.status <> 'Canceled'")
    boolean canCancelBeforeConsultationDate( Integer id,  LocalDate currentDate);

    List<Consultation> findConsultationByOwnerId(Integer ownerId);
    List<Consultation> findConsultationByInvestorId(Integer investorId);

}
