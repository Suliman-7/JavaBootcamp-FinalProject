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
  //عدلت هنا
    // Suliman
    public void addProject( Integer ownerId , Project project) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
         if(ownerUser.isActive()==false){
            throw new ApiException("owner is not active");
        }
        project.setOwner(ownerUser.getOwner());
        project.setPublication_date(LocalDate.now());
        project.setStatus(Project.ProjectStatus.PENDING);
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
        project1.setCreationDate(project.getCreationDate());
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

    // Suliman

    public List<Project> getClosestProjects(){
        List<Project> closestProject = projectRepository.findAll();

        for (int i = 0; i < closestProject.size() - 1; i++) {
            for (int j = 0; j < closestProject.size() - i - 1; j++) {
                if (closestProject.get(j).getCreationDate().isAfter(closestProject.get(j + 1).getCreationDate()) || closestProject.get(j).getCreationDate().isEqual(closestProject.get(j + 1).getCreationDate())) {
                    Project closest = closestProject.get(j);
                    closestProject.set(j, closestProject.get(j + 1));
                    closestProject.set(j + 1, closest);
                }
            }
        }
        if(closestProject.isEmpty()){
            throw new ApiException("No project found");
        }
        return closestProject;
    }

    // Suliman

    public List<Project> getHighestProjectsBudget(){
        List<Project> highestProjects = projectRepository.findAll();

        for (int i = 0; i < highestProjects.size() - 1; i++) {
            for (int j = 0; j < highestProjects.size() - i - 1; j++) {
                if (highestProjects.get(j).getBudget() <= highestProjects.get(j + 1).getBudget()) {
                    Project highest = highestProjects.get(j);
                    highestProjects.set(j, highestProjects.get(j + 1));
                    highestProjects.set(j + 1, highest);
                }
            }
        }
        if(highestProjects.isEmpty()){
            throw new ApiException("No project found");
        }
        return highestProjects;
    }

// Suliman


    public List<Project> getProjectByDate(LocalDate startDate , LocalDate endDate){
        if (startDate.isAfter(endDate)) {
            throw new ApiException("Start date cannot be after end date");
        }
        if(projectRepository.findByProjectDateBetween(startDate, endDate).isEmpty()){
            throw new ApiException("No project found in this date");
        }
        return projectRepository.findByProjectDateBetween(startDate , endDate);
    }
    //Shahad
    // investor get list project by budget
    public List<Project> getProjectsByBudget(Integer investorId,double maxBudget) {
        User invstorUser=userRepository.findUserById(investorId);
        if(invstorUser==null||!invstorUser.getRole().equalsIgnoreCase("Investor")){
            throw new ApiException("Investor not found");
        }
        if(invstorUser.isActive()==false){
            throw new ApiException("Investor not Active");
        }
        return projectRepository.listProjectWithSpecvicedBudgetAndStatus(maxBudget);
    }
    

}
