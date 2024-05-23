package pt.ipp.isep.dei.esoft.project.domain.org;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

public class GreenSpace {


    public enum  Type{MediumSize,LargeSize,Garden}

    private double areaInHectares;
    private String address;
    private String name;

    private Type type;


    public GreenSpace(double area, String address,String name,Type type) {
        setArea(area);
        setAddress(address);
        setName(name);
        setType(type);
    }

    public GreenSpace(GreenSpaceDto greenSpaceDto) {
        setArea(greenSpaceDto.getAreaInHectares());
        setAddress(greenSpaceDto.getAddress());
        setName(greenSpaceDto.getName());
        setType(greenSpaceDto.getType());
    }

    private void setType(Type type) {
        this.type = type;
    }

    private void setArea(double area) {
        this.areaInHectares = area;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return areaInHectares;
    }
    public String getAddress() {
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
}
