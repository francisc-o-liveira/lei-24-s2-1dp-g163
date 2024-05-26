package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

public class ViewDetailsEntryController {

    private EntryRepository entryRepository;

    private VehicleMapper vehicleMapper;

    private VehicleRepository vehicleRepository;

    private EntryMapper mapper;

    public ViewDetailsEntryController(){
        this.entryRepository = Repositories.getInstance().getEntryRepository();
        this.vehicleRepository = Repositories.getInstance().getVehicleRepository();
        this.mapper = new EntryMapper();
        this.vehicleMapper = new VehicleMapper();
    }

    public boolean cancelEntry(EntryDto entryDto){
        entryDto.cancel();
        return entryRepository.cancelEntry(entryDto).isPresent();
    }

    public boolean postEntry(EntryDto entryDto){
        return entryRepository.postponeEntry(entryDto).isPresent();
    }

    public getVehicleListPossibleForEntry(EntryDto entryDto){
        return vehicleMapper.vehicleListToVehicleDtoList(entryRepository.filterVehicleNotUseInTime(vehicleRepository.getVehicleList(),entryDto));
    }
}
