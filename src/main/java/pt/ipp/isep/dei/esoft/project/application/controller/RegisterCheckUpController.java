package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Optional;

/**
 * This class have the responsibility to Register a Check-Up (Controller)
 */
public class RegisterCheckUpController {
    private final VehicleRepository vehicleRepository;
    /**
     * This method is the method that get the vehicle repository from the Repositories Instance
     */
    public RegisterCheckUpController() {
        this.vehicleRepository = Repositories.getInstance().getVehicleRepository();
    }


    /**
     * This method get the data needed to register a check-up!
     *
     * @return List of plates to show to the User
     */
    public List<String> getDataNeededToRegisterCheckUp() {
        return vehicleRepository.getVehiclePlateList();
    }


    /**
     * This method add the check-up to the vehicle with a call for the vehicle repository
     *
     * @param vehicle      The vehicle that go have the new check-up
     * @param date         The date that the check-up have been done
     * @param currentKms   Correspond to the actual kilometers of the checkUp
     * @param mainetanceKm
     * @return a Optional List of all the check-up's made by the vehicle selected to add a new check-up if have been approved
     * or return null if fail
     */
    public Optional<Object> addCheckUp(Vehicle vehicle, Date date, double currentKms, double mainetanceKm) {
        if (vehicleRepository.addCheckUp(vehicle, date, currentKms,mainetanceKm)) {
            return Optional.of(vehicleRepository.getCheckUpDetailsList(vehicle));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method return the vehicle selected by an index selected by the user.
     * @param selectedPlate the index of the selected plate introduced by the user
     * @return the vehicle selected by the user by the index
     */

    public Vehicle getVehicleByIndex(int selectedPlate) {
        return vehicleRepository.getVehicleByIndex(selectedPlate);
    }

    public void removeFromList(Vehicle vehicle,CheckUp checkUp){
        vehicleRepository.removeFromListCheckUp(vehicle,checkUp);
    }
}
