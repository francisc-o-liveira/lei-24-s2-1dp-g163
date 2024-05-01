package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType.Type;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** This Class represents the UI to register a collaborator */
public class RegisterCollaboratorUI implements Runnable{

    /**All the parameters needed to register a collaborator*/
    private String name;
    private Date birthday;
    private Date admissionDate;
    private String address;
    private String addressZipCode;
    private String addressCity;
    private String phoneNumber;
    private Type docType;
    private int docIDNumber;
    private String email;
    private JobCategory jobCategory;

    Scanner scan= new Scanner(System.in);
    
    /**Controller*/
    public RegisterCollaboratorController ctrl;
    public RegisterCollaboratorUI() {
        ctrl= new RegisterCollaboratorController();
    }

    private RegisterCollaboratorController getController() {
        return ctrl;
    }

    public void run(){
        System.out.print("--------- Register a Collaborator ---------\n");
        jobCategory=displayAndSelectJobCategory();
        docType=displayAndSelectDocType();
        docIDNumber=registerDocIDNumber();
        requestData();
        submitData();
    }

    private int registerDocIDNumber() {
        System.out.print("ID Number from Document of Identification: ");
        boolean operationSuccess = false;
        int docIDNumber = 0;
        while(docIDNumber <= 0) {
            docIDNumber = scan.nextInt();
        }
        return docIDNumber;
    }

    private JobCategory displayAndSelectJobCategory() {
        List<JobCategory> jobCategoryList = ctrl.getJobCategoryList();
        int indexOfJobCategory = -1;
        Scanner input = new Scanner(System.in);
        while (indexOfJobCategory < 1 || indexOfJobCategory > jobCategoryList.size()) {
            displayJobCategoryOptions(jobCategoryList);
            System.out.print("Select a task category: ");
            indexOfJobCategory = input.nextInt();
        }
        return jobCategoryList.get(indexOfJobCategory - 1);
    }

    private void displayJobCategoryOptions(List<JobCategory> jobCategoryList) {
        int i = 1;
        for (JobCategory jobCategory : jobCategoryList) {
            System.out.println("  " + i + " - " + jobCategory.getName());
            i++;
        }
    }



    /**Method to register a collaborator
     *
     */
    public void requestData(){
        name=registerName();
        registerDates();
        registerAddress();
        phoneNumber=registerPhoneNumber();
        email=registerEmail();
    }
    private void submitData() {
        Optional<Collaborator> collaborator = getController().registerCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode,  phoneNumber, email, docType, docIDNumber, jobCategory);
        if (collaborator.isPresent()) {
            System.out.println("\nCollaborator successfully created!");
        } else {
            System.out.println("\nCollaborator not created!");
        }
    }

    /**
     * Register the name of collaborator
     * <p>
     * If the name is longer than six words, the user needs to re-introduce the name
     *
     * @return the name registed
     */
    public String registerName(){
        boolean validName=false;
        String name = "";
        while(!validName){
            System.out.print("Name of collaborator: ");
            name = scan.next();
            if(!verifyName(name)){
                throw new IllegalArgumentException("The introduced name is incorrect.");
            } else {
                validName=true;
            }
        }
        return name;
    }

    /**Register the date of birth and the date of admission of collaborator
     *
     * If the collaborator is younger than 18 years old, the user needs to re-introduce the dates
     */
    public void registerDates(){
            System.out.print("Date of birth (Format: YYYY MM DD): ");
            birthday.setData(scan.nextInt(), scan.nextInt(), scan.nextInt());
            System.out.print("Date of admission (Format: YYYY MM DD): ");
            admissionDate.setData(scan.nextInt(), scan.nextInt(), scan.nextInt());
            if(!verifyBirthdayAndAdmission(birthday, admissionDate)){
                throw new IllegalArgumentException("The collaborator cannot be under 18.");
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

    /**
     * Register the phone number of collaborator
     * <p>
     * If the phone number is longer than 9 digits or, in the international case, fewer than 6 digits or longer than 14 digits,
     * the user needs to re-introduce it
     *
     * @return phone number register by user
     */
    public String registerPhoneNumber(){
        boolean validPhoneNumber =false;
        String codePhoneNumber;
        String phoneNumber = "";
        while(!validPhoneNumber){
            System.out.print("Country telephone code: ");
            codePhoneNumber = scan.next();
            System.out.print("Phone number: ");
            int phone = scan.nextInt();
            phoneNumber = codePhoneNumber + phone;
            if(!verifyCodePhoneNumber(codePhoneNumber) || !verifyPhoneNumber(phone)){

                throw new IllegalArgumentException("The introduced phone number is incorrect.");
            }else {
                validPhoneNumber=true;
            }
        }
        return phoneNumber;
    }

    private boolean verifyCodePhoneNumber(String codePhoneNumber) {
        return codePhoneNumber.split("").length <= 4;
    }

    /**
     * Register the e-mail of collaborator
     * <p>
     * If the e-mail does not a prefix, "@" and a domain, containing a ".", the user needs to re-introduce it
     *
     * @return email registed by the user
     */
    public String registerEmail(){
        boolean validEmail=false;
        String email = "";
        while(!validEmail){
            System.out.print("E-mail (Format: ): ");
            email=scan.next();
            if(!verifyEmail(email)){
                throw new IllegalArgumentException("The introduced email is incorrect.");
            } else {
                validEmail=true;
            }
        }
        return email;
    }

    /**
     * Register the document of identification of collaborator
     * <p>
     * If the user chooses a wrong option, it is required to choose again
     * If the user introduces a wrong number according to its document type, the user needs to re-introduce it
     *
     * @return
     */
    public Type displayAndSelectDocType(){
        Type[] types = ctrl.getDocTypeList();
        Scanner scan = new Scanner(System.in);

            System.out.print("Select one of the following types of document of identification: \n");
            for(int i = 0; i < types.length; i++){
                System.out.printf("%d --- %s%n", i+1,types[i]);
            }
            int option = -1;
            while(option<1 || option>3){
                option=scan.nextInt();
            }

        return types[option-1];
    }
    /**
     * This method verifies the DocType Number by DocType
     * @param type of document (Passport,TaxPayer and CitizenCard)
     * @param docIDNumber represent the value introduce by User to register Collaborator
     * @return true if verify the value by there docType
     */




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

}


