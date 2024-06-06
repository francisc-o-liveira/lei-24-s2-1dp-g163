package pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem;

import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.CheckUpListController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;

/**
 * The CheckUpListController class manages the retrieval of vehicles needing check-up.
 */
public class CheckUpListController {
    private VehicleRepository vehicleRepository;

    /**
     * Constructs a CheckUpListController object and initializes the vehicle repository.
     */
    private CheckUpListController(){
        vehicleRepository= getVehicleRepository();
    }

    /**
     * Retrieves the vehicle repository from the Repositories instance.
     *
     * @return The vehicle repository instance.
     */
    private VehicleRepository getVehicleRepository(){
        return Repositories.getInstance().getVehicleRepository();
    }

    /**
     * Retrieves a list of vehicles needing check-up.
     *
     * @return A list of vehicles needing check-up.
     */
    public List<Vehicle> getVehicleNeedingCheckUpList() {
        return vehicleRepository.getVehicleNeedingCheckUp();
    }

    private static CheckUpListController instance;
    public static CheckUpListController getInstance(){
        if(instance == null){
            synchronized (CheckUpListController.class) {
                instance = new CheckUpListController();
            }
        }
        return instance;
    }
}
