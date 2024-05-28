package pt.ipp.isep.dei.esoft.project.domain.org;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.Objects;

public class GreenSpace {


    public String createdBy() {
        return email;
    }

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
        setEmailOfCreater(managerEmail);
    }

    private void setEmailOfCreater(String managerEmail) {
        if (managerEmail != null) {
            this.email = managerEmail;
        }
    }

    public GreenSpace(GreenSpaceDto greenSpaceDto) {
        setArea(greenSpaceDto.getAreaInHectares());
        setAddress(greenSpaceDto.getAddressStreet(), greenSpaceDto.getAddressCity(), greenSpaceDto.getAddressZipCode());
        setName(greenSpaceDto.getName());
        setType(greenSpaceDto.getType());
        setEmailOfCreater(greenSpaceDto.createdBy());
    }

    private void setType(Type type) {
        this.type = type;
    }

    private void setArea(double area) {
        this.areaInHectares = area;
    }

    private void setAddress(String address, String addressCity, String addressZipCode) {
        this.addressStreet = address;
        this.addressCity = addressCity;
        if ((addressZipCode.split("-").length==2) && addressStreet.split(" ").length<10 && addressCity.split(" ").length<6 && addressZipCode.split("").length==8) {
            this.addressZipCode = addressZipCode;
        }else {
            throw new IllegalArgumentException("Invalid address");
        }
    }

    private void setName(String name) {
        this.name = name;
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

}
