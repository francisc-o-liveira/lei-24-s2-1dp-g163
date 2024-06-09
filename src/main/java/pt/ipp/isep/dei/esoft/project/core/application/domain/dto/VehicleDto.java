package pt.ipp.isep.dei.esoft.project.core.application.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

/**
 * Represents a Data Transfer Object for a Vehicle in the system.
 */
public class VehicleDto {

    /** The brand of the vehicle. */
    private String brand;

    /** The model of the vehicle. */
    private String model;

    /** The type of the vehicle. */
    private Vehicle.Type type;

    /** The license plate of the vehicle. */
    private String plate;

    /** The current kilometres travelled by the vehicle. */
    private double currentKm;

    /** The date when the vehicle was acquired. */
    private Date acquisitionDate;

    /**
     * Constructor for initializing a VehicleDto with required parameters.
     *
     * @param brand           the brand of the vehicle
     * @param model           the model of the vehicle
     * @param type            the type of the vehicle
     * @param plate           the license plate of the vehicle
     * @param currentKm       the current kilometres travelled by the vehicle
     * @param acquisitionDate the date when the vehicle was acquired
     */
    public VehicleDto(String brand, String model, Vehicle.Type type, String plate, double currentKm, Date acquisitionDate) {
        setBrand(brand);
        setModel(model);
        setType(type);
        setPlate(plate);
        setCurrentKm(currentKm);
        setAcquisitionDate(acquisitionDate);
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type the type of the vehicle
     */
    public void setType(Vehicle.Type type) {
        this.type = type;
    }

    /**
     * Sets the license plate of the vehicle.
     *
     * @param plate the license plate of the vehicle
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Sets the model of the vehicle.
     *
     * @param model the model of the vehicle
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets the current kilometres travelled by the vehicle.
     *
     * @param currentKm the current kilometres travelled by the vehicle
     */
    public void setCurrentKm(double currentKm) {
        this.currentKm = currentKm;
    }

    /**
     * Sets the acquisition date of the vehicle.
     *
     * @param acquisitionDate the acquisition date of the vehicle
     */
    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Sets the brand of the vehicle.
     *
     * @param brand the brand of the vehicle
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the brand of the vehicle.
     *
     * @return the brand of the vehicle
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the model of the vehicle.
     *
     * @return the model of the vehicle
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the type of the vehicle.
     *
     * @return the type of the vehicle
     */
    public Vehicle.Type getType() {
        return type;
    }

    /**
     * Gets the license plate of the vehicle.
     *
     * @return the license plate of the vehicle
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Gets the current kilometres travelled by the vehicle.
     *
     * @return the current kilometres travelled by the vehicle
     */
    public double getCurrentKm() {
        return currentKm;
    }

    /**
     * Gets the acquisition date of the vehicle.
     *
     * @return the acquisition date of the vehicle
     */
    public Date getAcquisitionDate() {
        return acquisitionDate;
    }
}
