package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterCollaboratorUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This Class Represent the Controller for Register a Collaborator
 */
public class RegisterCollaboratorController {
    /**
     * This variable represent the Collaborator Repository
     */
    public CollaboratorRepository collaboratorRepository=getCollaboratorRepository();
    /**
     * This variable represent the JobCategory Repository
     */
    public JobCategoryRepository jobCategoryRepository=getJobCategoryRepository();

    /**This method initializes the repository of collaborators
     *
     * @return Repository of Collaborators
     */
    public CollaboratorRepository getCollaboratorRepository(){
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the Collaborator Repository
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    /**This method initializes the repository of job categories
     *
     * @return Repository of Job Categories
     */
    public JobCategoryRepository getJobCategoryRepository() {
        if (jobCategoryRepository == null) {
            Repositories repositories = Repositories.getInstance();

            // Getting the JobCategory Repository
            jobCategoryRepository = repositories.getJobCategoryRepository();
        }
        return jobCategoryRepository;
    }


    /**After the verification made in the UI, this constructor method registers the collaborator in the repository
     *
     * @param name
     * @param birthday
     * @param admissionDate
     * @param address
     * @param addressCity
     * @param addressZipCode
     * @param phoneNumber
     * @param email
     * @param docType
     * @param docIDNumber
     * @param jobCategory
     */
    public RegisterCollaboratorController(String name, Date birthday, Date admissionDate, String address, String addressCity, String addressZipCode, int phoneNumber, String email, DocType docType, int docIDNumber, JobCategory jobCategory) {
        collaboratorRepository.registerCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode, email, phoneNumber, docType, docIDNumber, jobCategory);
    }

    private void getHRMFromSession(){

    }

}
