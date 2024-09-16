package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final InvestorRepository investorRepository;
    private final OwnerRepository ownerRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    //Admin
    public List<Complaint> getAllComplaint() {
        return complaintRepository.findAll();
    }
    //Shahad
    //investor send complaint
    public void investorSendComplaint(Integer investorId,Complaint complaint) {
        Investor investor=investorRepository.findInvestorById(investorId);
        if(investor==null){
            throw new ApiException("Investor not found");
        }
        Project project=projectRepository.findProjectByInvestorId(investorId);
        if(!project.getId().equals(complaint.getProject_num())){
            throw new ApiException("You have no right to complain.");
        }
        complaint.setInvestor(investor);
        complaint.setOwner(project.getOwner());
        complaint.setStatus(Complaint.ComplaintStatus.UNDER_REVIEW);
        complaintRepository.save(complaint);
    }
    //Shahad
    //Owner send complaint
    public void ownerSendComplaint(Integer ownerId,Complaint complaint) {
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("Owner not found");
        }
        Project project=projectRepository.findProjectByOwnerId(ownerId);
        if(!project.getId().equals(complaint.getProject_num())){
            throw new ApiException("You have no right to complain.");
        }
        complaint.setOwner(owner);
        complaint.setInvestor(project.getInvestor());
        complaint.setStatus(Complaint.ComplaintStatus.UNDER_REVIEW);
        complaintRepository.save(complaint);
    }
    //Shahad
    //admin
    public void updateComplaint(Integer adminId,Integer compId,Complaint complaint) {
        Complaint complaint1 = complaintRepository.findComplaintsById(compId);
        if (complaint1 == null) {
            throw new ApiException("Complaint not found");
        }
        User adminUser=userRepository.findUserById(adminId);
        if(adminUser==null||!adminUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new ApiException("You do not have the authority");
        }
        complaint1.setDescription(complaint.getDescription());
        complaint1.setFile(complaint.getFile());
        complaint1.setStatus(complaint.getStatus());
        complaint1.setType(complaint.getType());
        complaint1.setPriority_level(complaint.getPriority_level());

    }

    //Shahad
    //admin deleted
    public void deleteComplaint(Integer adminId,Integer comId) {
        Complaint complaint1 = complaintRepository.findComplaintsById(comId);
        User adminUser=userRepository.findUserById(adminId);
        if(adminUser==null||!adminUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new ApiException("You do not have the authority");
        }
        if (complaint1 == null) {
            throw new ApiException("Complaint not found");
        }
        complaintRepository.delete(complaint1);
    }
    //shahad
    //complaint status to IN_PROGRESS by admin
    public void startProcessingComplaint(Integer adminId,Integer complaintId) {
        User user=userRepository.findUserById(adminId);
        if(user==null||!user.getRole().equalsIgnoreCase("ADMIN")||user.isActive()==false){
            throw new ApiException("Admin not found");
        }
        Complaint complaint = complaintRepository.findComplaintsById(complaintId);
        if (complaint == null) {
            throw new ApiException("Complaint not found");
        }
        if (complaint.getStatus() != Complaint.ComplaintStatus.UNDER_REVIEW) {
            throw new ApiException("Complaint is not in review state");
        }
        complaint.setStatus(Complaint.ComplaintStatus.IN_PROGRESS);
        complaintRepository.save(complaint);
    }
    //shahad
    //complaint status to RESOLVED by admin
    public void resolveComplaint(Integer adminId,Integer complaintId) {
        User user=userRepository.findUserById(adminId);
        if(user==null||!user.getRole().equalsIgnoreCase("ADMIN")||user.isActive()==false){
            throw new ApiException("Admin not found");
        }
        Complaint complaint = complaintRepository.findComplaintsById(complaintId);
        if (complaint == null) {
            throw new ApiException("Complaint not found");
        }
        if (complaint.getStatus() != Complaint.ComplaintStatus.IN_PROGRESS) {
            throw new ApiException("Complaint is not in progress");
        }
        complaint.setStatus(Complaint.ComplaintStatus.RESOLVED);
        complaintRepository.save(complaint);
    }
}
