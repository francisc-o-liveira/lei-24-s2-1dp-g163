package pt.ipp.isep.dei.esoft.project.core.application.mapper;

import pt.ipp.isep.dei.esoft.project.core.application.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Class EntryMapper responsible for mapping between Entry and EntryDto objects.
 */
public class EntryMapper {

    private GreenSpaceMapper mapperSpaces;
    private VehicleMapper vehicleMapper;
    private TeamMapper teamMapper;

    /**
     * Default constructor initializing required mappers.
     */
    public EntryMapper(){
        mapperSpaces = new GreenSpaceMapper();
        vehicleMapper = new VehicleMapper();
        teamMapper = new TeamMapper();
    }

    /**
     * Converts a list of Entry objects to a list of EntryDto objects.
     *
     * @param entryList the list of Entry objects
     * @return a list of EntryDto objects
     */
    public List<EntryDto> entryListToEntryDtoList(List<Entry> entryList) {
        List<EntryDto> entryDtoList = new ArrayList<>();
        for (Entry entry : entryList) {
            entryDtoList.add(entryToEntryDto(entry));
        }
        return entryDtoList;
    }

    /**
     * Converts an Entry object to an EntryDto object.
     *
     * @param entry the Entry object
     * @return the EntryDto object
     */
    public EntryDto entryToEntryDto(Entry entry) {
        if (entry.getTeamAssigned() == null && entry.getVehicleList().isEmpty() && entry.getStartDate() == null) {
            return new EntryDto(new EntryState(entry.getStatus().getState()), entry.getTitle(), entry.getDescription(), entry.getDegreeUrgency(), entry.getExpectedDuration(), mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()), entry.getReference());
        } else {
            if (entry.getStartDate() != null && entry.getTeamAssigned() == null && entry.getVehicleList().isEmpty()) {
                return new EntryDto(entry.getStartDate(),entry.getStartHour(), new EntryState(entry.getStatus().getState()), entry.getTitle(), entry.getDescription(), entry.getDegreeUrgency(), entry.getExpectedDuration(), mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()), entry.getReference());
            } else if (entry.getStartDate() != null && entry.getFinishDate() != null) {
                return new EntryDto(entry.getStartDate(), entry.getStartHour(), entry.getFinishDate(), new EntryState(entry.getStatus().getState()), entry.getTitle(), entry.getDescription(), entry.getDegreeUrgency(), entry.getExpectedDuration(), mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()), entry.getReference(), teamMapper.teamToTeamDto(entry.getTeamAssigned()), vehicleMapper.vehicleListToVehicleDtoList(entry.getVehicleList()), entry.getCollaboratorFinish());
            } else if (entry.getStartDate() != null && (entry.getTeamAssigned() != null || !entry.getVehicleList().isEmpty())) {
                return new EntryDto(entry.getStartDate(),entry.getStartHour(), new EntryState(entry.getStatus().getState()), entry.getTitle(), entry.getDescription(), entry.getDegreeUrgency(), entry.getExpectedDuration(), mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()), entry.getReference(), teamMapper.teamToTeamDto(entry.getTeamAssigned()), vehicleMapper.vehicleListToVehicleDtoList(entry.getVehicleList()));
            } else {
                throw new IllegalArgumentException("Impossible Dto");
            }
        }
    }

    /**
     * Converts an EntryDto object to a new Entry object.
     *
     * @param entryDto the EntryDto object
     * @return the new Entry object
     */
    public Entry entryDtoToEntryCreate(EntryDto entryDto){
        return new Entry(entryDto.getTitle(), entryDto.getDescription(), entryDto.getExpectedDuration(), mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()), entryDto.getDegreeUrgency(), entryDto.getStatus());
    }

    public Entry toDomain(EntryDto entryDto){
        return new Entry(entryDto.getStartDate(), entryDto.getTitle(), entryDto.getDescription(), entryDto.getExpectedDuration(), mapperSpaces.toDomain(entryDto.getGreenSpace()), entryDto.getDegreeUrgency(), entryDto.getStatus(), Integer.parseInt(entryDto.getReference()));
    }

    /**
     * Updates an existing Entry object with data from an EntryDto object.
     *
     * @param entryDto the EntryDto object
     * @param entry the Entry object to update
     */
    public void entryDtoToEntry(EntryDto entryDto, Entry entry) {
        if (shouldSetEntryAgenda(entry, entryDto)) {
            entry.setEntryAgenda(entryDto.getStartDate(), entryDto.getStartHour());
        } else if (shouldPostponeEntry(entry, entryDto)) {
            if (canPostpone(entryDto)) {
                entry.postponeEntry(entryDto.getStartDate());
            }
        } else if (shouldCancelEntry(entry, entryDto)) {
            entry.cancelEntry();
        } else if (shouldCompleteTask(entry, entryDto)) {
            completeTask(entry, entryDto);
        } else if (shouldUpdateTeamOrVehicles(entry, entryDto)) {
            updateTeamAndVehicles(entry, entryDto);
        } else {
            throw new IllegalArgumentException("Cannot modify task");
        }
    }

    /**
     * Checks if the entry's agenda should be set based on the entry and entryDto objects.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @return true if the agenda should be set, false otherwise
     */
    private boolean shouldSetEntryAgenda(Entry entry, EntryDto entryDto) {
        return entry.getStartDate() == null && entryDto.getStartDate() != null
                && entryDto.getStatus().getState().equals(EntryState.State.Assigned);
    }

    /**
     * Checks if the entry should be postponed based on the entry and entryDto objects.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @return true if the entry should be postponed, false otherwise
     */
    private boolean shouldPostponeEntry(Entry entry, EntryDto entryDto) {
        return entry.getStartDate() != null && !entryDto.getStartDate().equals(entry.getStartDate())
                && !entryDto.getStatus().equals(entry.getStatus());
    }

    /**
     * Checks if the entry can be postponed based on the entryDto object.
     *
     * @param entryDto the EntryDto object
     * @return true if the entry can be postponed, false otherwise
     */
    private boolean canPostpone(EntryDto entryDto) {
        boolean value = true;
        ComparatorDates comparatorDates = new ComparatorDates();
        Entry entryToCompare = getEntryFromDtoToCompare(entryDto);
        for (Entry entryAgenda : Repositories.getInstance().getEntryRepository().getAgenda().getList()) {
            if (comparatorDates.compare(entryToCompare, entryAgenda) == 0 && !entryAgenda.getStatus().isCanceled()) {
                if (haveObjectsOf(entryToCompare, entryAgenda)) {
                    value = false;
                }
            }
        }
        return value;
    }

    /**
     * Checks if the entry should be canceled based on the entry and entryDto objects.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @return true if the entry should be canceled, false otherwise
     */
    private boolean shouldCancelEntry(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate())
                && !entry.getStatus().equals(entryDto.getStatus())
                && entryDto.getFinishDate() == null;
    }

    /**
     * Checks if the task should be completed based on the entry and entryDto objects.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @return true if the task should be completed, false otherwise
     */
    private boolean shouldCompleteTask(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate())
                && !entry.getStatus().equals(entryDto.getStatus())
                && entryDto.getFinishDate() != null
                && entryDto.getTeamAssigned().equals(entry.getTeamAssigned())
                && entry.getVehicleList().equals(entryDto.getVehicleList());
    }

    /**
     * Completes the task for the entry based on the entryDto object.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @throws IllegalArgumentException if the entry is already completed
     */
    private void completeTask(Entry entry, EntryDto entryDto) {
        if (entry.getFinishDate() == null && entryDto.getCollaboratorFinish() != null) {
            entry.completeTask(entryDto.getFinishDate(), entryDto.getCollaboratorFinish());
        } else {
            throw new IllegalArgumentException("This entry is already completed");
        }
    }

    /**
     * Checks if the team or vehicles should be updated based on the entry and entryDto objects.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     * @return true if the team or vehicles should be updated, false otherwise
     */
    private boolean shouldUpdateTeamOrVehicles(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate()) && entry.getStatus().equals(entryDto.getStatus())&& ((entry.getTeamAssigned() == null || entryDto.getTeamAssigned() != null) || (entry.getVehicleList().size()==0 && entryDto.getVehicleList().size()>0));
    }

    /**
     * Updates the team and vehicles for the entry based on the entryDto object.
     *
     * @param entry the Entry object
     * @param entryDto the EntryDto object
     */
    private void updateTeamAndVehicles(Entry entry, EntryDto entryDto) {
        if (entry.getTeamAssigned() == null && entryDto.getTeamAssigned() != null) {
            entry.setTeamAssigned(teamMapper.teamDtoToTeam(entryDto.getTeamAssigned()));
        }
        if (entry.getVehicleList().size()==0 && entryDto.getVehicleList().size()>0) {
            entry.setVehicleList(vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList()));
        } else if (entry.getVehicleList().size()>0 && entryDto.getVehicleList().size()>0 && !entry.getVehicleList().equals(entryDto.getVehicleList())) {
            for (Vehicle vehicle : vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList())) {
                if (!entry.getVehicleList().contains(vehicle)) {
                    entry.assignVehicle(vehicle);
                }
            }
        }
    }

    /**
     * Checks if the two entries share the same objects (vehicles and team).
     *
     * @param entryToCompare the entry to compare
     * @param entryAgenda the entry from the agenda
     * @return true if they share objects, false otherwise
     */
    private boolean haveObjectsOf(Entry entryToCompare, Entry entryAgenda) {
        for (Vehicle vehicle : entryToCompare.getVehicleList()) {
            for (Vehicle vehicleAgenda : entryAgenda.getVehicleList()) {
                if (vehicle.equals(vehicleAgenda)) {
                    return true;
                }
            }
        }
        if (entryToCompare.getTeamAssigned().equals(entryAgenda.getTeamAssigned())) {
            return true;
        }
        return false;
    }

    /**
     * Creates an entry from the EntryDto object for comparison purposes.
     *
     * @param entryDto the EntryDto object
     * @return the created Entry object
     */
    private Entry getEntryFromDtoToCompare(EntryDto entryDto) {
        return new Entry(entryDto.getStartDate(), entryDto.getTitle(), entryDto.getDescription(), entryDto.getExpectedDuration(), mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()), entryDto.getDegreeUrgency(), entryDto.getStatus(), -1);
    }
}
