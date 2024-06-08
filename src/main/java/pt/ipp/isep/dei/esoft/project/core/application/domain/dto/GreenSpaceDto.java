package pt.ipp.isep.dei.esoft.project.core.application.domain.dto;

import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

/**
 * Represents a Data Transfer Object for a GreenSpace in the system.
 */
public class GreenSpaceDto {

    private double areaInHectares;
    private Address address;
    private String name;
    private GreenSpace.Type type;
    private String emailOfCreator;

    /**
     * Constructor for initializing a GreenSpaceDto with required parameters.
     *
     * @param areaInHectares the area of the green space in hectares
     * @param address        the address of the green space
     * @param name           the name of the green space
     * @param type           the type of the green space
     * @param emailOfCreator the email of the creator of the green space
     */
    public GreenSpaceDto(double areaInHectares, Address address, String name, GreenSpace.Type type, String emailOfCreator) {
        this.areaInHectares = areaInHectares;
        setAddress(address);
        this.name = name;
        this.type = type;
        setEmailOfCreator(emailOfCreator);
    }

    /**
     * Sets the email of the creator of the green space.
     *
     * @param emailOfCreator the email of the creator
     * @throws IllegalArgumentException if the email is null or empty
     */
    private void setEmailOfCreator(String emailOfCreator) {
        if (emailOfCreator != null && !emailOfCreator.isEmpty()) {
            this.emailOfCreator = emailOfCreator;
        } else {
            throw new IllegalArgumentException("Email of the creator is null or empty");
        }
    }

    /**
     * Gets the email of the creator of the green space.
     *
     * @return the email of the creator
     */
    public String createdBy() {
        return emailOfCreator;
    }

    /**
     * Gets the address of the green space.
     *
     * @return the address of the green space
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets the area of the green space in hectares.
     *
     * @return the area of the green space in hectares
     */
    public double getAreaInHectares() {
        return areaInHectares;
    }

    /**
     * Gets the name of the green space.
     *
     * @return the name of the green space
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the green space.
     *
     * @return the type of the green space
     */
    public GreenSpace.Type getType() {
        return type;
    }

    /**
     * Returns the name of the green space as its string representation.
     *
     * @return the name of the green space
     */
    public String toString() {
        return getName();
    }

    /**
     * Sets the name of the green space.
     *
     * @param name the new name of the green space
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the address of the green space.
     *
     * @param address the new address of the green space
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
