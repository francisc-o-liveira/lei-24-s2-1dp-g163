package pt.ipp.isep.dei.esoft.project.domain;

public class DocType {
    public enum Type {CitizenCard, BilheteIdentidade, Passport};
    private Type docType;

    public DocType(Type docType){
        this.docType=docType;
    }

    public Type[] getDocTypesValues() {
        return Type.values();
    }
}


