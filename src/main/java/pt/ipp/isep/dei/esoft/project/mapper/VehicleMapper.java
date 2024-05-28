package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class VehicleMapper {
    public List<VehicleDto> vehicleListToVehicleDtoList(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDtos.add(vehicleToVehicleDto(vehicle));
        }
        return vehicleDtos;
    }

    public VehicleDto vehicleToVehicleDto(Vehicle vehicle) {
        return new VehicleDto(vehicle.getBrand(), vehicle.getModel(), vehicle.getType(), vehicle.getPlate(), vehicle.getCurrentKm(), vehicle.getAcquisitionDate());
    }

    public Vehicle vehicleDtoToVehicle(VehicleDto vehicleDto) {
        List<Vehicle> vehicles = Repositories.getInstance().getVehicleRepository().getVehicleList();
        for (Vehicle vehicle : vehicles) {
            if (vehicleDto.getPlate().equals(vehicle.getPlate())) {
                return vehicle;
            }
        }
        throw new RuntimeException("Vehicle not found in Repository - Fatal Error");
    }
}
