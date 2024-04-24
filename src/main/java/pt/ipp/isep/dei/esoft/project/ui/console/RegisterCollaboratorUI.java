package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType.Type;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This Class represents the UI to register a collaborator */
public class RegisterCollaboratorUI implements Runnable{

    /**All the parameters needed to register a collaborator*/
    private String name;
    private Date birthday;
    private Date admissionDate;
    private String address;
    private String addressZipCode;
    private String addressCity;
    private int phoneNumber;
    private DocType docType;
    private int docIDNumber;
    private String email;
    private JobCategory jobCategory;

    Scanner scan= new Scanner(System.in);


    /**Controller*/
    public RegisterCollaboratorController controller= new RegisterCollaboratorController();


    public void run(){
        System.out.print("--------- Register a Collaborator ---------\n");
        registerCollaborator();
    }


    /**Method to register a collaborator
     *
     */
    public void registerCollaborator(){
        registerName();
        registerDates();
        registerAddress();
        registerPhoneNumber();
        registerEmail();
        registerDocType();
        registerJobCategory();
        controller.registerCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode,  phoneNumber, email, docType, docIDNumber, jobCategory);
        System.out.println("Collaborator registered!");
    }

    /**Register the name of collaborator
     *
     * If the name is longer than six words, the user needs to re-introduce the name
     */
    public void registerName(){
        boolean validName=false;
        while(!validName){
            System.out.print("Name of collaborator: ");
            name= scan.next();
            if(!verifyName(name)){
                throw new IllegalArgumentException("The introduced name is incorrect.");
            } else {
                validName=true;
            }
        }
    }

    /**Register the date of birth and the date of admission of collaborator
     *
     * If the collaborator is younger than 18 years old, the user needs to re-introduce the dates
     */
    public void registerDates(){
        boolean validDate=false;
        while(!validDate){
            System.out.print("Date of birth (Format: YYYY MM DD): ");
            birthday.setData(scan.nextInt(), scan.nextInt(), scan.nextInt());
            System.out.print("Date of admission (Format: YYYY MM DD): ");
            admissionDate.setData(scan.nextInt(), scan.nextInt(), scan.nextInt());
            if(!verifyBirthdayAndAdmission(birthday, admissionDate)){
                throw new IllegalArgumentException("The collaborator cannot be under 18.");
            }
        }
    }

    /**Register the address of collaborator
     *
     * If any of the fields for addresss are incorrect, the user needs to re-introduce it
     */
    public void registerAddress(){
        boolean validAddress=false;
        while(!validAddress){
            System.out.print("Address of collaborator: ");
            address= scan.next();
            System.out.print("City: ");
            addressCity= scan.next();
            System.out.print("Zip Code (Format: XXXX-XXX): ");
            addressZipCode= scan.next();
            if(!verifyAddress(address,addressCity,addressZipCode)){
                throw new IllegalArgumentException("The introduced address is incorrect.");
            } else {
                validAddress=true;
            }
        }
    }

    /**Register the phone number of collaborator
     *
     * If the phone number is longer than 9 digits or, in the international case, fewer than 6 digits or longer than 14 digits,
     * the user needs to re-introduce it
     */
    public void registerPhoneNumber(){
        boolean validPhoneNumber =false;
        String codePhoneNumber;
        while(!validPhoneNumber){
            System.out.print("Country telephone code: ");
            codePhoneNumber=scan.next();
            System.out.print("Phone number: ");
            phoneNumber=scan.nextInt();
            String phone=codePhoneNumber+ phoneNumber;
            if(!verifyPhoneNumber(phoneNumber)){
                throw new IllegalArgumentException("The introduced phone number is incorrect.");
            } else if(!codePhoneNumber.equals("+351") && !verifyInternationalPhoneNumber(phone)) {
                throw new IllegalArgumentException("The introduced phone number is incorrect.");
            } else {
                validPhoneNumber=true;
            }
        }
    }

    /**Register the e-mail of collaborator
     *
     * If the e-mail does not a prefix, "@" and a domain, containing a ".", the user needs to re-introduce it
     */
    public void registerEmail(){
        boolean validEmail=false;
        while(!validEmail){
            System.out.print("E-mail (Format: ): ");
            email=scan.next();
            if(!verifyEmail(email)){
                throw new IllegalArgumentException("The introduced email is incorrect.");
            } else {
                validEmail=true;
            }
        }
    }

    /**Register the document of identification of collaborator
     *
     * If the user chooses a wrong option, it is required to choose again
     * If the user introduces a wrong number according to its document type, the user needs to re-introduce it
     */
    public void registerDocType(){
        boolean validDocType=false;
        while(!validDocType){
            System.out.print("Select one of the following for the type of document: \n");
            System.out.print("1- CitizenCard\n");
            System.out.print("2- TaxPayerCard\n");
            System.out.print("3- Passport\n");
            switch (scan.nextInt()){
                case 1:
                    docType=new DocType(Type.CitizenCard);
                break;
                case 2:
                    docType=new DocType(Type.TaxPayerCard);
                break;
                case 3:
                    docType=new DocType(Type.Passport);
                break;
                default:
                    System.out.println("Invalid choice. Please select a valid option (1, 2, or 3).");
                continue;
            }
            System.out.print("ID Number from Document of Identification: ");
            docIDNumber=scan.nextInt();
            if(!validateDocType(docType,docIDNumber)){
                throw new IllegalArgumentException("The introduced document of identification is incorrect.");
            } else {
                validDocType=true;
            }
        }
    }

    /**Register a job for collaborator
     *
     * If the job registered does not exist in the system, the user needs to re-introduce it
     */
    public void registerJobCategory(){
        boolean validJob = false;
        while (!validJob) {
            System.out.print("Job of Collaborator: ");
            jobCategory.setTradeName(scan.next());

            if (verifyIfJobCategoryExists(jobCategory)) {
                validJob= true;
            } else {
                System.out.println("The introduced Job Category does not exist. Please try again.");
            }
        }
    }



    /**
     * This method verifies the DocType Number by DocType
     * @param type of document (Passport,TaxPayer and CitizenCard)
     * @param docTypeNumber represent the value introduce by User to register Collaborator
     * @return true if verify the value by there docType
     */
    public boolean validateDocType(DocType type, int docTypeNumber){
        return type.verifyDocType(docTypeNumber);
    }

    /**Verifies if e-mail is correctly inserted
     *
     * @param email of collaborator
     * @return true if e-mail is correct
     */

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

    /**Verifies if phone number is correctly inserted
     *
     * @param phoneNumber of collaborator
     * @return true if phoneNumber is correct
     */

    private boolean verifyPhoneNumber(int phoneNumber) {
        return (phoneNumber%1000000000)>0.9 && (phoneNumber%1000000000)<1 &&( (phoneNumber/10000000)==91 || (phoneNumber/10000000)==92 || (phoneNumber/10000000)==93 || (phoneNumber/10000000)==96 );
    }

    /** Using Java Regex, this method verifies an international phone number
     *
     * @param phone of collaborator to validate
     * @return true if phoneNumber starts with a plus sign followed by 6 to 14 digits with optional spaces between them
     *
     * */

    private boolean verifyInternationalPhoneNumber(String phone){
        String verify="^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(verify);
        Matcher matcher=pattern.matcher(phone);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    /**Verifies if address is correctly inserted
     *
     * @param address of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity of collaborator
     * @return true if address is correct
     */
    private boolean verifyAddress(String address, String addressZipCode, String addressCity) {
        return (addressZipCode.split("-").length==2 && addressCity.split(" ").length<5);
    }

    /**Verifies if the date of birth and date of admission is correctly inserted
     *
     * @param birthday of collaborator
     * @param admissionDate of collaborator
     * @return true if birthday and admissionDate are correct
     */

    private boolean verifyBirthdayAndAdmission(Date birthday, Date admissionDate) {
        if(birthday.diference(Date.atualDate())>6574){
            return true;
        }
        return false;
    }

    /**Verifies if the name is valid
     *
     * @param name of collaborator
     * @return true if name contains six words or fewer
     */

    private boolean verifyName(String name) {
        String[]arrayNeedSize=name.split(" ");
        return arrayNeedSize.length<=6;
    }

    /**Verifies if Job Category exists
     *
     * @param jobCategory of collaborator
     * @return true if job exists
     */

    private boolean verifyIfJobCategoryExists(JobCategory jobCategory){
        List<JobCategory> jobCategoryList=controller.jobCategoryRepository.getJobCategoryList();
        if(jobCategoryList.contains(jobCategory)){
            return true;
        }
        return false;
    }
}
