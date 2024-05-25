package pt.ipp.isep.dei.esoft.project.mapper;


import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
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
        if (entry.getStartDate() == null && entryDto.getStartDate() != null){
            entry.setEntryAgenda(entryDto.getStartDate(),entryDto.getStatus());
        }else if (entry.getStartDate() != null && !entryDto.getStartDate().equals(entry.getStartDate())){
            // postPone entry
        }else if (entry.getStartDate().equals(entryDto.getStartDate())){
            // assign team and assign vehicles
        }else {
            //modify task if it is possible
            throw new IllegalArgumentException();
        }
    }
}
