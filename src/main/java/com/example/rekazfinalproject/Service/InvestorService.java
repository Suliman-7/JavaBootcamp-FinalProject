package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.InvestorDTO;
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
public class InvestorService {

    //*** All CRUD Done by Danah ****
    private final InvestorRepository investorRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;
    private final OwnerRepository ownerRepository;
    private final QuestionRepository questionRepository;
    //Admin
    public List<Investor> getAllInvestor(){
        return investorRepository.findAll();
    }
    //primt
    public void registerInvestor(InvestorDTO investorDTO) {
        User user = new User();
        user.setUsername(investorDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(investorDTO.getPassword());
        user.setPassword(hash);
        user.setEmail(investorDTO.getEmail());
        user.setRole("INVESTOR");

        Investor investor = new Investor();
        investor.setCommercialRegister(investorDTO.getCommercialRegister());
        investor.setNumOfInvest(investorDTO.getNumOfInvest());
        investor.setInvestorSectors(investorDTO.getInvestorSectors());
        investor.setCreatedAt(LocalDate.now());
        investor.setUser(user);


        user.setInvestor(investor);

        userRepository.save(user);
    }

    public void updateInvestor(Integer userId,Integer id, InvestorDTO investorDTO) {
        Investor investor = investorRepository.findInvestorById(id);
        if (investor == null) {
            throw new ApiException("investor not found");
        }
        if(investor.getId()!=userId){
            throw new ApiException("You do not have the authority");
        }
        investor.setCommercialRegister(investorDTO.getCommercialRegister());
        investor.setNumOfInvest(investorDTO.getNumOfInvest());
        investor.setInvestorSectors(investorDTO.getInvestorSectors());
        investorRepository.save(investor);

        User user = investor.getUser();
        user.setUsername(investorDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(investorDTO.getPassword());
        user.setPassword(hash);
        user.setEmail(investorDTO.getEmail());
        userRepository.save(user);
    }

    public void deleteInvestor(Integer adminId,Integer id) {
        User user = userRepository.findUserById(adminId);
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ApiException("You do not have the authority");
        }
        User investor = userRepository.findUserById(id);
        if (investor == null) {
            throw new ApiException("investor not found");
        }
        userRepository.delete(investor);
    }

    // Investor add bid in specific project , made by suliman
    public void addBid( Integer investorId , Integer projectId , Bid bid) {

        Project project = projectRepository.findProjectById(projectId);

        if(project==null){
            throw new ApiException("Project not found");
        }

        Investor investor = investorRepository.findInvestorById(investorId);

        if(investor==null){
            throw new ApiException("Investor not found");
        }

        // investor cannot make many bids to same project

        for(Bid bid1 : project.getBid()) {
            if(bid1.getInvestor()==investor){
                throw new ApiException("you have already bid in this project");
            }
        }

        // when bid is approved other investors cannot add bids

        if (project.getStatus() == Project.ProjectStatus.COMPLETED) {
            throw new ApiException("This project has an approved bid.");
        }

        bid.setStatus(Bid.BidStatus.PENDING);

        bid.setInvestor(investor);

        bid.setProject(project);

        bidRepository.save(bid);
    }


    // Investor edit his bid , made by suliman

    public void editBid ( int investorId , int id , Bid bid) {

        Investor investor = investorRepository.findInvestorById(investorId);

        if(investor==null){
            throw new ApiException("Investor not found");
        }

        User investorUser = userRepository.findUserById(investorId);
        if(investorUser.isActive()==false){
            throw new ApiException("investor is not active");
        }


        Bid bid1 = bidRepository.findBidById(id);

        if(bid1==null){
            throw new ApiException("Bid not found");
        }



        if(bid1.getStatus()== Bid.BidStatus.APPROVED){
            throw new ApiException("Approved Bid cannot be edited");
        }

        if(bid1.getInvestor()!=investor){
            throw new ApiException("this bid belong to another investor");
        }

        bid1.setBudget(bid.getBudget());
        bid1.setDeadline(bid.getDeadline());
        bid1.setDescription(bid.getDescription());
        bid1.setStatus(Bid.BidStatus.PENDING);
        bid1.setComment("");

        bidRepository.save(bid1);
    }

    // Suliman

    public List<Project> getMyProjects(int investorId) {
        List<Project> myProjects = new ArrayList<>();
        Investor investor = investorRepository.findInvestorById(investorId);
        if (investor == null) {
            throw new ApiException("Investor not found");
        }
        for(Project project : projectRepository.findAll()){
            if (project.getInvestor().equals(investor)) {
                myProjects.add(project);
            }
        }
        if (myProjects.size() == 0) {
            throw new ApiException("Investor doesn't have any projects");
        }
        return myProjects;
    }

    // Suliman

    public List<Project> getOwnerProject(int ownerId){
        List<Project> ownerProjects = new ArrayList<>();
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("Owner not found");
        }
        for(Project project : projectRepository.findAll()){
            if(project.getOwner().equals(owner)){
                ownerProjects.add(project);
            }
        }
        if (ownerProjects.size() == 0) {
            throw new ApiException("Owner doesn't have any projects");
        }
        return ownerProjects;
    }


    // Suliman

    public List<Investor> showHighestInvestorsRate() {
        List<Investor> highestRev = investorRepository.findAll();

        for (int i = 0; i < highestRev.size() - 1; i++) {
            for (int j = 0; j < highestRev.size() - i - 1; j++) {
                if (highestRev.get(j).getRate() < highestRev.get(j + 1).getRate()) {

                    Investor highest = highestRev.get(j);
                    highestRev.set(j, highestRev.get(j + 1));
                    highestRev.set(j + 1, highest);
                }
            }
        }

        return highestRev;

    }

    //shahad
    //get investor Company have bid in the owner project
    public List<Investor> listInvestorCompanyByOwner(Integer ownerId) {
        User owner = userRepository.findUserById(ownerId);
        if (owner == null || !owner.getRole().equalsIgnoreCase("Owner")) {
            throw new ApiException("Owner not found");
        }
        //Find projects owned by the Owner
        List<Project> ownerProjects = projectRepository.findProjectsByOwnerId(ownerId);
        if (ownerProjects.isEmpty()) {
            throw new ApiException("No projects found for this owner");
        }
        //Find investors who have bid on this Owner's projects.
        return investorRepository.findInvestorsByProjects(ownerProjects);
    }

}
