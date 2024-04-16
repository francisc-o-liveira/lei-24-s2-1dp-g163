package pt.ipp.isep.dei.esoft.project.domain;

public class Collaborator {
    private String name;
    //private Data birthday;
    //private Data admissionDate
    private String address;
    private String addressZipCode;
    private int phoneNumber;
    private DocType docType;
    private String docIDNumber;
    private JobCategory jobCategory;

    public Collaborator(String name, /*Data birthday, Data admissionDate*/ String address, String addressZipCode, int phoneNumber, DocType docType, String docIDNumber, JobCategory jobCategory){
        this.name=name;
        this.address=address;
        this.addressZipCode=addressZipCode;
        this.phoneNumber=phoneNumber;
        this.docType=docType;
        this.docIDNumber=docIDNumber;
        this.jobCategory=jobCategory;
    }

}
