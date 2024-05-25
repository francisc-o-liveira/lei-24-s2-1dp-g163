package pt.ipp.isep.dei.esoft.project.mapper;


import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;

/**
 * Class EntryMapper
 */
public class EntryMapper {

    private GreenSpaceMapper mapperSpaces;

    public EntryMapper(){
        mapperSpaces = new GreenSpaceMapper();
    }

    public EntryDto entryToEntryDto(Entry entry){
            return new EntryDto(entry.getStartDate(),entry.getStatus(),entry.getTitle(),entry.getDescription(),entry.getDegreeUrgency(),entry.getExpectedDuration(),mapperSpaces.greenSpaceToGreenSpaceDto(entry.getGreenSpace()));
    }


    public Entry entryDtoToEntryCreate(EntryDto entryDto, int reference){
        return new Entry(entryDto.getTitle(),entryDto.getDescription(),entryDto.getExpectedDuration(),mapperSpaces.greenSpaceDtoToGreenSpace(entryDto.getGreenSpace()),entryDto.getDegreeUrgency(),entryDto.getStatus());
    }
}
