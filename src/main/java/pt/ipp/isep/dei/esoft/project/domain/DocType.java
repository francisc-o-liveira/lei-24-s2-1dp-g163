package pt.ipp.isep.dei.esoft.project.domain;

public class DocType {
    private enum Type {CitizenCard, BilheteIdentidade};
    private Type docType;

    public DocType(Type docType){
        this.docType=docType;
    }
}
