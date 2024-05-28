package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;

public class GreenSpaceDto {

    private double areaInHectares;
    private String addressStreet;
    private String addressCity;
    private String addressZipCode;
    private String name;
    private GreenSpace.Type type;
    private String emailOfCreator;

    public GreenSpaceDto(double areaInHectares, String address,String addressCity,String addressZipCode, String name, GreenSpace.Type type, String emailOfCreator) {
        this.areaInHectares = areaInHectares;
        setAddress(address,addressCity,addressZipCode);
        this.name = name;
        this.type = type;
        setEmailOfCreator(emailOfCreator);
    }

    private void setEmailOfCreator(String emailOfCreator) {
        if (emailOfCreator != null && !emailOfCreator.isEmpty()) {
            this.emailOfCreator = emailOfCreator;
        }else {
            throw new IllegalArgumentException("Email of the creator is null or empty");
        }
    }

    public String createdBy() {
        return emailOfCreator;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }
    public String getAddressZipCode() {
        return addressZipCode;
    }

    public double getAreaInHectares() {
        return areaInHectares;
    }


    public String getName() {
        return name;
    }

    public GreenSpace.Type getType() {
        return type;
    }

    public String toString(){
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String addressStreet, String addressCity, String addressZipCode) {
        this.addressStreet = addressStreet;
        this.addressCity=addressCity;
        this.addressZipCode=addressZipCode;
    }

}
