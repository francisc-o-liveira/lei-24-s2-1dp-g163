package pt.ipp.isep.dei.esoft.project.domain;

public class DocType {
    public enum Type {CitizenCard, BilheteIdentidade, Passport};
    private Type docType;

    public DocType(Type docType){
        this.docType=docType;
    }

    public boolean verifyDocType(int numberID){
        boolean valueVerify = false;
        switch (this.docType) {
            case CitizenCard:
                if(numberID){
                    valueVerify=true;
                }
            case BilheteIdentidade:
                if(numberID){
                    valueVerify=true;
                }
            case Passport:
                if(numberID){
                    valueVerify=true;
                }
        }
        return valueVerify;
    }

    public Type[] getDocTypesValues() {
        return Type.values();
    }
}


