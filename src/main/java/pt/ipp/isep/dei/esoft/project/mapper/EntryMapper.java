package pt.ipp.isep.dei.esoft.project.mapper;


import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class EntryMapper
 */
public class EntryMapper {

    private GreenSpaceMapper mapperSpaces;
    private VehicleMapper vehicleMapper;
    private TeamMapper teamMapper;

    public EntryMapper(){
        mapperSpaces = new GreenSpaceMapper();
        vehicleMapper = new VehicleMapper();
        teamMapper = new TeamMapper();
    }

    public List<EntryDto> entryListToEntryDtoList(List<Entry> entryList) {
        List<EntryDto> entryDtoList = new ArrayList<EntryDto>();
        for (Entry entry : entryList) {
            entryDtoList.add(entryToEntryDto(entry));
        }
        return entryDtoList;
    }
    public EntryDto entryToEntryDto(Entry entry){
        if(entry.getTeamAssigned()==null && entry.getVehicleList()==null && entry.getStartDate() ==null){
            return new EntryDto(new EntryState(entry.getStatus().getState()),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()),entry.getReference());
        }else{
            if (entry.getStartDate()!=null && entry.getTeamAssigned()==null && entry.getVehicleList()==null){
                return new EntryDto(entry.getStartDate(),new EntryState(entry.getStatus().getState()),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()),entry.getReference());
            }else if (entry.getStartDate()!=null && entry.getTeamAssigned()!=null || entry.getStartDate()!=null && entry.getVehicleList()==null){
                return new EntryDto(entry.getStartDate(),new EntryState(entry.getStatus().getState()),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()),entry.getReference(), teamMapper.teamToTeamDto(entry.getTeamAssigned()),vehicleMapper.vehicleListToVehicleDtoList(entry.getVehicleList()));
            }else if (entry.getStartDate()!=null && entry.getFinishDate()!=null){
                return new EntryDto(entry.getStartDate(),entry.getStartHour(),entry.getFinishDate(),new EntryState(entry.getStatus().getState()),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()),entry.getReference(), teamMapper.teamToTeamDto(entry.getTeamAssigned()),vehicleMapper.vehicleListToVehicleDtoList(entry.getVehicleList()),entry.getCollaboratorFinish());
            }else {
                throw new IllegalArgumentException("Impossible Dto");
            }
        }

    }
    public Entry entryDtoToEntryCreate(EntryDto entryDto){
        return new Entry(entryDto.getTitle(),entryDto.getDescription(),entryDto.getExpectedDuration(),mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()),entryDto.getDegreeUrgency(),entryDto.getStatus());
    }
    public void entryDtoToEntry(EntryDto entryDto, Entry entry) {
        if (shouldSetEntryAgenda(entry, entryDto)) {
            entry.setEntryAgenda(entryDto.getStartDate(),entryDto.getStartHour());
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

    private boolean shouldSetEntryAgenda(Entry entry, EntryDto entryDto) {
        return entry.getStartDate() == null && entryDto.getStartDate() != null
                && entryDto.getStatus().getState().equals(EntryState.State.Assigned);
    }

    private boolean shouldPostponeEntry(Entry entry, EntryDto entryDto) {
        return entry.getStartDate() != null && !entryDto.getStartDate().equals(entry.getStartDate())
                && !entryDto.getStatus().equals(entry.getStatus());
    }

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

    private boolean shouldCancelEntry(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate())
                && !entry.getStatus().equals(entryDto.getStatus())
                && entryDto.getFinishDate() == null;
    }

    private boolean shouldCompleteTask(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate())
                && !entry.getStatus().equals(entryDto.getStatus())
                && entryDto.getFinishDate() != null
                && entryDto.getTeamAssigned().equals(entry.getTeamAssigned())
                && entry.getVehicleList().equals(entryDto.getVehicleList());
    }

    private void completeTask(Entry entry, EntryDto entryDto) {
        if (entry.getFinishDate() == null && entryDto.getCollaboratorFinish() != null) {
            entry.completeTask(entryDto.getFinishDate(), entryDto.getCollaboratorFinish());
        } else {
            throw new IllegalArgumentException("This entry is already completed");
        }
    }

    private boolean shouldUpdateTeamOrVehicles(Entry entry, EntryDto entryDto) {
        return entry.getStartDate().equals(entryDto.getStartDate()) && entry.getStatus().equals(entryDto.getStatus());
    }

    private void updateTeamAndVehicles(Entry entry, EntryDto entryDto) {
        if (entry.getTeamAssigned() == null && entryDto.getTeamAssigned() != null) {
            entry.setTeamAssigned(teamMapper.teamDtoToTeam(entryDto.getTeamAssigned()));
        }
        if (entry.getVehicleList() == null && entryDto.getVehicleList() != null) {
            entry.setVehicleList(vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList()));
        } else if (entry.getVehicleList() != null && entryDto.getVehicleList() != null) {
            for (Vehicle vehicle : vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList())) {
                if (!entry.getVehicleList().contains(vehicle)) {
                    entry.assignVehicle(vehicle);
                }
            }
        }
    }

    private boolean haveObjectsOf(Entry entryToCompare, Entry entryAgenda) {
        for (Vehicle vehicle : entryToCompare.getVehicleList()) {
            for (Vehicle vehicleAgenda : entryAgenda.getVehicleList()) {
                if(vehicle.equals(vehicleAgenda)){
                    return true;
                }
            }
        }
        if (entryToCompare.getTeamAssigned().equals(entryAgenda.getTeamAssigned())) {
            return true;
        }
        return false;
    }

    private Entry getEntryFromDtoToCompare(EntryDto entryDto) {
        return new Entry(entryDto.getStartDate(), entryDto.getTitle(), entryDto.getDescription(),entryDto.getExpectedDuration(), mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()) ,entryDto.getDegreeUrgency(), entryDto.getStatus(),-1);
    }


}
