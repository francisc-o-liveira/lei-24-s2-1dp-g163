package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;

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
    private int taxPayerNumber;
    private JobCategory jobCategory;
    private Skill skill;
    private List<Skill> skills;
    public enum StatusType {Active,NotActive}
    private StatusType statusType;
    private String collaboratorID;
    private List<Skill> SKILLS_BY_OMISSION=null;

    public Collaborator(String name, String collaboratorID, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType.Type docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, Skill skill, StatusType status){
        this.name=name;
        this.address=address;
        this.addressZipCode=addressZipCode;
        this.addressCity=addressCity;
        this.phoneNumber=phoneNumber;
        this.docType=docType;
        this.docIDNumber=docIDNumber;
        this.taxPayerNumber=taxPayerNumber;
        this.jobCategory=jobCategory;
        this.skill=skill;
        this.statusType=status;
        this.collaboratorID=collaboratorID;
        this.skills=SKILLS_BY_OMISSION;
    }

    public Collaborator(String name, String collaboratorID, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType.Type docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, List<Skill> skill, StatusType status){
        this.name=name;
        this.address=address;
        this.addressZipCode=addressZipCode;
        this.addressCity=addressCity;
        this.phoneNumber=phoneNumber;
        this.docType=docType;
        this.docIDNumber=docIDNumber;
        this.taxPayerNumber=taxPayerNumber;
        this.jobCategory=jobCategory;
        this.skills=skill;
        this.statusType=status;
        this.collaboratorID=collaboratorID;
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

    public String getCollaboratorID(){
        return this.collaboratorID;
    }

    public void setSkill(Skill skill){
        this.skill=skill;
    }

    public Skill getSkill(){
        return this.skill;
    }

    public String getID(){
        return this.collaboratorID;
    }



}
