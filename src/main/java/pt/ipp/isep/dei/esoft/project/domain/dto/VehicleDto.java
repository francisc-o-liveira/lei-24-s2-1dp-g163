package pt.ipp.isep.dei.esoft.project.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class VehicleDto {
    /** The brand of the vehicle. */
    private String brand;

    /** The model of the vehicle. */
    private String model;
    private Vehicle.Type type;

    /** The license plate of the vehicle. */
    private String plate;

    /** The current kilometres travelled by the vehicle. */
    private double currentKm;

    /** The date when the vehicle was acquired. */
    private Date acquisitionDate;

    public VehicleDto(String brand, String model, Vehicle.Type type, String plate, double currentKm, Date acquisitionDate) {
        setBrand(brand);
        setModel(model);
        setType(type);
        setPlate(plate);
        setCurrentKm(currentKm);
        setAcquisitionDate(acquisitionDate);
    }

    public void setType(Vehicle.Type type) {
        this.type = type;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCurrentKm(double currentKm) {
        this.currentKm = currentKm;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public Vehicle.Type getType() {
        return type;
    }
    public String getPlate() {
        return plate;
    }
    public double getCurrentKm() {
        return currentKm;
    }
    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

}
