package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class RegisterCollaboratorController {
    public CollaboratorRepository collaboratorRepository;
    public JobCategoryRepository jobCategoryRepository;

    public RegisterCollaboratorController(){
        getJobCategoriesRepository();
        getCollaboratorRepository();
    }

    public CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();

            // Getting the JobCategory Repository
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    public JobCategoryRepository getJobCategoriesRepository() {
        if (jobCategoryRepository == null) {
            Repositories repositories = Repositories.getInstance();

            // Getting the JobCategory Repository
            jobCategoryRepository = repositories.getJobCategoryRepository();
        }
        return jobCategoryRepository;
    }


    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }

    public boolean validateDocType(DocType type, int docTypeNumber){
        return type.verifyDocType(docTypeNumber);
    }
    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType docType, int docIDNumber, JobCategory jobCategory, Collaborator.StatusType statusType){
        if(verifyData(name, birthday, admissionDate, address, addressZipCode, addressCity, phoneNumber, docType, docIDNumber, jobCategory, statusType)){

        }
        Collaborator collaborator = new Collaborator(name, birthday, admissionDate,address, addressZipCode, addressCity,phoneNumber, docType, docIDNumber, jobCategory, statusType);
    }

    private boolean verifyData(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType docType, int docIDNumber, JobCategory jobCategory, Collaborator.StatusType statusType) {
        boolean value=false;
        if(verifyName(name) && verifyBirthdayAndAdmission(birthday,admissionDate) && verifyAddress(address,addressZipCode,addressCity) && verifyPhoneNumber(phoneNumber) && verifyStatus(statusType)){
            value=true;
        }
        return value;
    }

    private boolean verifyStatus(Collaborator.StatusType statusType) {

    }

    private boolean verifyPhoneNumber(int phoneNumber) {
        return (phoneNumber%1000000000)>0.9 && (phoneNumber%1000000000)<1 &&( (phoneNumber/10000000)==91 || (phoneNumber/10000000)==92 || (phoneNumber/10000000)==93 || (phoneNumber/10000000)==96 );
    }

    private boolean verifyAddress(String address, String addressZipCode, String addressCity) {
        return false;
    }

    private boolean verifyBirthdayAndAdmission(Date birthday, Date admissionDate) {
        return false;
    }

    private boolean verifyName(String name) {
        return false;
    }

    public DocType.Type[] getDocTypeList(){
        return DocType.Type.values();
    }

    private void getHRMFromSession(){

    }

}
