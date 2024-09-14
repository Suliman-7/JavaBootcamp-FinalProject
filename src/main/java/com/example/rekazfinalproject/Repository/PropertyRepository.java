package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {
    Property findPropertiesById(Integer id);
}
