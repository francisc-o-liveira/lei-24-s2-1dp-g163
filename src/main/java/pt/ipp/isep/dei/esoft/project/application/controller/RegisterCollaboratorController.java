package pt.ipp.isep.dei.esoft.project.application.controller;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType.Type;

import java.util.List;

/**
 * This Class represents the Controller to Register a Collaborator
 */
public class RegisterCollaboratorController {
    /**
     * This variable represents the Collaborator Repository
     */
    public CollaboratorRepository collaboratorRepository;
    /**
     * This variable represents the JobCategory Repository
     */
    public JobCategoryRepository jobCategoryRepository;


    public RegisterCollaboratorController() {
        getDataNeededToRegister();
    }

    /**
     * This method gets the instances of Job Category Repository and Collaborator Repository
     * It is necessary Data to register a Collaborator
     */
    public void getDataNeededToRegister(){
        if (jobCategoryRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the JobCategory Repository
            jobCategoryRepository = repositories.getJobCategoryRepository();
            }
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the Collaborator Repository
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
    }

    /**
     * This method registers a collaborator from collaborator's Repository
     * @param name of collaborator
     * @param birthday of collaborator
     * @param admissionDate of collaborator
     * @param address of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity of collaborator
     * @param email of collaborator
     * @param phoneNumber of collaborator
     * @param docType of collaborator
     * @param docIDNumber of collaborator
     * @param jobCategory of collaborator
     */
    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressCity, String addressZipCode, int phoneNumber, String email, Type docType, int docIDNumber, JobCategory jobCategory){
        collaboratorRepository.createCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode, email, phoneNumber, docType, docIDNumber, jobCategory);
    }

    private void getHRMFromSession(){

    }

    public boolean validateDocType(DocType.Type type, int docIDNumber) {
            return type.verifyDocTypeValues(docTypeNumber);
    }

    public Type[] getDocTypeList() {
        return DocType.Type.values();
    }

    public List<JobCategory> getJobCategoryList() {
        return jobCategoryRepository.getJobCategoryList();
    }
}
