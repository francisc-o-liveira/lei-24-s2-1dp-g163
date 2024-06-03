package pt.ipp.isep.dei.esoft.project.domain.org;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.util.Objects;

public class GreenSpace {


    public enum Type{MediumSize,LargeSize,Garden}

    private double areaInHectares;
    private Address address;
    private String name;
    private Type type;
    private String email;


    public GreenSpace(double area,String name,Type type, String managerEmail, Address address, String email) {
        setArea(area);
        setAddress(address);
        setName(name);
        setType(type);
        setEmailOfCreator(managerEmail);
    }

    public GreenSpace(GreenSpaceDto greenSpaceDto) {
        setArea(greenSpaceDto.getAreaInHectares());
        setAddress(greenSpaceDto.getAddress());
        setName(greenSpaceDto.getName());
        setType(greenSpaceDto.getType());
        setEmailOfCreator(greenSpaceDto.createdBy());
    }

    private void setType(Type type) {
        this.type = type;
    }

    private void setArea(double area) {
        if(area <= 0){
            throw new IllegalArgumentException("Area cannot be less or equal to 0");
        }
        this.areaInHectares = area;
    }

    private void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }else {
            this.address = address;
        }
    }

    private boolean verifyStreet(String address) {
        char[] chars = address.toCharArray();;
        for(Character c : chars){
            if (!Character.isLetter(c) && Character.isDigit(c)){
                return false;
            }
        }
        return true;
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


    private void setName(String name) {
        if (verifyIsOnlyCharacter(name)){
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name Cannot have have numbers");
        }
    }

    public double getArea() {
        return areaInHectares;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name + "----" + this.areaInHectares + "-----" + this.address;
    }

    public static final Type[] getEnumGreenSpaceTypes(){
        return Type.values();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GreenSpaceDto)) {
            return false;
        }
        GreenSpaceDto gs = (GreenSpaceDto) obj;

        return Objects.equals(this.getName(), gs.getName()) &&
                Double.compare(this.getArea(), gs.getAreaInHectares()) == 0 &&
                Objects.equals(this.getAddress(), gs.getAddress()) &&
                Objects.equals(this.getType(), gs.getType());
    }

    public String createdBy() {
        return email;
    }

    private void setEmailOfCreator(String managerEmail) {
        if (managerEmail != null) {
            this.email = managerEmail;
        }
    }

}
