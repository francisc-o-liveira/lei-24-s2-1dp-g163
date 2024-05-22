package pt.ipp.isep.dei.esoft.project.domain.org;

public class GreenSpace {

    private double area;
    private String address;
    private String name;

    private enum Type{MediumSize,LargeSize,Garden}
    private Type type;


    public GreenSpace(double area, String address,String name,Type type) {
        setArea(area);
        setAddress(address);
        setName(name);
        setType(type);
    }

    private void setType(Type type) {
        this.type = type;
    }

    private void setArea(double area) {
        this.area = area;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
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
        return this.name + "----" + this.area + "-----" + this.address;
    }
}
