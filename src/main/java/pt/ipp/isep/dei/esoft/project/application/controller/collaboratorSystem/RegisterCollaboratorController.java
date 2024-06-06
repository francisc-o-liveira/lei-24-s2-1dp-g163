package pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem;
import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType.Type;

import java.util.List;
import java.util.Optional;

/**
 * This Class represents the Controller to Register a Collaborator
 */
public class RegisterCollaboratorController {
    /**
     * This variable represents the Collaborator Repository
     */
    private CollaboratorRepository collaboratorRepository;
    /**
     * This variable represents the JobCategory Repository
     */
    private JobCategoryRepository jobCategoryRepository;


    private RegisterCollaboratorController() {
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

    /** Gets the repository of Collaborator
     * @return Repository of Collaborators
     */

    public CollaboratorRepository getCollaboratorRepository(){
        return collaboratorRepository;
    }
    /**
     * This method registers a collaborator from collaborator's Repository
     *
     * @param name           of collaborator
     * @param birthday       of collaborator
     * @param admissionDate  of collaborator
     * @param address        of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity    of collaborator
     * @param email          of collaborator
     * @param phoneNumber    of collaborator
     * @param docType        of collaborator
     * @param docIDNumber    of collaborator
     * @param jobCategory    of collaborator
     * @return Optional of Collaborator if Collaborator has been successfully registered; null if Collaborator wasn't registered
     */
    public Optional<Collaborator> registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressCity, String addressZipCode, String phoneNumber, String email, Type docType, int docIDNumber, JobCategory jobCategory) throws CloneNotSupportedException {
        Optional<Collaborator> newCollab = collaboratorRepository.createCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode, email, phoneNumber, docType, docIDNumber, jobCategory);
        return newCollab;
    }

    /**
     * This method return the docTypes that exist to register the user
     * @return an Array of Types of a Enum Type
     */
    public Type[] getDocTypeList() {
        return DocType.Type.values();
    }

    /**
     * This method get the job category's list of the job category repository
     * @return the job category existent registed by an HRM or GSM
     */
    public List<JobCategory> getJobCategoryList() {
        return jobCategoryRepository.getJobCategoryList();
    }

    /** Gets the List of Collaborators
     *
     * @return List of Collaborators
     */
    public List<Collaborator> getCollaboratorList(){
        return Repositories.getInstance().getCollaboratorRepository().getCollaboratorList();
    }

    /** Removes a Collaborator from the List
     *
     * @param collaborator to remove
     */

    public void removeFromList(Collaborator collaborator){
        collaboratorRepository.removeFromList(collaborator);
    }

    /*private void getHRMFromSession(){

    }*/

    private static RegisterCollaboratorController instance;
    public static RegisterCollaboratorController getInstance(){
        if(instance == null){
            synchronized (RegisterCollaboratorController.class) {
                instance = new RegisterCollaboratorController();
            }
        }
        return instance;
    }
}
