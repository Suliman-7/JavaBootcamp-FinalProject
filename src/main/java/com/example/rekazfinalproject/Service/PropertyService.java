package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.DTO.PropertyDTO;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.Property;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import com.example.rekazfinalproject.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final ProjectRepository projectRepository;
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    public void addProperty(PropertyDTO propertyDTO) {
        // Create and populate Project entity
        Project project=new Project();
        project.setTitle(propertyDTO.getTitle());
        project.setProjectType(propertyDTO.getProjectType());
        project.setDescription(propertyDTO.getDescription());
        project.setDeadline(propertyDTO.getDeadline());
        project.setBudget(propertyDTO.getBudget());
        project.setPublication_date(propertyDTO.getPublication_date());
        project.setStatus(propertyDTO.getStatus());

        // Create and populate property entity
       Property property=new Property();
       property.setOwnershipStartDate(propertyDTO.getOwnershipStartDate());
       property.setOwnerName(propertyDTO.getOwnerName());
        property.setOwnershipEndDate(propertyDTO.getOwnershipEndDate());
        property.setLandValue(propertyDTO.getLandValue());
        property.setLocation(propertyDTO.getLocation());
        property.setLandTitle(propertyDTO.getLandTitle());
        property.setLandArea(propertyDTO.getLandArea());
        property.setDescription(propertyDTO.getDescription());

        property.setProject(project);
        project.setProperty(property);
        propertyRepository.save(property);
        projectRepository.save(project);
    }

    public void updateProperty(Integer id, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findPropertiesById(id);
        if (property == null) {
            throw new ApiException("property not found");
        }
        property.setOwnershipStartDate(propertyDTO.getOwnershipStartDate());
        property.setOwnerName(propertyDTO.getOwnerName());
        property.setOwnershipEndDate(propertyDTO.getOwnershipEndDate());
        property.setLandValue(propertyDTO.getLandValue());
        property.setLocation(propertyDTO.getLocation());
        property.setLandTitle(propertyDTO.getLandTitle());
        property.setLandArea(propertyDTO.getLandArea());
        property.setDescription(propertyDTO.getDescription());
        propertyRepository.save(property);

        Project project=new Project();
        project.setTitle(propertyDTO.getTitle());
        project.setProjectType(propertyDTO.getProjectType());
        project.setDescription(propertyDTO.getDescription());
        project.setDeadline(propertyDTO.getDeadline());
        project.setBudget(propertyDTO.getBudget());
        project.setPublication_date(propertyDTO.getPublication_date());
        project.setStatus(propertyDTO.getStatus());
        projectRepository.save(project);

    }

    public void deleteProperty(Integer id) {
        Property property = propertyRepository.findPropertiesById(id);
        if (property == null) {
            throw new ApiException("property not found");
        }
        propertyRepository.delete(property);
    }

}
