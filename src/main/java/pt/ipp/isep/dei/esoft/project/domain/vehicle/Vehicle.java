package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
/**
 * Represents a vehicle with various properties and functionalities.
 */
public class Vehicle {
        /** The brand of the vehicle. */
        private String brand;

        /** The model of the vehicle. */
        private String model;

        /** The type of the vehicle. */
        public enum Type {
            LightPassenger, // Light Passenger
            LightCargo,       // Light Cargo
            Heavy,             // Heavy
            Motorcycle,  // Motorcycle
            Tractor //Tractor
        }
        private Type type;

    public int getTare() {
        return tare;
    }

    /** The tare weight of the vehicle. */
        private int tare;

        /** The license plate of the vehicle. */
        private String plate;

        /** The gross weight of the vehicle. */
        private double grossWeight;

        /** The current kilometres travelled by the vehicle. */
        private double currentKm;

        /** The date when the vehicle was registered. */
        private Date registerDate;

        /** The date when the vehicle was acquired. */
        private Date acquisitionDate;

        /** The frequency of checks required for the vehicle (in kilometres). */
        private double frequencyCheckKm;

        /** The list of check-ups performed on the vehicle. */
        private List<CheckUp> checkUpList;

        /** The status of the vehicle. */
        public enum StatusType {
            Use,    // In use
            NotUse  // Not in use
        }
        private StatusType statusType;

        /** The tax rate for a close check. */
        private static final double TAX_FOR_CLOSE_CHECK = 0.05;

        /**
         * Constructs a new Vehicle object with the specified parameters.
         * @param brand the brand of the vehicle
         * @param model the model of the vehicle
         * @param type the type of the vehicle
         * @param tare the tare weight of the vehicle
         * @param grossWeight the gross weight of the vehicle
         * @param currentKm the current kilometres travelled by the vehicle
         * @param registerDate the date when the vehicle was registered
         * @param acquisitionDate the date when the vehicle was acquired
         * @param frequencyCheckKm the frequency of checks required for the vehicle (in kilometres)
         * @param plate the license plate of the vehicle
         * @param date the date of the last check-up
         * @param lastKmCheck the kilometres travelled since the last check-up
         */
        public Vehicle(String brand, String model, Type type, int tare, double grossWeight, double currentKm,
                       Date registerDate, Date acquisitionDate, double frequencyCheckKm, String plate, Date date,
                       double lastKmCheck) {
            setBrand(brand);
            setModel(model);
            setType(type);
            setTare(tare);
            setGrossWeight(grossWeight);
            setCurrentKm(currentKm);
            setRegisterDate(registerDate);
            setAcquisitionDate(acquisitionDate);
            setFrequencyCheckKm(frequencyCheckKm);
            setStatusType(StatusType.NotUse);
            setPlate(plate);
            setLastCheckUp(date, lastKmCheck);
        }



    /**
     * Sets the last check-up for the vehicle.
     * @param date the date of the last check-up
     * @param lastKmCheck the kilometres travelled since the last check-up
     * @throws IllegalArgumentException if the last check-up is incorrect or not within the required parameters
     */
    private void setLastCheckUp(Date date, double lastKmCheck) {
        checkUpList = new ArrayList<>();
        if (currentKm < 10000 && lastKmCheck == 0 && date.compareTo(registerDate) == 0) {
            CheckUp defaultCheck = new CheckUp(lastKmCheck, date);
            checkUpList.add(defaultCheck);
        } else if (currentKm < 10000) {
            throw new IllegalArgumentException("The last check-up needs to be default: Fatal Error UI");
        } else {
            if (lastKmCheck < currentKm && lastKmCheck > 0 && date.compareTo(registerDate) > 0) {
                CheckUp lastCheck = new CheckUp(lastKmCheck, date);
                checkUpList.add(lastCheck);
            } else {
                throw new IllegalArgumentException("The last check-up is incorrect to register the vehicle");
            }
        }
    }

    /**
     * Verifies the validity of the provided license plate.
     * @param plate the license plate to verify
     * @return true if the plate is valid, otherwise false
     */
    private boolean verifyPlate(String plate) {
        if(registerDate.getYear()>1936 && registerDate.getYear() < 1993){
            return plate.matches("^[A-Z]{2}-\\d{2}-\\d{2}$");
        } else if (registerDate.getYear()>1992 && registerDate.getYear()<2005) {
            return plate.matches("^\\d{2}-\\d{2}-[A-Z]{2}$");
        } else if (registerDate.getYear()>2004 && registerDate.getYear()<2020) {
            return plate.matches("^\\d{2}-[A-Z]{2}-\\d{2}$");
        }else {
            return plate.matches("^[A-Z]{2}-\\d{2}-[A-Z]{2}$");
        }
    }

    /**
     * Retrieves the status of the vehicle.
     * @return the status of the vehicle
     */
    public StatusType getStatus() {
        return statusType;
    }

    /**
     * Checks if the vehicle is close to a maintenance check.
     * @return true if the vehicle is close to a maintenance check, otherwise false
     */
    public boolean isCloseToCheck() {
        return (frequencyCheckKm * TAX_FOR_CLOSE_CHECK) >= getKmCloseToCheck();
    }

    /**
     * Calculates the remaining kilometres until the next maintenance check.
     * @return the remaining kilometres until the next maintenance check
     */
    public double getKmCloseToCheck() {
        return (this.getLastCheckUpKm() + frequencyCheckKm) - currentKm;
    }

    /**
     * Registers a new check-up for the vehicle.
     * @param currentKmOfCheck the current kilometres travelled at the time of the check-up
     * @param dateOfCheck the date of the check-up
     * @param maintenanceKm the kilometres interval for maintenance checks
     * @return an optional containing the registered check-up, or empty if registration fails
     * @throws RejectedExecutionException if the check-up could not be registered due to invalid data
     */
    public Optional<CheckUp> registerCheckUp(double currentKmOfCheck, Date dateOfCheck, double maintenanceKm) {
        Optional<CheckUp> newCheck = Optional.empty();
        boolean operationSuccess = false;
        if (maintenanceKm == 0) {
            operationSuccess = true;
        } else {
            this.frequencyCheckKm = maintenanceKm;
        }
        if (currentKmOfCheck >= currentKm && currentKmOfCheck > getLastCheckUpKm() && dateOfCheck.diference(Date.atualDate()) < 30) {
            CheckUp regist = new CheckUp(currentKmOfCheck, dateOfCheck);
            newCheck = Optional.of(regist);
            operationSuccess = this.checkUpList.add(regist);
        }
        if (!operationSuccess) {
            throw new RejectedExecutionException("The check-up could not be registered due to invalid data (current Kilometers of check-up and date of check-up)");
        }
        return newCheck;
    }

    /**
     * Retrieves the kilometres travelled at the time of the last check-up.
     * @return the kilometres travelled at the time of the last check-up
     */
    private double getLastCheckUpKm() {
        double save = 0;
        for (CheckUp c : checkUpList) {
            save = c.getKmOfCheck();
        }
        return save;
    }

    /**
     * Retrieves the list of check-ups performed on the vehicle.
     * @return the list of check-ups performed on the vehicle
     */
    public List<CheckUp> getCheckUpList() {
        return checkUpList;
    }

    /**
     * Retrieves the status type of the vehicle.
     * @return the status type of the vehicle
     */
    public StatusType getStatusType() {
        return statusType;
    }

    /**
     * Retrieves the acquisition date of the vehicle.
     * @return the acquisition date of the vehicle
     */
    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Retrieves the registration date of the vehicle.
     * @return the registration date of the vehicle
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * Retrieves the gross weight of the vehicle.
     * @return the gross weight of the vehicle
     */
    public double getGrossWeight() {
        return grossWeight;
    }

    /**
     * Retrieves the current kilometres travelled by the vehicle.
     * @return the current kilometres travelled by the vehicle
     */
    public double getCurrentKm() {
        return currentKm;
    }

    /**
     * Retrieves the frequency of checks required for the vehicle (in kilometres).
     * @return the frequency of checks required for the vehicle
     */
    public double getFrequencyCheckKm() {
        return frequencyCheckKm;
    }

    /**
     * Retrieves the brand of the vehicle.
     * @return the brand of the vehicle
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Retrieves the model of the vehicle.
     * @return the model of the vehicle
     */
    public String getModel() {
        return model;
    }

    /**
     * Retrieves the license plate of the vehicle.
     * @return the license plate of the vehicle
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Retrieves the type of the vehicle.
     * @return the type of the vehicle
     */
    public Type getType() {
        return type;
    }


    /**
     * Compares this vehicle to the specified object for equality.
     * @param obj the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        return this.plate.equals(((Vehicle) obj).plate);
    }

    /**
     * Returns a string representation of the vehicle.
     * @return a string representation of the vehicle
     */
    @Override
    public String toString() {
        return String.format("Vehicle:\n" +
                        "Brand: %s\n" +
                        "Model: %s\n" +
                        "Type: %s\n" +
                        "Current Km: %.2f\n" +
                        "Frequency Check Km: %.2f\n",
                brand, model, type, currentKm,  frequencyCheckKm);
    }

    /**
     * Sets the brand of the vehicle.
     * @param brand the brand to set
     * @throws IllegalArgumentException if the brand is invalid
     */
    public void setBrand(String brand) {
        if (verifyModelAndBrand(brand)) {
            this.brand = brand;
        } else {
            throw new IllegalArgumentException("Invalid brand");
        }
    }

    /**
     * Sets the current kilometres travelled by the vehicle.
     * @param currentKm the current kilometres to set
     * @throws IllegalArgumentException if the current kilometres is invalid
     */
    public void setCurrentKm(double currentKm) {
        if (currentKm > 0 && currentKm > this.currentKm) {
            this.currentKm = currentKm;
        } else {
            throw new IllegalArgumentException("Invalid current km");
        }
    }

    /**
     * Sets the model of the vehicle.
     * @param model the model to set
     * @throws IllegalArgumentException if the model is invalid
     */
    public void setModel(String model) {
        if (verifyModelAndBrand(model)) {
            this.model = model;
        } else {
            throw new IllegalArgumentException("Invalid model name");
        }
    }

    /**
     * Verifies the validity of the model and brand name.
     * @param name the name to verify
     * @return true if the name is valid, otherwise false
     */
    private boolean verifyModelAndBrand(String name) {
        return name.split(" ").length <= 3;
    }

    /**
     * Sets the license plate of the vehicle.
     * @param plate the license plate to set
     * @throws RejectedExecutionException if the plate does not correspond with the acquisition date
     */
    public void setPlate(String plate) {
        if (verifyPlate(plate)) {
            this.plate = plate;
        } else {
            throw new RejectedExecutionException("Plate does not correspond with the Acquisition Date");
        }
    }

    /**
     * Sets the frequency of checks required for the vehicle (in kilometres).
     * @param frequencyCheckKm the frequency of checks to set
     * @throws IllegalArgumentException if the frequency of checks is invalid
     */
    public void setFrequencyCheckKm(double frequencyCheckKm) {
        if (frequencyCheckKm >= 1000) {
            this.frequencyCheckKm = frequencyCheckKm;
        } else {
            throw new IllegalArgumentException("Frequency Check Km should be greater than 1000");
        }
    }

    /**
     * Sets the tare weight of the vehicle.
     * @param tare the tare weight to set
     */
    public void setTare(int tare) {
        this.tare = tare;
    }

    /**
     * Sets the registration date of the vehicle.
     * @param registerDate the registration date to set
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * Sets the acquisition date of the vehicle.
     * @param acquisitionDate the acquisition date to set
     * @throws IllegalArgumentException if the acquisition date is invalid
     */
    public void setAcquisitionDate(Date acquisitionDate) {
        if (acquisitionDate.getYear() > 1900) {
            this.acquisitionDate = acquisitionDate;
        } else {
            throw new IllegalArgumentException("Acquisition date cannot be less than 1900");
        }
    }

    /**
     * Sets the gross weight of the vehicle.
     * @param grossWeight the gross weight to set
     * @throws IllegalArgumentException if the gross weight is invalid
     */
    public void setGrossWeight(double grossWeight) {
        if (grossWeight > tare) {
            this.grossWeight = grossWeight;
        } else {
            throw new IllegalArgumentException("Gross Weight cannot be less than tare");
        }
    }

    /**
     * Sets the status type of the vehicle.
     * @param statusType the status type to set
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * Sets the type of the vehicle.
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }


    /** Converts a date to LocalDate
     *
     * @param date to be converted
     * @return date in the LocalDate format
     */
    public static LocalDate convertToJavaLocalDate(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        return LocalDate.of(year, month, day);
    }

    /** Gets the registerDate in the LocalDate format
     *
     * @return registerDate in LocalDate format
     */
    public LocalDate getRegisterDateLocal(){
        return convertToJavaLocalDate(registerDate);
    }

    /** Gets the acquisition date in the LocalDate format
     *
     * @return acquisition date in LocalDate format
     */

    public LocalDate getAcquisitionDateLocal(){
        return convertToJavaLocalDate(acquisitionDate);
    }

}
