package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Complaint;
import com.example.rekazfinalproject.Repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaint() {
        return complaintRepository.findAll();
    }

    public void addNewComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public void updateComplaint(Integer id,Complaint complaint) {
        Complaint complaint1 = complaintRepository.findComplaintsById(id);
        if (complaint1 == null) {
            throw new ApiException("Complaint not found");
        }
        complaint1.setDescription(complaint.getDescription());
        complaint1.setFile(complaint.getFile());
        complaint1.setStatus(complaint.getStatus());
        complaint1.setType(complaint.getType());
        complaint1.setPriority_level(complaint.getPriority_level());

    }


    public void deleteComplaint(Integer id) {
        Complaint complaint1 = complaintRepository.findComplaintsById(id);

        if (complaint1 == null) {
            throw new ApiException("Complaint not found");
        }
        complaintRepository.delete(complaint1);
    }
}
