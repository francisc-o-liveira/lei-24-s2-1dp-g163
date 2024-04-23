package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Calendar;
import java.util.List;

/**
 * This Class Represent the Controller for Register a Collaborator
 */
public class RegisterCollaboratorController {
    /**
     * This variable represent the Collaborator Repository
     */
    public CollaboratorRepository collaboratorRepository;
    /**
     * This variable represent the JobCategory Repository
     */
    public JobCategoryRepository jobCategoryRepository;

    /**
     * The Constructor Method of the controller go take the instances of the repositories
     */
    public RegisterCollaboratorController(){
        getDataNeededToRegister();
    }

    /**
     * This method get the instances of Job Category Repository and Collaborator Repository
     * It is needed to register a Collaborator (Data Needed)
     */
    public void getDataNeededToRegister() {
        if (jobCategoryRepository == null) {
            Repositories repositories = Repositories.getInstance();

            // Getting the JobCategory Repository
            jobCategoryRepository = repositories.getJobCategoryRepository();
        }
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the JobCategory Repository
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
    }

    /**
     * This method return the JobCategories List
     * @return List of JobCategory existent
     */

    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }

    /**
     * This method the Doc Type List
     * @return a Type List for Select (Passport,TaxPayer and CitizenCard)
     */
    public DocType.Type[] getDocTypeList(){
        return DocType.Type.values();
    }

    /**
     * This method verify the DocType Number by DocType
     * @param type of document (Passport,TaxPayer and CitizenCard)
     * @param docTypeNumber represent the value introduce by User to register Collab
     * @return true if verify the value by there docType
     */
    public boolean validateDocType(DocType type, int docTypeNumber){
        return type.verifyDocType(docTypeNumber);
    }

    /**
     * This method instance
     * @param name
     * @param birthday
     * @param admissionDate
     * @param address
     * @param addressZipCode
     * @param addressCity
     * @param email
     * @param phoneNumber
     * @param docType
     * @param docIDNumber
     * @param jobCategory

     */
    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String email, int phoneNumber, DocType docType, int docIDNumber, JobCategory jobCategory){
        collaboratorRepository.registerCollaborator(name, birthday, admissionDate, address, addressZipCode, addressCity, email,phoneNumber, docType, docIDNumber, jobCategory);


        // THE CONTROLLER DONT VERIFY DATA TO REGISTER A COLLABORATOR (CORRECTION THIS)
        if(verifyData(name, birthday, admissionDate, address, addressZipCode, addressCity, phoneNumber, email, docType, docIDNumber, jobCategory)){
            Collaborator collaborator = new Collaborator(name, birthday, admissionDate,address, addressZipCode, addressCity,phoneNumber,email, docType, docIDNumber, jobCategory);
        }
    }

    private void getHRMFromSession(){

    }


    // I THINK THIS METHODS GO TO UI

    private boolean verifyData(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber,String email, DocType docType, int docIDNumber, JobCategory jobCategory) {
        return (verifyName(name) && verifyBirthdayAndAdmission(birthday,admissionDate) && verifyAddress(address,addressZipCode,addressCity) && verifyPhoneNumber(phoneNumber) && verifyEmail(email));
    }

    private boolean verifyEmail(String email) {
        boolean value = false;
        String[] check = email.split("");
        for(String letter: check){
            if(letter.equals("@")){
                value=true;
            }
        }
        String[] domainPrefix;
        String[] domain;
        if(value){
            domainPrefix = email.split("@");
            value = domainPrefix.length==2;
            if (value){
                domain=domainPrefix[1].split(".");
                value = domain.length==2;
                if (value){
                    value= domain[1].equals("com") || domain[1].equals("pt");
                }
            }
        }
        return value;
    }



    private boolean verifyPhoneNumber(int phoneNumber) {
        return (phoneNumber%1000000000)>0.9 && (phoneNumber%1000000000)<1 &&( (phoneNumber/10000000)==91 || (phoneNumber/10000000)==92 || (phoneNumber/10000000)==93 || (phoneNumber/10000000)==96 );
    }

    private boolean verifyAddress(String address, String addressZipCode, String addressCity) {
        return (addressZipCode.split("-").length==2 && addressCity.split(" ").length<5);
    }

    private boolean verifyBirthdayAndAdmission(Date birthday, Date admissionDate) {
        if(birthday.diference(Date.atualDate())>6574){
            return true;
        }
        return false;
    }

    private boolean verifyName(String name) {
        String[]arrayNeedSize=name.split(" ");
        return arrayNeedSize.length<=6;
    }

    private boolean verifyInternationalPhoneNumber(int phoneNumber){
        return false;
    }

}
