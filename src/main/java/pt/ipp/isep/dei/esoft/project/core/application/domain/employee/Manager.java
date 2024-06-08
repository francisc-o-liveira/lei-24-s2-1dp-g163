package pt.ipp.isep.dei.esoft.project.core.application.domain.employee;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Manager in the system.
 * Implements Serializable to support object serialization.
 */
public class Manager implements Serializable {

    private String email;
    private String name;
    private String position;
    public static enum Role { HRM, GSM, VFM }
    private String phone;

    /**
     * Retrieves all possible roles for a manager.
     *
     * @return An array of Role enums.
     */
    public static Role[] getEnumManagerRoles() {
        return Role.values();
    }

    /**
     * Constructs a Manager instance with the specified name, position, phone, and email.
     *
     * @param name     the name of the manager
     * @param position the position of the manager
     * @param phone    the phone number of the manager
     * @param email    the email of the manager
     * @throws IllegalArgumentException if the name, phone, or email are invalid
     */
    public Manager(String name, String position, String phone, String email) {
        setName(name);
        setPosition(position);
        setPhone(phone);
        setEmail(email);
    }

    /**
     * Sets the name of the manager.
     *
     * @param name the name to set
     * @throws IllegalArgumentException if the name contains more than 6 words
     */
    public void setName(String name) {
        if (name.split(" ").length <= 6) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name must contain a maximum of 6 words");
        }
    }

    /**
     * Sets the phone number of the manager.
     *
     * @param phone the phone number to set
     * @throws IllegalArgumentException if the phone number is invalid
     */
    public void setPhone(String phone) {
        if (verifyPhone(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Phone number is invalid. Please try again.");
        }
    }

    /**
     * Verifies if the given phone number is valid.
     *
     * @param phone the phone number to verify
     * @return true if the phone number is valid, false otherwise
     */
    private boolean verifyPhone(String phone) {
        String verify = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(verify);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * Sets the position of the manager.
     *
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Sets the email of the manager.
     *
     * @param email the email to set
     * @throws IllegalArgumentException if the email is invalid
     */
    public void setEmail(String email) {
        if (verifyCollaboratorEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    /**
     * Verifies if the given email is valid.
     *
     * @param email the email to verify
     * @return true if the email is valid, false otherwise
     */
    private boolean verifyCollaboratorEmail(String email) {
        String[] emailDiv = email.split("@");
        return emailDiv.length == 2 && emailDiv[1].split("\\.").length == 2;
    }

    /**
     * Gets the email of the manager.
     *
     * @return the email of the manager
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the name of the manager.
     *
     * @return the name of the manager
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the position of the manager.
     *
     * @return the position of the manager
     */
    public String getPosition() {
        return position;
    }

    /**
     * Checks if this manager is equal to another object.
     * Managers are considered equal if they have the same email.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        Manager manager = (Manager) o;
        return email.equals(manager.email);
    }

    /**
     * Computes the hash code for this manager.
     *
     * @return the hash code of this manager
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Checks if this manager has the given email.
     *
     * @param email the email to check
     * @return true if this manager has the given email, false otherwise
     */
    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }

    /**
     * Creates and returns a clone of this manager.
     *
     * @return a clone of this manager
     */
    @Override
    public Manager clone() {
        return new Manager(this.name, this.position, this.phone, this.email);
    }

    /**
     * Returns a string representation of the manager.
     *
     * @return a string representation of the manager
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s", name, email, position, phone);
    }
}
