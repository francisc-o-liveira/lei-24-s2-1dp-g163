package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Collaborator{
    private static final JobCategory JOBCATEGORY_OMISSION = new JobCategory("NONE");
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
    public enum StatusType {Active,NotActive}
    private StatusType statusType;
    private List<Skill> SKILLS_BY_OMISSION=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.split(" ").length<=6 && verifyIsOnlyCharacter(name)){
            this.name = name;
        }else {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
    }

    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if (birthday.diference(Date.atualDate())>6574){
            this.birthday = birthday;
        }else {
            throw new IllegalArgumentException("Invalid birthday: " + birthday);
        }
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(verifyPhoneNumber(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }else {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }

    /** Using Java Regex, this method verifies an international phone number
     *
     * @param phoneNumber of collaborator to validate
     * @return true if phoneNumber starts with a plus sign followed by 6 to 14 digits with optional spaces between them
     *
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

    public DocType.Type getDocType() {
        return docType;
    }
    /**
     * This method verify if the docIDNumber is valid to add for collaborator
     * @param docType the docType (passport, citizen card, ...)
     * @param docIDNumber the docIDNumber correspondent to the collab!
     */

    public void setDocType(DocType.Type docType,int docIDNumber) {
        if (DocType.verifyDocType(docType,docIDNumber)){
            this.docType = docType;
            this.docIDNumber = docIDNumber;
        }else{
            throw new IllegalArgumentException("Invalid document type: " + docType);
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(verifyEmail(email)){
            this.email = email;
        }else {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
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
                domain=domainPrefix[1].split("\\.");
                value = domain.length==2;
                if (value){
                    value= domain[1].equals("com") || domain[1].equals("pt");
                }
            }
        }
        return value;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }


    public void setSKILLS_BY_OMISSION(List<Skill> SKILLS_BY_OMISSION) {
        this.skills = SKILLS_BY_OMISSION;
    }


    /**
     * This method it is for trade the statusType
     *
     * @param statusType represent the new status for the collaborator
     * @return
     */
    public void setStatus(StatusType statusType) {
        this.statusType=statusType;
    }

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
        setStatusType(StatusType.Active);
        setSKILLS_BY_OMISSION(SKILLS_BY_OMISSION);
    }

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
        setStatusType(StatusType.Active);
        setSKILLS_BY_OMISSION(SKILLS_BY_OMISSION);
    }

    /**
     * Return the number of Skills
     * @return number Skills in the collaborator
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

    /**
     * This method is for get the status of collaborator{Activate or NotActivate}
     * @return
     */
    public StatusType getStatus(){
        return this.statusType;
    }


    /**
     *  This method is for add Skills to the collaborator and verify if the collaborator already have the Skill (Assign Skill)
     * @param skill represent the skill to assign to the collaborator
     * @return the collaborator if assign the skill to the collaborator
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
     * This method verify if the collaborator have or no one skill selected by user
     * @param skill selected by the user to check if the collab have or no
     * @return true if the collab have this skill
     */

    public boolean verifyIfHaveSkill(Skill skill) {
        return skills.contains(skill);
    }

    /**
     * This method is for get the all Skills of the Collaborator
     * @return List of Skills (of the Collaborator)
     */

    public List<Skill> getSkills(){
        return List.copyOf(skills);
    }

    /**
     * This method is for get the DocIDNumber of the collaborator
     * @return the docIDNumber
     */

    public int getDocIDNumber(){
        return this.docIDNumber;
    }

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


}
