package pt.ipp.isep.dei.esoft.project.domain.collaborator;

public class DocType {
    public enum Type {CitizenCard, TaxPayerCard, Passport};
    private Type docType;

    public DocType(Type docType){
        this.docType=docType;
    }

    public Type[] getDocTypesValues() {
        return Type.values();
    }

    public static boolean verifyDocType(Type docType , int numberID){
        boolean valueVerify = false;
        switch (docType) {
            case CitizenCard:
                if(numberID>0 && numberID<999999999){
                    valueVerify=true;
                }
            case TaxPayerCard:
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

    @Override
    public String toString(){
        return String.format("%s", docType);
    }

}


