package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;

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
        return new VehicleDto();
    }
}
