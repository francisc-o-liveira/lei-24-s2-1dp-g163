package pt.ipp.isep.dei.esoft.project.domain.collaborator;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Optional;

public class Collaborator {
    private String name;
    private Date birthday;
    private Date admissionDate;
    private String address;
    private String addressZipCode;
    private String addressCity;
    private int phoneNumber;
    private DocType.Type docType;
    private int docIDNumber;
    private String email;
    private JobCategory jobCategory;
    private List<Skill> skills;
    public enum StatusType {Active,NotActive}
    private StatusType statusType;
    private List<Skill> SKILLS_BY_OMISSION=null;

    /**
     * This method it is for trade the statusType
     *
     * @param statusType represent the new status for the collaborator
     * @return
     */
    public void setStatus(StatusType statusType) {
        this.statusType=statusType;
    }

    public Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, String email, DocType.Type docType, int docIDNumber, JobCategory jobCategory){
        this.name=name;
        this.birthday=birthday;
        this.admissionDate=admissionDate;
        this.address=address;
        this.addressZipCode=addressZipCode;
        this.addressCity=addressCity;
        this.phoneNumber=phoneNumber;
        this.docType=docType;
        this.docIDNumber=docIDNumber;
        this.jobCategory=jobCategory;
        this.statusType=StatusType.NotActive;
        this.skills=SKILLS_BY_OMISSION;

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
    public Optional<Collaborator> setAddSkill(Skill skill){
        Optional<Collaborator> collabWithNewSkill = Optional.empty();
        collabWithNewSkill = Optional.of(this);
        if(!verifyIfHaveSkill(skill)){
            this.skills.add(skill);
        }else {
            Optional.empty();
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
