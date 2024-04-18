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

    public boolean verifyDocType(int numberID){
        boolean valueVerify = false;
        switch (this.docType) {
            case CitizenCard:
                if(numberID>0 && numberID<999999999){
                    valueVerify=true;
                }
            case BilheteIdentidade:
                if(numberID>0 && numberID<999999999){
                    valueVerify=true;
                }
            case Passport:
                if(numberID>0 && numberID<999999){
                    valueVerify=true;
                }
        }
        return valueVerify;
    }
}


