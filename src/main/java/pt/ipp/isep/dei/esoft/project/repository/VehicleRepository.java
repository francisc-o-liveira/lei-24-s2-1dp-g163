package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle.StatusType.Use;
import static pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle.StatusType.NotUse;

/**
 * The VehicleRepository class manages vehicle data and operations.
 * It handles vehicle registration, activation, deactivation, check-ups, and retrieval.
 */
public class VehicleRepository {
    private List<Vehicle> vehicleList;


    public Vehicle searchForVehicleByPlate(String plate){
        for (Vehicle vehicle : vehicleList) {
            if(vehicle.getPlate().equals(plate)){
                return vehicle;
            }
        }
        throw new RuntimeException("No vehicle found with plate " + plate);
    }

    /**
     * Retrieves a list of vehicles that are not currently active.
     *
     * @return A list of inactive vehicles.
     */

    public List<Vehicle> getVehicleNotActive() {
        List<Vehicle> vehicleNotActive = new ArrayList<>();
        for (Vehicle c : vehicleList) {
            if (c.getStatus() == NotUse) {
                vehicleNotActive.add(c);
            }
        }
        return vehicleNotActive;
    }

    /**
     * Activates a specified vehicle.
     *
     * @param vehicle The vehicle to activate.
     */
    public void activateVehicle(Vehicle vehicle) {
        if (vehicle.getStatus() == NotUse) {
            vehicle.setStatusType(Use);
        }
    }

    /**
     * Deactivates a specified vehicle.
     *
     * @param vehicle The vehicle to deactivate.
     */
    public void deactivateVehicle(Vehicle vehicle) {
        if (vehicle.getStatus() == Use) {
            vehicle.setStatusType(NotUse);
        }
    }

    /**
     * Retrieves a list of vehicles in need of check-up.
     *
     * @return A list of vehicles needing check-up.
     */
    public List<Vehicle> getVehicleListPerCheckUp() {
        ArrayList<Vehicle> vehiclesNeedCheckUp = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.isCloseToCheck()) {
                vehiclesNeedCheckUp.add(v);
            }
        }
        return vehiclesNeedCheckUp;
    }

    /**
     * Retrieves a list of vehicles based on their status type.
     *
     * @param statusType The status type of the vehicles to retrieve.
     * @return A list of vehicles with the specified status type.
     */
    public List<Vehicle> getVehicleListPerType(Vehicle.StatusType statusType) {
        ArrayList<Vehicle> vehiclesPerType = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.getStatus() == statusType) {
                vehiclesPerType.add(v);
            }
        }
        return vehiclesPerType;
    }

    /**
     * Sorts the list of vehicles based on their proximity to the next check-up.
     *
     * @param vehiclesCloseToCheck The list of vehicles close to check-up.
     */
    public void sortVehiclesPerCloseToCheckUp(List<Vehicle> vehiclesCloseToCheck) {
        Vehicle temp;
        for (int i = 0; i < vehiclesCloseToCheck.size() - 1; i++) {
            for (int j = i + 1; j < vehiclesCloseToCheck.size(); j++) {
                if (vehiclesCloseToCheck.get(i).getKmCloseToCheck() < vehiclesCloseToCheck.get(j).getKmCloseToCheck()) {
                    temp = vehiclesCloseToCheck.get(i);
                    vehiclesCloseToCheck.set(i, vehiclesCloseToCheck.get(j));
                    vehiclesCloseToCheck.set(j, temp);
                }
            }
        }
    }

    /**
     * Adds a new vehicle to the repository.
     *
     * @param vehicle The vehicle to add.
     * @return An Optional containing the added vehicle if successful, otherwise empty.
     * @throws CloneNotSupportedException if there is an issue cloning the vehicle.
     */
    public Optional<Vehicle> addVehicle(Vehicle vehicle) throws CloneNotSupportedException {
        Optional<Vehicle> newVehicle = Optional.empty();
        newVehicle = Optional.of(vehicle);
        boolean operationSuccess = false;
        if (isValidVehicle(vehicle)) {
            operationSuccess = saveVehicle(vehicle);
        }
        if (!operationSuccess) {
            throw new CloneNotSupportedException("Vehicle already exists in the system");
        }
        return newVehicle;
    }

    private boolean saveVehicle(Vehicle vehicle) {
        return vehicleList.add(vehicle);
    }

    private boolean isValidVehicle(Vehicle vehicle) {
        return !vehicleList.contains(vehicle);
    }

    /**
     * Retrieves an array of vehicle types.
     *
     * @return An array of Vehicle.Type representing all vehicle types.
     */
    public Vehicle.Type[] getVehicleTypeList() {
        return Vehicle.Type.values();
    }

    /**
     * Registers a new vehicle with the provided details.
     *
     * @param brand            The brand of the vehicle.
     * @param model            The model of the vehicle.
     * @param acquisitionDate  The date of vehicle acquisition.
     * @param registerDate     The date of vehicle registration.
     * @param currentKM        The current kilometers of the vehicle.
     * @param checkupFrequency The frequency of vehicle checkups.
     * @param grossWeight      The gross weight of the vehicle.
     * @param tare             The tare of the vehicle.
     * @param plate            The registration plate number of the vehicle.
     * @param type             The type of the vehicle.
     * @param lastCheck        The date of the last vehicle check-up.
     * @param lastCheckUpKm    The kilometers at the last vehicle check-up.
     * @return An Optional containing the registered vehicle if successful, otherwise empty.
     * @throws CloneNotSupportedException if there is an issue cloning the vehicle.
     */
    public Optional<Vehicle> registerVehicle(String brand, String model, Date acquisitionDate, Date registerDate, double currentKM, double checkupFrequency, double grossWeight, int tare, String plate, Vehicle.Type type, Date lastCheck, double lastCheckUpKm) throws CloneNotSupportedException {
        Optional<Vehicle> newVehicle = Optional.empty();
        Vehicle vehicle = new Vehicle(brand, model, type, tare, grossWeight, currentKM, registerDate, acquisitionDate, checkupFrequency, plate, lastCheck, lastCheckUpKm);
        newVehicle = addVehicle(vehicle);
        return newVehicle;
    }

    /**
     * Registers a check-up for a vehicle based on its plate number.
     *
     * @param vehicle       The vehicle to register the check-up for.
     * @param dateOfCheckUp The date of the check-up in the format "DD/MM/YYYY".
     * @param currentKms    The current kilometers of the vehicle.
     * @param maintenanceKm The kilometers for maintenance purposes.
     * @return True if the check-up is successfully added, false otherwise.
     */
    public boolean addCheckUp(Vehicle vehicle, Date dateOfCheckUp, double currentKms, double maintenanceKm) {
        Optional<CheckUp> newCheck = null;
        if (vehicle != null) {
            newCheck = vehicle.registerCheckUp(currentKms, dateOfCheckUp, maintenanceKm);
        } else {
            throw new IllegalArgumentException("Vehicle not found");
        }
        if (newCheck.isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of check-up details for a specified vehicle.
     *
     * @param vehicle The vehicle to retrieve check-up details for.
     * @return An Optional containing a list of check-up details if available, otherwise empty.
     */
    public Optional<List<CheckUp>> getCheckUpDetailsList(Vehicle vehicle) {
        return Optional.of(vehicle.getCheckUpList());
    }

    /**
     * Retrieves a list of vehicle registration plate numbers.
     *
     * @return A list of vehicle registration plate numbers.
     */
    public List<String> getVehiclePlateList() {
        List<String> vehiclePlate = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            vehiclePlate.add(v.getPlate());
        }
        return vehiclePlate;
    }

    /**
     * Retrieves a vehicle based on its index in the list.
     *
     * @param selectedPlate The index of the vehicle in the list.
     * @return The vehicle corresponding to the provided index.
     */
    public Vehicle getVehicleByIndex(int selectedPlate) {
        return vehicleList.get(selectedPlate - 1);
    }

    /**
     * Retrieves a list of vehicles in need of check-up.
     *
     * @return A list of vehicles needing check-up.
     */
    public List<Vehicle> getVehicleNeedingCheckUp() {
        List<Vehicle> vehiclesNeedingCheckUp = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.isCloseToCheck()) {
                vehiclesNeedingCheckUp.add(v);
            }
        }
        return vehiclesNeedingCheckUp;
    }

    /**
     * Retrieves the list of all vehicles in the repository.
     *
     * @return A list of all vehicles in the repository.
     */
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * Removes a vehicle from the repository.
     *
     * @param selectedVehicle The vehicle to remove.
     * @return True if the vehicle is successfully removed, otherwise false.
     */
    public boolean removeVehicle(Vehicle selectedVehicle) {
        if (vehicleList.contains(selectedVehicle)) {
            return vehicleList.remove(selectedVehicle);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }

    public boolean removeFromListCheckUp(Vehicle vehicle,CheckUp checkUp){
        if (vehicle.getCheckUpList().contains(checkUp)) {
            return vehicle.getCheckUpList().remove(checkUp);
        } else {
            throw new RuntimeException("Check Up not found");
        }
    }
}