package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final QuestionRepository questionRepository;


    //*** All CRUD Done by Danah ****
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void registerOwner(OwnerDTO ownerDTO) {
        // Create and populate User entity
        User user = new User();
        user.setUsername(ownerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(ownerDTO.getPassword());
        user.setPassword(hash);
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



        owner.setCommercialRegister(ownerDTO.getCommercialRegister());
        owner.setScopeOfWork(ownerDTO.getScopeOfWork());
        owner.setNumOfProject(ownerDTO.getNumOfProject());
        ownerRepository.save(owner);

        User user = owner.getUser();
        user.setUsername(ownerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(ownerDTO.getPassword());
        user.setPassword(hash);
        user.setEmail(ownerDTO.getEmail());
        user.setActive(true);
        userRepository.save(user);
    }

    public void deleteOwner(Integer id) {
        User owner = userRepository.findUserById(id);

        userRepository.delete(owner);
    }

    // Suliman

    public List<Project> getMyProjects(Integer ownerId) {
        List<Project> myProjects = new ArrayList<>();
        Owner owner = ownerRepository.findOwnerById(ownerId);

        for(Project project : projectRepository.findAll()){
            if (project.getOwner().equals(owner)) {
                myProjects.add(project);
            }
        }
        if (myProjects.isEmpty()) {
            throw new ApiException("Owner doesn't have any projects");
        }
        return myProjects;
    }



    // Suliman
    public void approveBid( Integer ownerId , Integer bidId){
        Owner owner = ownerRepository.findOwnerById(ownerId);

        if(!owner.getUser().isActive()){
            throw new ApiException("User is not active");
        }
        Bid bid = bidRepository.findBidById(bidId);
        if(bid.getProject().getOwner().getId() != owner.getId()){
            throw new ApiException("User id mismatch");
        }
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
        //عدلت هنا
        project.setInvestor(bid.getInvestor());
        project.setStatus(Project.ProjectStatus.IN_PROGRESS);
        bid.setStatus(Bid.BidStatus.APPROVED);
        bidRepository.save(bid);
    }

    // Suliman

    public void rejectBid( int ownerId , int bidId , String comment ){
        Owner owner = ownerRepository.findOwnerById(ownerId);

        if(!owner.getUser().isActive()){
            throw new ApiException("User is not active");
        }
        Bid bid = bidRepository.findBidById(bidId);
        if(bid.getProject().getOwner().getId() != owner.getId()){
            throw new ApiException("User id mismatch");
        }
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

        // if(comment==null){
        //     bid.setComment("no comment from the owner");
        // }
        
        bid.setStatus(Bid.BidStatus.REJECTED);
        bid.setComment(comment);

        bidRepository.save(bid);
    }




}
