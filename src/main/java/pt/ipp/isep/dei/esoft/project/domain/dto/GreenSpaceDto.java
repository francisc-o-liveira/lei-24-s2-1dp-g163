package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;

public class GreenSpaceDto {

    private double areaInHectares;
    private String address;
    private String name;

    private GreenSpace.Type type;

    public GreenSpaceDto(double areaInHectares, String address, String name, GreenSpace.Type type) {
        this.areaInHectares = areaInHectares;
        this.address = address;
        this.name = name;
        this.type = type;

    }

    public double getAreaInHectares() {
        return areaInHectares;
    }

    public String getAddress() {
        return address;
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
}
