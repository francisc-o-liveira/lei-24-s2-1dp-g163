package pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem;

import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The RegisterVehicleController class manages vehicle registration operations.
 * It interacts with the VehicleRepository to perform registration, retrieval,
 * and removal of vehicles.
 */
public class RegisterVehicleController {

    private VehicleRepository vehicleRepository;

    /**
     * Constructs a RegisterVehicleController object.
     */
    private RegisterVehicleController(){
        getVehicleRepository();
    }

    /**
     * Retrieves the vehicle repository instance.
     */
    public void getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the Vehicle Repository
            vehicleRepository = repositories.getVehicleRepository();
        }
    }

    /**
     * Retrieves the available vehicle types from the repository.
     *
     * @return An array of Vehicle.Type representing the available vehicle types.
     */
    public Vehicle.Type[] getVehicleTypeValues() {
        return vehicleRepository.getVehicleTypeList();
    }

    /**
     * Registers a new vehicle with the provided details.
     *
     * @param brand          The brand of the vehicle.
     * @param model          The model of the vehicle.
     * @param acquisitionDate The date of vehicle acquisition.
     * @param registerDate   The date of vehicle registration.
     * @param currentKM      The current kilometers of the vehicle.
     * @param checkupFrequency The frequency of vehicle checkups.
     * @param grossWeight    The gross weight of the vehicle.
     * @param tare           The tare of the vehicle.
     * @param plate          The registration plate number of the vehicle.
     * @param type           The type of the vehicle.
     * @param lastCheckUpDate The date of the last vehicle checkup.
     * @param lastCheckUpKm  The kilometers at the last vehicle checkup.
     * @return An Optional containing the registered vehicle if successful, otherwise empty.
     * @throws CloneNotSupportedException if the vehicle registration process encounters cloning issues.
     */
    public Optional<Vehicle> registerVehicle(String brand, String model, Date acquisitionDate, Date registerDate, double currentKM, double checkupFrequency, double grossWeight, int tare, String plate, Vehicle.Type type, Date lastCheckUpDate, double lastCheckUpKm) throws CloneNotSupportedException {
        return vehicleRepository.registerVehicle(brand, model, acquisitionDate, registerDate, currentKM, checkupFrequency, grossWeight, tare, plate, type, lastCheckUpDate, lastCheckUpKm);
    }

    /**
     * Retrieves a list of all registered vehicles.
     *
     * @return A List of Vehicle objects representing all registered vehicles.
     */
    public List<Vehicle> getVehicleList() {
        return vehicleRepository.getVehicleList();
    }

    /**
     * Removes a vehicle from the list of registered vehicles.
     *
     * @param selectedVehicle The vehicle to be removed.
     * @return true if the vehicle is successfully removed, otherwise false.
     */
    public void removeVehicleFromList(Vehicle selectedVehicle) {
         vehicleRepository.removeVehicle(selectedVehicle);
    }

    /**
     * Update Kilometers in a vehicle
     * @param selectedVehicle the vehicle to be updated the kilometers
     * @param km the actual current kilometers
     * @return true if update the kilometers
     */
    public boolean updateKm(Vehicle selectedVehicle, double km) throws IOException {
        return vehicleRepository.addUpdateKmToVehicle(selectedVehicle,km);
    }

    /**Gets the list of vehicles needing check-up
     *
     */
    public List<Vehicle> getVehiclesNeedingCheckUp(){
        return vehicleRepository.getVehicleNeedingCheckUp();
    }

    private static RegisterVehicleController instance;
    public static RegisterVehicleController getInstance(){
        if(instance == null){
            synchronized (RegisterVehicleController.class) {
                instance = new RegisterVehicleController();
            }
        }
        return instance;
    }
}
