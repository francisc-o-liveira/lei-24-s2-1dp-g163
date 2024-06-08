package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  Domain Class for Collaborator Object */
public class Collaborator implements Serializable {

    /** Parameters needed for Collaborator */
    private String name;
    private Date birthday;
    private Date admissionDate;
    private String address;
    private String addressZipCode;
    private String addressCity;
    private String phoneNumber;
    private DocType.Type docType;
    private int docIDNumber;
    private String email;
    private JobCategory jobCategory;
    private List<Skill> skills;
    public enum StatusType implements Serializable {Active,NotActive}
    private StatusType statusType;

    /** When created, the Collaborator does not own any skills */
    private List<Skill> SKILLS_BY_OMISSION=new ArrayList<>();
    private static final JobCategory JOBCATEGORY_OMISSION = new JobCategory("NONE");

    /** Gets the name of collaborator
     *
     * @return name of collaborator
     */
    public String getName() {
        return name;
    }

    /** Sets the name of collaborator after verification
     *
     * A name is only valid if it is shorter than 6 words and only contains characters
     * @param name of Collaborator
     */
    public void setName(String name) {
        if (name.split(" ").length<=6 && verifyIsOnlyCharacter(name)){
            this.name = name;
        }else {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
    }

    /** Method to verify if the name only contains characters
     *
     * @param name of Collaborator
     * @return true if the name only contains characters
     */
    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

    /** Gets the birthday of Collaborator
     *
     * @return date of birth of Collaborator
     */

    public Date getBirthday() {
        return birthday;
    }

    /** Sets the birthday of Collaborator after verifying if Collaborator is older than 18 years
     *
     */

    public void setBirthday(Date birthday) {
        if (birthday.diference(Date.atualDate())>6574){
            this.birthday = birthday;
        }else {
            throw new IllegalArgumentException("Invalid birthday: " + birthday);
        }
    }

    /** Gets the date of admission of Collaborator
     *
     * @return admission date of Collaborator
     */

    public Date getAdmissionDate() {
        return admissionDate;
    }

    /** Sets the date of admission of Collaborator */
    public void setAdmissionDate(Date admissionDate) {
        if(admissionDate.compareTo(birthday)>0){
        this.admissionDate = admissionDate;
        } else {
            throw new IllegalArgumentException("Invalid admission date: " + admissionDate);
        }
    }

    /** Gets the address of Collaborator
     *
     * @return Collaborator's address
     */

    public String getAddress() {
        return address;
    }

    /** Sets the address of Collaborator
     *
     * @param address of Collaborator
     */

    public void setAddress(String address) {
        if(verifyIsOnlyCharacterOrDigit(address)){
            this.address = address;
        }else{
            throw new IllegalArgumentException("Invalid address: " + address);
        }
    }

    private boolean verifyIsOnlyCharacterOrDigit(String address) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c) && !Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    /** Gets the zip code of the address of Collaborator
     *
     * @return Collaborator's zip code
     */
    public String getAddressZipCode() {
        return addressZipCode;
    }

    /** Sets the zip code of the address of Collaborator
     *
     * @param addressZipCode of Collaborator
     */

    public void setAddressZipCode(String addressZipCode) {
        if(verifyZipCode(addressZipCode)){
            this.addressZipCode = addressZipCode;
        }else{
            throw new IllegalArgumentException("Invalid address zip code: " + addressZipCode);
        }
    }

    private boolean verifyZipCode(String zipCode) {
        if (zipCode.matches("\\d{4}-\\d{3}")) {
            return true;
        } else {
            return false;
        }
    }


    /** Gets the city of the address of Collaborator
     *
     * @return Collaborator's city address
     */
    public String getAddressCity() {
        return addressCity;
    }

    /** Sets the city of the address of Collaborator
     *
     * @param addressCity of Collaborator
     */
    public void setAddressCity(String addressCity) {
        if (verifyIsOnlyCharacter(addressCity)){
            this.addressCity = addressCity;
        }else {
            throw new IllegalArgumentException("Invalid address city: " + addressCity);
        }
    }

    /** Gets the phone number of Collaborator
     *
     * @return Collaborator's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Sets the phone number of Collaborator
     *
     * @param phoneNumber of Collaborator
     */
    public void setPhoneNumber(String phoneNumber) {
        if(verifyPhoneNumber(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }else {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }

    /** Using Java Regex, this method verifies a phone number
     *
     * @param phoneNumber of collaborator to validate
     * @return true if phoneNumber starts with a plus sign followed by 6 to 14 digits with optional spaces between them
     * */

    private boolean verifyPhoneNumber(String phoneNumber) {
        String verify="^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(verify);
        Matcher matcher=pattern.matcher(phoneNumber);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    /** Gets the type of identification document
     *
     * @return type of identification document
     */

    public DocType.Type getDocType() {
        return docType;
    }
    /**
     * This method verify if the docIDNumber is valid to add for collaborator
     * @param docType type of identification document  (passport, citizen card, ...)
     * @param docIDNumber the identification number from the document correspondent to the Collaborator
     */

    public void setDocType(DocType.Type docType,int docIDNumber) {
        if (DocType.verifyDocType(docType,docIDNumber)){
            this.docType = docType;
            this.docIDNumber = docIDNumber;
        }else{
            throw new IllegalArgumentException("Invalid document type: " + docType);
        }
    }


    /** Gets the e-mail address of Collaborator
     *
     * @return Collaborator's e-mail address
     */
    public String getEmail() {
        return email;
    }

    /** Sets the e-mail address of Collaborator after being verified
     *
     * @param email of Collaborator
     */

    public void setEmail(String email) {
        if(verifyEmail(email)){
            this.email = email;
        }else {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }
    /**Verifies if e-mail is correctly inserted
     *
     *  An e-mail is correct if it contains the "@" followed by a domain and if it has a prefix
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
                domain=domainPrefix[1].split("\\.");
                value = domain.length==2;
                if (value){
                    value= domain[1].equals("com") || domain[1].equals("pt");
                }
            }
        }
        return value;
    }

    /** Gets the Job Category of Collaborator
     *
     * @return Collaborator's Job Category
     */

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    /**
     * This method is for get the all Skills of the Collaborator
     * @return List of Skills (of the Collaborator)
     */

    public List<Skill> getSkills(){
        return List.copyOf(skills);
    }

    /**
     * This method gets the identification number of Collaborator
     * @return Collaborators' identification number
     */

    public int getDocIDNumber(){
        return this.docIDNumber;
    }

    /**
     * Returns the number of Skills of Collaborator
     * @return quantity of Skills Collaborator has
     */

    public int getNumberOfSkills(){
        int count=0;
        if(skills != null){
            for(Skill s : skills){
                count++;
            }
        }
        return count;
    }

    /** Gets the Status of a Collaborator
     *
     * @return Collaborators' Status
     */
    public StatusType getStatus(){
        return this.statusType;
    }

    /** Sets the Job Category for Collaborator
     *
     * @param jobCategory to be assigned to Collaborator
     */

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    /** Sets the skills to Collaborator
     *
     * @param skills for Collaborator
     */

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    /** Sets the Status of a Collaborator
     *
     * @param statusType for Collaborator
     */

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /** When created, the Collaborator doesn't own any skills
     *
     * @param SKILLS_BY_OMISSION
     */
    public void setSKILLS_BY_OMISSION(List<Skill> SKILLS_BY_OMISSION) {
        this.skills = SKILLS_BY_OMISSION;
    }


    /**
     * This method changes the status of Collaborator
     *
     * @param statusType represent the new status for the collaborator
     * @return
     */
    public void setStatus(StatusType statusType) {
        this.statusType=statusType;
    }

    /** Constructor method for Collaborator
     *
     * @param name of Collaborator
     * @param birthday of Collaborator
     * @param admissionDate of Collaborator
     * @param address of Collaborator
     * @param addressZipCode of Collaborator
     * @param addressCity of Collaborator
     * @param phoneNumber of Collaborator
     * @param email of Collaborator
     * @param docType of Collaborator
     * @param docIDNumber of Collaborator
     * @param jobCategory of Collaborator
     */
    public Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String phoneNumber, String email, DocType.Type docType, int docIDNumber, JobCategory jobCategory){
        setName(name);
        setBirthday(birthday);
        setAdmissionDate(admissionDate);
        setAddress(address);
        setAddressZipCode(addressZipCode);
        setAddressCity(addressCity);
        setPhoneNumber(phoneNumber);
        setDocType(docType,docIDNumber);
        setEmail(email);
        setJobCategory(jobCategory);
        setStatusType(StatusType.NotActive);
        setSKILLS_BY_OMISSION(SKILLS_BY_OMISSION);
    }

    /** Constructor method for Collaborator if a Job Category is not selected
     *
     * @param name of Collaborator
     * @param birthday of Collaborator
     * @param admissionDate of Collaborator
     * @param address of Collaborator
     * @param addressZipCode of Collaborator
     * @param addressCity of Collaborator
     * @param phoneNumber of Collaborator
     * @param email of Collaborator
     * @param docType of Collaborator
     * @param docIDNumber of Collaborator
     *
     */
    public Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String phoneNumber, String email, DocType.Type docType, int docIDNumber) {
        setName(name);
        setBirthday(birthday);
        setAdmissionDate(admissionDate);
        setAddress(address);
        setAddressZipCode(addressZipCode);
        setAddressCity(addressCity);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setDocType(docType,docIDNumber);
        setJobCategory(JOBCATEGORY_OMISSION);
        setStatusType(StatusType.NotActive);
        setSKILLS_BY_OMISSION(SKILLS_BY_OMISSION);
    }


    /**
     *  This method to add Skills to the collaborator and verify if the collaborator already has the Skill (Assign Skill)
     * @param skill represent the skill to assign to the collaborator
     * @return the collaborator if skill has been assigned
     */
    public Optional<Collaborator> setAddSkill(Skill skill) throws CloneNotSupportedException {
        Optional<Collaborator> collabWithNewSkill;
        collabWithNewSkill = Optional.of(this);
        if(!verifyIfHaveSkill(skill)){
            this.skills.add(skill);
        }else {
            collabWithNewSkill=Optional.empty();
            throw new CloneNotSupportedException("The Collaborator already have this Skill");
        }
        return collabWithNewSkill;
    }

    /**
     * This method verifies if the collaborator has or not the skill selected by user
     * @param skill selected by the user to check if Collaborator has the skill
     * @return true if Collaborator has this skill
     */

    public boolean verifyIfHaveSkill(Skill skill) {
        return skills.contains(skill);
    }

    /** Method to get the data of Collaborator in a String
     *
     * @return String with the data of Collaborator
     */
    @Override
    public String toString(){
        return String.format("Name: %s\n" +
                "Birthday: %s\n" +
                "Admission Date: %s\n" +
                "Address: %s, %s, %s\n" +
                "Phone Number: %s\n" +
                "Document Type: %s\n" +
                "Document ID Number: %d\n" +
                "Email: %s\n" +
                "Job Category: %s\n" +
                "Status Type: %s\n" +
                "Skills: %s\n", name, birthday, admissionDate, address, addressCity, addressZipCode, phoneNumber, docType, docIDNumber, email, jobCategory, statusType, skills);
    }

    /** Method to compare if two Collaborators are the same
     *
     * Two Collaborators are the same if both have the same ID Number
     *
     * @param other - Collaborator to be compared along with other
     * @return true if they are the same
     */

    @Override
    public boolean equals(Object other){
        if(this==other){
            return true;
        }
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Collaborator otherCollab=(Collaborator) other;

        return this.getDocIDNumber()==otherCollab.getDocIDNumber();
    }

    /** Converts a date to LocalDate format
     *
     * @param date to converted
     * @return date in LocalDate format
     */

    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

    /** Gets the birthday date in LocalDate format
     *
     * @return birthday in LocalDate format
     */

    public LocalDate getBirthdayLocal() {
        return convertToJavaLocalDate(this.birthday);
    }

    /** Gets the admission date in LocalDate format
     *
     * @return admission date in LocalDate format
     */

    public LocalDate getAdmissionDateLocal(){
        return convertToJavaLocalDate(this.admissionDate);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
