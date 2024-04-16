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
        return jobCategoryRepository.getJobCategoryList();
    }

    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType.Type docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, Skill skill){
        Collaborator collaborator=new Collaborator(name, birthday, admissionDate,address, addressZipCode, addressCity,phoneNumber, docType, docIDNumber, taxPayerNumber, jobCategory, skill);
    }

    public DocType.Type[] getDocTypeList(){
        return DocType.Type.values();
    }

    private void getHRMFromSession(){

    }

}
