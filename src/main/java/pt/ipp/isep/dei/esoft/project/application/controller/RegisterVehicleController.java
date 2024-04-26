package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

public class RegisterVehicleController {

    private VehicleRepository vehicleRepository;

    public void getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the JobCategory Repository
            vehicleRepository = repositories.getVehicleRepository();
        }
    }

    public Vehicle.Type[] getVehicleTypeValues() {
        return vehicleRepository.getVehicleTypeList();
    }
}
