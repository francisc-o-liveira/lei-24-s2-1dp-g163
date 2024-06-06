package pt.ipp.isep.dei.esoft.project.domain.org;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a GreenSpace in the system.
 * Implements Serializable to support object serialization.
 */
public class GreenSpace implements Serializable {

    /**
     * Enum representing the type of GreenSpace.
     */
    public enum Type implements Serializable { MediumSize, LargeSize, Garden }

    private double areaInHectares;
    private Address address;
    private String name;
    private Type type;
    private String email;

    /**
     * Constructs a GreenSpace instance with the specified details.
     *
     * @param area         the area of the green space in hectares
     * @param name         the name of the green space
     * @param type         the type of the green space
     * @param managerEmail the email of the manager who created this green space
     * @param address      the address of the green space
     * @param email        the email of the creator
     * @throws IllegalArgumentException if the area is less than or equal to 0, or if the address is null
     */
    public GreenSpace(double area, String name, Type type, String managerEmail, Address address, String email) {
        setArea(area);
        setAddress(address);
        setName(name);
        setType(type);
        setEmailOfCreator(managerEmail);
    }

    /**
     * Constructs a GreenSpace instance from a GreenSpaceDto.
     *
     * @param greenSpaceDto the DTO containing the green space details
     */
    public GreenSpace(GreenSpaceDto greenSpaceDto) {
        setArea(greenSpaceDto.getAreaInHectares());
        setAddress(greenSpaceDto.getAddress());
        setName(greenSpaceDto.getName());
        setType(greenSpaceDto.getType());
        setEmailOfCreator(greenSpaceDto.createdBy());
    }

    /**
     * Sets the type of the green space.
     *
     * @param type the type to set
     */
    private void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets the area of the green space.
     *
     * @param area the area to set
     * @throws IllegalArgumentException if the area is less than or equal to 0
     */
    private void setArea(double area) {
        if (area <= 0) {
            throw new IllegalArgumentException("Area cannot be less than or equal to 0");
        }
        this.areaInHectares = area;
    }

    /**
     * Sets the address of the green space.
     *
     * @param address the address to set
     * @throws IllegalArgumentException if the address is null
     */
    private void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }

    /**
     * Verifies if the given address is valid.
     *
     * @param address the address to verify
     * @return true if the address is valid, false otherwise
     */
    private boolean verifyStreet(String address) {
        char[] chars = address.toCharArray();
        for (Character c : chars) {
            if (!Character.isLetter(c) && Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if the given name contains only characters.
     *
     * @param name the name to verify
     * @return true if the name contains only characters, false otherwise
     */
    private boolean verifyIsOnlyCharacter(String name) {
        char[] chars = name.replaceAll("\\s", "").toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the name of the green space.
     *
     * @param name the name to set
     * @throws IllegalArgumentException if the name contains numbers
     */
    private void setName(String name) {
        if (verifyIsOnlyCharacter(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot contain numbers");
        }
    }

    /**
     * Gets the area of the green space.
     *
     * @return the area in hectares
     */
    public double getArea() {
        return areaInHectares;
    }

    /**
     * Gets the address of the green space.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets the name of the green space.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the green space.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns a string representation of the green space.
     *
     * @return a string representation of the green space
     */
    @Override
    public String toString() {
        return this.name + "----" + this.areaInHectares + "-----" + this.address;
    }

    /**
     * Retrieves all possible types of green spaces.
     *
     * @return an array of Type enums
     */
    public static Type[] getEnumGreenSpaceTypes() {
        return Type.values();
    }

    /**
     * Checks if this green space is equal to another object.
     * Green spaces are considered equal if they have the same name, area, address, and type.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
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

    /**
     * Gets the email of the creator of this green space.
     *
     * @return the email of the creator
     */
    public String createdBy() {
        return email;
    }

    /**
     * Sets the email of the creator of this green space.
     *
     * @param managerEmail the manager's email to set
     */
    private void setEmailOfCreator(String managerEmail) {
        if (managerEmail != null) {
            this.email = managerEmail;
        }
    }
}
