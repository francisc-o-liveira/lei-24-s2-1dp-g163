package pt.ipp.isep.dei.esoft.project.domain;

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
    public enum StatusType {Active,NotActive};
    private StatusType statusType;

    public Collaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, int phoneNumber, DocType.Type docType, int docIDNumber, int taxPayerNumber, JobCategory jobCategory, Skill skill, StatusType status){
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
    }

    public StatusType[] getStatusList(){
        return StatusType.values();
    }

    public StatusType getStatus(){
        return this.statusType;
    }



}
