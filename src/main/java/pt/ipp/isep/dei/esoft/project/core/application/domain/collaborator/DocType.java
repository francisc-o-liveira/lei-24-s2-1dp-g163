package pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator;

import java.io.Serializable;

/** Domain class for the type of identification document Object */
public class DocType implements Serializable {

    /** Enumerate type for the type of identification document
     *
     */
    public enum Type {CitizenCard, TaxPayerCard, Passport};

    /** Variable to represent the type of identification document
     *
     */
    private Type docType;

    /** Constructor method for the type of document identification
     *
     * @param docType - type of identification document
     */
    public DocType(Type docType){
        this.docType=docType;
    }

    /** Gets the types of identification document
     *
     * @return Array with the types of identification document
     */
    public Type[] getDocTypesValues() {
        return Type.values();
    }

    /** Method to verify if the number of identification has been introduced correctly
     * according to the type of identification document
     *
     * @return true if the number of identification has been filed correctly
     */

    public static boolean verifyDocType(Type docType , int numberID){
        boolean valueVerify = false;
        switch (docType) {
            case CitizenCard:
                if(numberID>99999999 && numberID<999999999){
                    valueVerify=true;
                }
            case TaxPayerCard:
                if(numberID>99999999 && numberID<999999999){
                    valueVerify=true;
                }
                //verify number of Serie of Passport don't verify the letters
            case Passport:
                if(numberID>99999 && numberID<999999){
                    valueVerify=true;
                }
        }
        return valueVerify;
    }
    /** Method to get the type of document in a String
     *
     * @return String with the type of document
     */
    @Override
    public String toString(){
        return String.format("%s", docType);
    }

}


