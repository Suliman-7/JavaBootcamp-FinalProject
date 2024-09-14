package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Consultation;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.ConsultationRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    public final ProjectRepository projectRepository;

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public void addNewConsultation(Consultation consultation) {
        consultationRepository.save(consultation);
    }

    public void updateConsultation(Integer id,Consultation consultation) {
        Consultation consultation1 = consultationRepository.findConsultationById(id);

        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }

        consultation1.setConsultationDate(consultation.getConsultationDate());
        consultation1.setDuration(consultation.getDuration());
        consultationRepository.save(consultation1);
    }


    public void deleteConsultation(Integer id) {
        Consultation consultation1 = consultationRepository.findConsultationById(id);
        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }
        consultationRepository.delete(consultation1);
    }





}