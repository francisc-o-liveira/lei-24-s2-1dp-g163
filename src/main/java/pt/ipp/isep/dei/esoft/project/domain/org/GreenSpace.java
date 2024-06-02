package pt.ipp.isep.dei.esoft.project.domain.org;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.Objects;

public class GreenSpace {


    public enum Type{MediumSize,LargeSize,Garden}

    private double areaInHectares;
    private String addressStreet;
    private String addressCity;
    private String addressZipCode;
    private String name;
    private Type type;
    private String email;


    public GreenSpace(double area,String name,Type type, String managerEmail, String addressStreet, String addressCity, String addressZipCode, String email) {
        setArea(area);
        setAddress(addressStreet,addressCity,addressZipCode);
        setName(name);
        setType(type);
        setEmailOfCreator(managerEmail);
    }

    public GreenSpace(GreenSpaceDto greenSpaceDto) {
        setArea(greenSpaceDto.getAreaInHectares());
        setAddress(greenSpaceDto.getAddressStreet(), greenSpaceDto.getAddressCity(), greenSpaceDto.getAddressZipCode());
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

    private void setAddress(String address, String addressCity, String addressZipCode) {
        if (verifyStreet(address)) {
            this.addressStreet = address;
        }
        if(verifyIsOnlyCharacter(addressCity)){
            this.addressCity = addressCity;
        }
        if ((addressZipCode.split("-").length==2) && addressZipCode.split("").length==8) {
            this.addressZipCode = addressZipCode;
        }else {
            throw new IllegalArgumentException("Invalid address");
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
        }
  throw new IllegalArgumentException("Name Cannot have have numbers");
    }

    public double getArea() {
        return areaInHectares;
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

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name + "----" + this.areaInHectares + "-----" + this.addressCity + "----" + this.addressZipCode + "----" + this.addressStreet;
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
                Objects.equals(this.getAddressStreet(), gs.getAddressStreet()) &&
                Objects.equals(this.getAddressCity(), gs.getAddressCity()) &&
                Objects.equals(this.getAddressZipCode(), gs.getAddressZipCode()) &&
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
