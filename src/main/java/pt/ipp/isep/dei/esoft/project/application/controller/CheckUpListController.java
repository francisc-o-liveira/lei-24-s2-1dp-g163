package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;

public class CheckUpListController {
    private VehicleRepository vehicleRepository;

    public CheckUpListController(){
        vehicleRepository= getVehicleRepository();
    }

   private VehicleRepository getVehicleRepository(){
        return Repositories.getInstance().getVehicleRepository();
   }
    public List<Vehicle> getVehicleNeedingCheckUpList() {
        return vehicleRepository.getVehicleNeedingCheckUp();
    }
}
