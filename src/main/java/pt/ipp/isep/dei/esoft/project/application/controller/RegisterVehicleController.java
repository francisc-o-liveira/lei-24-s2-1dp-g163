package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Optional;

public class RegisterVehicleController {

    private VehicleRepository vehicleRepository;

    public RegisterVehicleController(){
        getVehicleRepository();
    }

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

    public Optional<Vehicle> registerVehicle(String brand, String model, Date acquisitionDate, Date registerDate, double currentKM, double checkupFrequency, double grossWeight, int tare, String plate, Vehicle.Type type, Date lastCheckUpDate, double lastCheckUpKm) throws CloneNotSupportedException {
        return vehicleRepository.registerVehicle(brand,model,acquisitionDate,registerDate,currentKM,checkupFrequency,grossWeight,tare,plate,type,lastCheckUpDate,lastCheckUpKm);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleRepository.getVehicleList();
    }
}
