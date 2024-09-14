package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Contract findContractById(Integer id);

//    List<Contract> findAllByUserId(Integer userId);
}
