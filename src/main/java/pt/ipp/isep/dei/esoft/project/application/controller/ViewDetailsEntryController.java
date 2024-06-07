package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.adapters.EmailService;
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
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for viewing and managing details of entries.
 */
public class ViewDetailsEntryController {

    private EntryRepository entryRepository;
    private VehicleMapper vehicleMapper;
    private VehicleRepository vehicleRepository;
    private EntryMapper mapper;
    private TeamMapper teamMapper;
    private TeamRepository teamRepository;
    private EmailService serv;

    // Private constructor to enforce singleton pattern
    private ViewDetailsEntryController(){
        this.entryRepository = Repositories.getInstance().getEntryRepository();
        this.vehicleRepository = Repositories.getInstance().getVehicleRepository();
        this.mapper = new EntryMapper();
        this.vehicleMapper = new VehicleMapper();
        this.teamMapper = new TeamMapper();
        this.teamRepository = Repositories.getInstance().getTeamRepository();
        this.serv = new EmailService();
    }

    /**
     * Cancels an entry.
     *
     * @param entryDto The entry to be canceled.
     * @return True if the entry is successfully canceled, false otherwise.
     */
    public boolean cancelEntry(EntryDto entryDto){
        entryDto.cancel();
        return entryRepository.cancelEntry(entryDto).isPresent();
    }

    /**
     * Postpones an entry.
     *
     * @param entryDto The entry to be postponed.
     * @param postpone The new date for the entry.
     * @param startTime The new start time for the entry.
     * @return True if the entry is successfully postponed, false otherwise.
     */
    public boolean postponeEntry(EntryDto entryDto, Date postpone, Tempo startTime){
        entryDto.postpone(postpone,startTime);
        return entryRepository.postponeEntry(entryDto).isPresent();
    }

    /**
     * Retrieves a list of vehicles that are possible for assignment to the entry.
     *
     * @param entryDto The entry for which to retrieve possible vehicles.
     * @return A list of possible vehicles.
     */
    public List<VehicleDto> getVehicleListPossibleForEntry(EntryDto entryDto){
        return vehicleMapper.vehicleListToVehicleDtoList(entryRepository.getAgenda().filterVehicleNotUseInTime(vehicleRepository.getVehicleList(),entryDto));
    }

    /**
     * Assigns a vehicle to an entry.
     *
     * @param vehicleDto The vehicle to be assigned.
     * @param entryDto The entry to which the vehicle is to be assigned.
     * @return True if the vehicle is successfully assigned to the entry, false otherwise.
     */
    public boolean assignVehicleToEntry(VehicleDto vehicleDto, EntryDto entryDto){
        if (entryDto.assignVehicle(vehicleDto)){
            return entryRepository.assignVehicleOnEntry(entryDto).isPresent();
        }
        return false;
    }

    /**
     * Retrieves a list of teams that are possible for assignment to the entry.
     *
     * @param entryDto The entry for which to retrieve possible teams.
     * @return A list of possible teams.
     */
    public List<TeamDto> getTeamListPossibleForEntry(EntryDto entryDto){
        return teamMapper.teamListToTeamDtoList(entryRepository.getAgenda().filterTeamNotActivateInTime(teamRepository.getTeams(),entryDto));
    }

    /**
     * Assigns a team to an entry and sends email notifications.
     *
     * @param teamDto The team to be assigned.
     * @param entryDto The entry to which the team is to be assigned.
     * @return True if the team is successfully assigned to the entry, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public boolean assignTeamToEntry(TeamDto teamDto, EntryDto entryDto) throws IOException {
        entryDto.assignTeam(teamDto);
        if(entryRepository.assignTeamOnEntry(entryDto).isPresent()){
            serv.sendEmailToList(teamDto.getTeamList(),entryDto);
            return true;
        } else {
            return false;
        }
    }

    // Singleton pattern implementation
    private static ViewDetailsEntryController instance;
    public static ViewDetailsEntryController getInstance(){
        if(instance == null){
            synchronized (ViewDetailsEntryController.class) {
                instance = new ViewDetailsEntryController();
            }
        }
        return instance;
    }
}
