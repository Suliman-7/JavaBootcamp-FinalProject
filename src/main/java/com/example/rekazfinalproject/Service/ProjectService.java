package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.Contract;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Repository.BidRepository;
import com.example.rekazfinalproject.Repository.ContractRepository;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor


public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;
    private final ContractRepository contractRepository;
    private final OwnerRepository ownerRepository;

    // Suliman
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Suliman
    public void addProject( Integer ownerId , Project project) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        project.setOwner(owner);
        project.setStatus(Project.ProjectStatus.IN_PROGRESS);
        projectRepository.save(project);
    }

    // Suliman
    public void updateProject(Integer id , Project project) {
        Project project1 = projectRepository.findProjectById(id);
        if (project1 == null) {
            throw new ApiException("Project not found");
        }
        project1.setTitle(project.getTitle());
        project1.setDescription(project.getDescription());
        project1.setProjectType(project.getProjectType());
        project1.setBudget(project.getBudget());
        project1.setDeadline(project.getDeadline());
        project1.setCity(project.getCity());
        projectRepository.save(project1);
    }


    // Suliman
    public void deleteProject(Integer id) {

        for (Contract contract : contractRepository.findAll() ) {
            if (contract.getProject().getId() == id) {
                throw new ApiException("Project is associated with this investor company you cannot delete this project");
            }
        }


        Project project = projectRepository.findProjectById(id);
        if (project == null) {
            throw new ApiException("Project not found");
        }


        for (Bid bid : bidRepository.findAll() ) {
            if (bid.getProject().getId() == id) {
                bidRepository.delete(bid);
            }
        }


        projectRepository.delete(project);
    }

    //Danah
    public List<Project> findByCity(String city) {
        return projectRepository.findProjectByCity(city);
    }

    //Danah
    public List<Project> findByProjectType(String projectType) {
        return projectRepository.findProjectByProjectType(projectType);
    }

}