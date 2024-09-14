package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.BidRepository;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {


    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;


    //*** All CRUD Done by Danah ****
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void registerOwner(OwnerDTO ownerDTO) {
        // Create and populate User entity
        User user = new User();
        user.setUsername(ownerDTO.getUsername());
        user.setPassword(ownerDTO.getPassword());
        user.setEmail(ownerDTO.getEmail());
        user.setRole("OWNER");

        // Create and populate Owner entity
        Owner owner = new Owner();
        owner.setCommercialRegister(ownerDTO.getCommercialRegister());
        owner.setScopeOfWork(ownerDTO.getScopeOfWork());
        owner.setNumOfProject(ownerDTO.getNumOfProject());
        owner.setCreatedAt(LocalDate.now());
        owner.setUser(user);

        user.setOwner(owner);

        // Save User and Owner
        userRepository.save(user);
    }

    public void updateOwner(Integer id, OwnerDTO ownerDTO) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }

        owner.setCommercialRegister(ownerDTO.getCommercialRegister());
        owner.setScopeOfWork(ownerDTO.getScopeOfWork());
        owner.setNumOfProject(ownerDTO.getNumOfProject());
        ownerRepository.save(owner);

        User user = owner.getUser();
        user.setUsername(ownerDTO.getUsername());
        user.setPassword(ownerDTO.getPassword());
        user.setEmail(ownerDTO.getEmail());
        userRepository.save(user);
    }

    public void deleteOwner(Integer id) {
        User owner = userRepository.findUserById(id);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        userRepository.delete(owner);
    }

    // Suliman

    public void addProject( Integer ownerId , Project project) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if(!owner.getUser().isActive()){
            throw new ApiException("User is not active");
        }
        project.setOwner(owner);
        project.setCreationDate(LocalDate.now());
        projectRepository.save(project);
    }

    // Suliman

    public List<Project> getMyProjects(Integer ownerId) {
        List<Project> myProjects = new ArrayList<>();
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        for(Project project : projectRepository.findAll()){
            if (project.getOwner().equals(owner)) {
                myProjects.add(project);
            }
        }
        if (myProjects.size() == 0) {
            throw new ApiException("Owner doesn't have any projects");
        }
        return myProjects;
    }

    // Suliman
    public void approveBid( Integer ownerId , Integer bidId){
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if(!owner.getUser().isActive()){
            throw new ApiException("User is not active");
        }
        Bid bid = bidRepository.findBidById(bidId);
        if (bid == null) {
            throw new ApiException("Bid not found");
        }
        if(!bid.getProject().getOwner().equals(owner)){
            throw new ApiException("this bid belong to another owner");
        }

        if(bid.getProject().getStatus() == Project.ProjectStatus.COMPLETED) {
            throw new ApiException("The project for this bid is already completed and associated with a different bid.");
        }
        // rejected bid can be approved ?
//        if(bid.getStatus().equalsIgnoreCase("Rejected")){
//            throw new ApiException("Rejected bid can't be approved");
//        }
        if (bid.getStatus() == Bid.BidStatus.APPROVED) {
            throw new ApiException("Bid is already approved");
        }
        Project project = projectRepository.findProjectById(bid.getProject().getId());
        project.setStatus(Project.ProjectStatus.COMPLETED);
        bid.setStatus(Bid.BidStatus.APPROVED);
        bidRepository.save(bid);
    }

    // Suliman

    public void rejectBid( Integer ownerId , int bidId){
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if(!owner.getUser().isActive()){
            throw new ApiException("User is not active");
        }
        Bid bid = bidRepository.findBidById(bidId);
        if (bid == null) {
            throw new ApiException("Bid not found");
        }
        if(!bid.getProject().getOwner().equals(owner)){
            throw new ApiException("this bid belong to another owner");
        }
        if (bid.getStatus() == Bid.BidStatus.APPROVED) {
            throw new ApiException("Approved bid can't be rejected");
        }
        if (bid.getStatus() == Bid.BidStatus.REJECTED) {
            throw new ApiException("Bid is already rejected");
        }

        bid.setStatus(Bid.BidStatus.REJECTED);

        bidRepository.save(bid);
    }

}
