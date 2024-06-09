package pt.ipp.isep.dei.esoft.project.core.application.mapper;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * The VehicleMapper class is responsible for mapping between Vehicle and VehicleDto objects.
 */
public class VehicleMapper {

    /**
     * Converts a list of Vehicle objects to a list of VehicleDto objects.
     *
     * @param vehicles The list of Vehicle objects to be converted.
     * @return The list of converted VehicleDto objects.
     */
    public List<VehicleDto> vehicleListToVehicleDtoList(List<Vehicle> vehicles) {
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDtos.add(vehicleToVehicleDto(vehicle));
        }
        return vehicleDtos;
    }

    /**
     * Converts a Vehicle object to a VehicleDto object.
     *
     * @param vehicle The Vehicle object to be converted.
     * @return The converted VehicleDto object.
     */
    public VehicleDto vehicleToVehicleDto(Vehicle vehicle) {
        return new VehicleDto(vehicle.getBrand(), vehicle.getModel(), vehicle.getType(), vehicle.getPlate(), vehicle.getCurrentKm(), vehicle.getAcquisitionDate());
    }

    /**
     * Converts a VehicleDto object to a Vehicle object.
     *
     * @param vehicleDto The VehicleDto object to be converted.
     * @return The converted Vehicle object.
     * @throws RuntimeException if the corresponding Vehicle object is not found in the repository.
     */
    public Vehicle vehicleDtoToVehicle(VehicleDto vehicleDto) {
        List<Vehicle> vehicles = Repositories.getInstance().getVehicleRepository().getVehicleList();
        for (Vehicle vehicle : vehicles) {
            if (vehicleDto.getPlate().equals(vehicle.getPlate())) {
                return vehicle;
            }
        }
        throw new RuntimeException("Vehicle not found in Repository - Fatal Error");
    }

    /**
     * Converts a list of VehicleDto objects to a list of Vehicle objects.
     *
     * @param vehicleList The list of VehicleDto objects to be converted.
     * @return The list of converted Vehicle objects.
     */
    public List<Vehicle> vehicleListDtoToVehicleList(List<VehicleDto> vehicleList) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (VehicleDto vehicleDto : vehicleList) {
            vehicles.add(vehicleDtoToVehicle(vehicleDto));
        }
        return vehicles;
    }
}
