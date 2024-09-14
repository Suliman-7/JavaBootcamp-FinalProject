package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")

public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get-all-projects")
    public ResponseEntity getAllProjects() {
        return ResponseEntity.status(200).body(projectService.getAllProjects());
    }

    @PostMapping("/add-project/{ownerId}")
    public ResponseEntity addProject(@PathVariable Integer ownerId , @Valid @RequestBody Project project) {
        projectService.addProject( ownerId , project);
        return ResponseEntity.status(200).body("project added successfully");
    }

    @PutMapping("/update-project/{id}")
    public ResponseEntity updateProject( @PathVariable  Integer id , @Valid  @RequestBody Project project) {
        projectService.updateProject(id, project);
        return ResponseEntity.status(200).body("project updated successfully");
    }

    @DeleteMapping("/delete-project/{id}")
    public ResponseEntity deleteProject( @PathVariable  Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.status(200).body("project deleted successfully");
    }

    //-- Danah
    @GetMapping("/city/{city}")
    public ResponseEntity getAllProjectsByCity(@PathVariable String city) {
        List<Project> projects = projectService.findByCity(city);
        return ResponseEntity.status(200).body(projects);
    }

    //-- Danah
    @GetMapping("/project-type/{projectType}")
    public ResponseEntity getAllProjectsByProjectType(@PathVariable String projectType) {
        List<Project> projects = projectService.findByProjectType(projectType);
        return ResponseEntity.status(200).body(projects);
    }

    // Suliman
        @GetMapping("/get-closest-projects")
    public ResponseEntity getClosestProject() {
        return ResponseEntity.status(200).body(projectService.getClosestProjects());
    }

        // Suliman

    
    @GetMapping("/get-highest-projects")
    public ResponseEntity getHighestProject() {
        return ResponseEntity.status(200).body(projectService.getHighestProjectsBudget());
    }

        // Suliman

    
    @GetMapping("/get-by-date/{startDate}/{endDate}")
    public ResponseEntity getByDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.status(200).body(projectService.getProjectByDate(startDate, endDate));
    }
}
