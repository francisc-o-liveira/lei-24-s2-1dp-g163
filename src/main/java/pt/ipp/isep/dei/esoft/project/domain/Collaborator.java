package pt.ipp.isep.dei.esoft.project.domain;

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
    private DocType docType;
    private int docIDNumber;
    private String email;
    private JobCategory jobCategory;
    private List<Skill> skills;
    public enum StatusType {Active,NotActive}
    private StatusType statusType;
    private List<Skill> SKILLS_BY_OMISSION=null;

    public void setStatus(StatusType statusType) {
        this.statusType=statusType;
    }

    public Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, String email, DocType docType, int docIDNumber, JobCategory jobCategory, StatusType status){
        this.name=name;
        this.address=address;
        this.addressZipCode=addressZipCode;
        this.addressCity=addressCity;
        this.phoneNumber=phoneNumber;
        this.docType=docType;
        this.docIDNumber=docIDNumber;
        this.jobCategory=jobCategory;
        this.statusType=status;
        this.skills=SKILLS_BY_OMISSION;

    }


    public int getNumberOfSkills(){
        int count=0;
        if(skills != null){
            for(Skill s : skills){
                count++;
            }
        }
        return count;
    }

    public StatusType getStatus(){
        return this.statusType;
    }



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

    public boolean verifyIfHaveSkill(Skill skill) {
        return skills.contains(skill);
    }

    public List<Skill> getSkills(){
        return List.copyOf(skills);
    }

    public int getDocIDNumber(){
        return this.docIDNumber;
    }




}
