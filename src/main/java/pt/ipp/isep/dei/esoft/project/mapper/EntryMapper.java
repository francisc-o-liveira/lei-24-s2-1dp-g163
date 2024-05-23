package pt.ipp.isep.dei.esoft.project.mapper;


import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;

/**
 * Class EntryMapper
 */
public class EntryMapper {

    public EntryMapper(){
    }

    public EntryDto entryToEntryDto(Entry entry){
        return new EntryDto(entry.getStartDate(),entry.getStatus(),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency());
    }

    public Entry entryDtoToEntry(EntryDto entryDto){
        return null;
    }
}
