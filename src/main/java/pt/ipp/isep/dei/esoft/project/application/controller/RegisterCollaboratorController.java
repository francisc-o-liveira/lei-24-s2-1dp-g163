package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Calendar;
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
    public void registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity,String email, int phoneNumber, DocType docType, int docIDNumber, JobCategory jobCategory, Collaborator.StatusType statusType){
        if(verifyData(name, birthday, admissionDate, address, addressZipCode, addressCity, phoneNumber, email, docType, docIDNumber, jobCategory, statusType)){

        }
        Collaborator collaborator = new Collaborator(name, birthday, admissionDate,address, addressZipCode, addressCity,phoneNumber,email, docType, docIDNumber, jobCategory, statusType);
    }

    private boolean verifyData(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber,String email, DocType docType, int docIDNumber, JobCategory jobCategory, Collaborator.StatusType statusType) {
        return (verifyName(name) && verifyBirthdayAndAdmission(birthday,admissionDate) && verifyAddress(address,addressZipCode,addressCity) && verifyPhoneNumber(phoneNumber) && verifyEmail(email) && verifyStatus(statusType));
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

    private boolean verifyStatus(Collaborator.StatusType statusType) {
        return statusType.equals(Collaborator.StatusType.Active) || statusType.equals(Collaborator.StatusType.NotActive);
    }

    private boolean verifyPhoneNumber(int phoneNumber) {
        return (phoneNumber%1000000000)>0.9 && (phoneNumber%1000000000)<1 &&( (phoneNumber/10000000)==91 || (phoneNumber/10000000)==92 || (phoneNumber/10000000)==93 || (phoneNumber/10000000)==96 );
    }

    private boolean verifyAddress(String address, String addressZipCode, String addressCity) {
        return (addressZipCode.split("-").length==2 && addressCity.split(" ").length<5);
    }

    private boolean verifyBirthdayAndAdmission(Date birthday, Date admissionDate) {
        Date outraData = new Date(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if(birthday.getNumberOfDaysOfLife(outraData)>6574){
            return true;
        }
        return false;
    }
    public static Date dataAtual() {
        Calendar hoje = Calendar.getInstance();
        int ano = hoje.get(Calendar.YEAR);
        int mes = hoje.get(Calendar.MONTH) + 1;    // janeiro é representado por 0
        int dia = hoje.get(Calendar.DAY_OF_MONTH);
        return new Date(ano, mes, dia);
    }

    private boolean verifyName(String name) {
        String[]arrayNeedSize=name.split(" ");
        return arrayNeedSize.length<=6;
    }

    public DocType.Type[] getDocTypeList(){
        return DocType.Type.values();
    }

    private void getHRMFromSession(){

    }

}
