package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findProjectById(int id);

    Integer countProjectByOwnerId(Integer id);

    List<Project> findProjectByCity(String city);

    List<Project> findProjectByProjectType(String projectType);


}
