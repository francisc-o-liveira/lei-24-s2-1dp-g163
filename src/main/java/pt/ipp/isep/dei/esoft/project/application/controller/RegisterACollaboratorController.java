package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;

import java.util.List;

public class RegisterACollaboratorController {
    public CollaboratorRepository collaboratorRepository;
    public JobCategoryRepository jobCategoryRepository;

    public RegisterACollaboratorController(CollaboratorRepository collaboratorRepository, JobCategoryRepository jobCategoryRepository){

    }

    public List<JobCategory> getJobCategoriesList(){

        return jobCategoryRepository;
    }

    public void registerCollaborator(Collaborator collaborator){

    }

    private void getHRMFromSession(){

    }

    + registerCollaborator(name, date of birthday, admission data,\n address street, zipcode, address city, email, phone number, docType,\n docIDNumber, jobCategory)

}
