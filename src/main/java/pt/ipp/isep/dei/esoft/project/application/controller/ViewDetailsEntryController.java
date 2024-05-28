package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.mapper.TeamMapper;
import pt.ipp.isep.dei.esoft.project.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;

public class ViewDetailsEntryController {

    private EntryRepository entryRepository;

    private VehicleMapper vehicleMapper;

    private VehicleRepository vehicleRepository;

    private EntryMapper mapper;

    private TeamMapper teamMapper;

    private TeamRepository teamRepository;

    public ViewDetailsEntryController(){
        this.entryRepository = Repositories.getInstance().getEntryRepository();
        this.vehicleRepository = Repositories.getInstance().getVehicleRepository();
        this.mapper = new EntryMapper();
        this.vehicleMapper = new VehicleMapper();
        this.teamMapper = new TeamMapper();
        this.teamRepository = Repositories.getInstance().getTeamRepository();
    }

    public boolean cancelEntry(EntryDto entryDto){
        entryDto.cancel();
        return entryRepository.cancelEntry(entryDto).isPresent();
    }

    public boolean postponeEntry(EntryDto entryDto){
        return entryRepository.postponeEntry(entryDto).isPresent();
    }

    public List<VehicleDto> getVehicleListPossibleForEntry(EntryDto entryDto){
        return vehicleMapper.vehicleListToVehicleDtoList(entryRepository.filterVehicleNotUseInTime(vehicleRepository.getVehicleList(),entryDto));
    }

    public boolean assignVehicleToEntry(VehicleDto vehicleDto, EntryDto entryDto){
        if (entryDto.assignVehicle(vehicleMapper.vehicleDtoToVehicle(vehicleDto))){
            return entryRepository.assignVehicleOnEntry(entryDto).isPresent();
        }
        return false;
    }

    public List<TeamDto> getTeamListPossibleForEntry(EntryDto entryDto){
        return teamMapper.teamListToTeamDtoList(entryRepository.filterTeamNotActivateInTime(teamRepository.getTeams(),entryDto));
    }

    public boolean assignTeamToEntry(TeamDto teamDto, EntryDto entryDto){
        entryDto.assignTeam(teamMapper.teamDtoToTeam(teamDto));
        return entryRepository.assignTeamOnEntry(entryDto).isPresent();
    }


}
