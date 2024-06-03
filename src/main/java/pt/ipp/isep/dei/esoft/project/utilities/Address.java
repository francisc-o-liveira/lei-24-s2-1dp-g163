package pt.ipp.isep.dei.esoft.project.utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class Address, the address contains two attributes, which are zipCode and Street
 */
public class Address implements Serializable {

    private String zipCode;
    private String street;
    private String city;

    /**
     * Address constructor
     * <p>
     * @param zipCode  Zip code of the object
     * @param street Street of the object
     */
    public Address(String zipCode, String street,String city) throws IOException {
        setStreet(street);
        setZipCode(zipCode);
        setCity(city);
    }


    public void setCity(String city) {
        if(verifyIsOnlyCharacter(city)){
            this.city = city;
        }else {
            throw new IllegalArgumentException("INVALID CITY");
        }
    }

    public String getCity() {
        return city;
    }

    /**
     * Resposible for get and return the zipCode
     * <p>
     * @return The zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Responsible for set a new zipCode
     * <p>
     * @param zipCode The new zipCode
     */
    public void setZipCode(String zipCode) throws IOException {
        if(zipCode.matches("\\d{5}") && zipCode.split("-").length == 2){
        this.zipCode = zipCode;
        }else{
            throw new IllegalArgumentException("INVALID ZIPCODE");
        }

    }
    /**
     * Resposible for get and return the street
     * <p>
     * @return The street
     */
    public String getStreet() {
        return street;
    }
    /**
     * Responsible for set a new street
     * <p>
     * @param street The new street
     */
    public void setStreet(String street) {
        if (verifyIsOnlyCharacter(street)){
            this.street = street;
        }else {
            throw new IllegalArgumentException("INVALID STREET");
        }
    }



    /** Method to verify if the name only contains characters
     *
     * @param name of Collaborator
     * @return true if the name only contains characters
     */
    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method responsible for compare two Address objects
     * <p>
     * @param o the other object Address
     * <p>
     * @return the boolean representing the comaparation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return zipCode.equals(address.zipCode) && street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, street,city);
    }

    @Override
    public String toString() {
        return  "\n\t\t|->zipCode: " + zipCode +
                "\n\t\t|->street: " + street;
    }
}
