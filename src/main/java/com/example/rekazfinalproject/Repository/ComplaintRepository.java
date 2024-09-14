package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    Complaint findComplaintsById(Integer id);

}
