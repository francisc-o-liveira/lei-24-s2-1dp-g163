package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The VehicleRepository class manages vehicle data and operations.
 * It handles vehicle registration, activation, deactivation, check-ups, and retrieval.
 */
public class VehicleRepository {

    /**Variable for the list of Vehicles */
    private List<Vehicle> vehicleList;

    /** Initializes the list of Vehicles*/
    public VehicleRepository(){
        try {
            vehicleList=new ArrayList<>();
            loadFromVehicleDataBase();
        } catch (IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /** Method to search for a Vehicle based on the plate
     *
     * @param plate of the vehicle
     * @return the vehicle
     * @throws RuntimeException if a Vehicle with the given plate does not exist
     */
    public Vehicle searchForVehicleByPlate(String plate){
        for (Vehicle vehicle : vehicleList) {
            if(vehicle.getPlate().equals(plate)){
                return vehicle;
            }
        }
        throw new RuntimeException("No vehicle found with plate " + plate);
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
        boolean operationSuccess = false;
        if (isValidVehicle(vehicle)) {
            saveFromVehicleInDataBase(vehicle);
            newVehicle = Optional.of(vehicle);
        }
        if (!operationSuccess) {
            throw new CloneNotSupportedException("Vehicle already exists in the system");
        }
        return newVehicle;
    }

    /** Adds a vehicle to the list of Vehicles
     *
     * @param vehicle to be added
     * @return true if vehicle has been added
     */
    private boolean saveVehicle(Vehicle vehicle) {
        return vehicleList.add(vehicle);
    }

    /** Checks if a vehicle already exists on the list of vehicles
     *
     * @param vehicle to be checked
     * @return true if vehicle does not exist on the list
     */

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
     * @param dateOfCheckUp The date of the check-up in the format "YYYY/MM/DD".
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
    public void removeVehicle(Vehicle selectedVehicle) {
        if (vehicleList.contains(selectedVehicle)) {
            removeFromVehicleDataBase(selectedVehicle);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }

    /** Removes a Check-Up from the list of Check-Ups of Vehicle
     *
     * @param vehicle to remove check-up from
     * @param checkUp being removed
     * @return true if check-up has been removed
     */

    public boolean removeFromListCheckUp(Vehicle vehicle,CheckUp checkUp){
        if (vehicle.getCheckUpList().contains(checkUp)) {
            return vehicle.getCheckUpList().remove(checkUp);
        } else {
            throw new RuntimeException("Check Up not found");
        }
    }

    /** Updates the kilometers of a vehicle
     *
     * @param selectedVehicle to add kilometers
     * @param km to add to vehicle
     * @return true if kilometers have been added
     * @throws IOException if kilometers have not been updated
     */

    public boolean addUpdateKmToVehicle(Vehicle selectedVehicle, double km) throws IOException {
        if (selectedVehicle == null) {
            throw new NullPointerException("Vehicle not selected please close window and select one");
        }
        selectedVehicle.setCurrentKm(km);
        if (selectedVehicle.getCurrentKm()==km){
            return true;
        }else {
            return false;
        }
    }


    public void removeFromVehicleDataBase(Vehicle vehicle) {
        vehicleList.remove(vehicle);
        saveVehicles();
    }

    public void saveFromVehicleInDataBase(Vehicle vehicle) {
        if (!vehicleList.contains(vehicle)) {
            saveVehicle(vehicle);
            saveVehicles();
        }
    }

    private void saveVehicles() {
        cleanFile(MainApp.getVehicleDataBaseFile());
        try (FileOutputStream fileOut = new FileOutputStream(MainApp.getVehicleDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(vehicleList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanFile(String vehicleDataBaseFile) {
        File file = new File(MainApp.getVehicleDataBaseFile());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromVehicleDataBase() throws CloneNotSupportedException, IOException {
        File file = new File(MainApp.getVehicleDataBaseFile());
        if (!file.exists()) {
            throw new IOException("Vehicle database file does not exist. Starting with an empty list.");
        }
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            List<Vehicle> vehicleList = (List<Vehicle>) in.readObject();
            loadInSystem(vehicleList);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<Vehicle> vehicles) throws CloneNotSupportedException {
        for (Vehicle vehicle : vehicles) {
            saveVehicle(vehicle);
        }
    }

}