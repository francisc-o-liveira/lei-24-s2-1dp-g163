package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

public class GreenSpaceDto {

    private double areaInHectares;
    private Address address;
    private String name;
    private GreenSpace.Type type;
    private String emailOfCreator;

    public GreenSpaceDto(double areaInHectares, Address address, String name, GreenSpace.Type type, String emailOfCreator) {
        this.areaInHectares = areaInHectares;
        setAddress(address);
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

    public Address getAddress() {
        return address;
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

    public void setAddress(Address address) {
        this.address = address;
    }

}
