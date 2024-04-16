package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;

import java.util.List;

public class RegisterACollaboratorController {
    public CollaboratorRepository collaboratorRepository;
    public JobCategoryRepository jobCategoryRepository;

    public RegisterACollaboratorController(CollaboratorRepository collaboratorRepository, JobCategoryRepository jobCategoryRepository){

    }

    public List<JobCategory> getJobCategoriesList(){

    }

    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, Skill skill){
        Collaborator collaborator=new Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, Skill skill);
    }

    private void getHRMFromSession(){

    }

}
