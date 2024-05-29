package pt.ipp.isep.dei.esoft.project.mapper;


import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Class EntryMapper
 */
public class EntryMapper {

    private GreenSpaceMapper mapperSpaces;

    public EntryMapper(){
        mapperSpaces = new GreenSpaceMapper();
    }

    public List<EntryDto> entryListToEntryDtoList(List<Entry> entryList) {
        List<EntryDto> entryDtoList = new ArrayList<EntryDto>();
        for (Entry entry : entryList) {
            entryDtoList.add(entryToEntryDto(entry));
        }
        return entryDtoList;
    }
    public EntryDto entryToEntryDto(Entry entry){
            return new EntryDto(entry.getStartDate(),entry.getStatus(),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()),entry.getReference());
    }
    public Entry entryDtoToEntryCreate(EntryDto entryDto, int reference){
        return new Entry(entryDto.getTitle(),entryDto.getDescription(),entryDto.getExpectedDuration(),mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()),entryDto.getDegreeUrgency(),entryDto.getStatus(),reference);
    }

    public void entryDtoToEntry(EntryDto entryDto, Entry entry) {
        // set entry on agenda
        if (entry.getStartDate() == null && entryDto.getStartDate() != null) {
            entry.setEntryAgenda(entryDto.getStartDate(), entryDto.getStatus());

            // Postpone Dont now if are working
        } else if (entry.getStartDate() != null && !entryDto.getStartDate().equals(entry.getStartDate()) && !entryDto.getStatus().equals(entry.getStatus())) {
                boolean value=true;
                ComparatorDates comparatorDates = new ComparatorDates();
                Entry entryToCompare = getEntryFromDtoToCompare(entryDto);
                for(Entry entryAgenda : Repositories.getInstance().getEntryRepository().getAgenda()){
                    if(comparatorDates.compare(entryToCompare,entryAgenda)==0 && !entryAgenda.getStatus().isCanceled()){ // == 0 significa que ha sobreposiçao das entrys nas datas
                        if(haveObjectsOf(entryToCompare,entryAgenda)){
                            value = false;
                        }
                    }
                }
                if(value){
                    entry.postponeEntry(entryDto.getStartDate());
                }



            // Cancel Entry // Dates equals, but status no
        } else if (entry.getStartDate().equals(entryDto.getStartDate()) && !entry.getStatus().equals(entryDto.getStatus())) {
            entry.cancelEntry();

            // DATAS IGUAIS E STATUS IGUAIS // POSSIVEL MUDANÇA DE TEAM OU VEICULO
        }else if (entry.getStartDate().equals(entryDto.getStartDate()) && entry.getStatus().equals(entryDto.getStatus())){
            if (!entry.getTeamAssigned().equals(entryDto.getTeamAssigned())){
                entry.setTeamAssigned(entryDto.getTeamAssigned());
            }
            /*if (!entry.getVehicleList().equals(entryDto.getVehicleList())){
                entry.setVehicleList(entryDto.getVehicleList());
            }*/
        }else {
            //modify task if it is possible
            throw new IllegalArgumentException();
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
        return new Entry(entryDto.getTitle(), entryDto.getDescription(),entryDto.getExpectedDuration(), mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()) ,entryDto.getDegreeUrgency(), entryDto.getStatus(),-1);
    }
}
