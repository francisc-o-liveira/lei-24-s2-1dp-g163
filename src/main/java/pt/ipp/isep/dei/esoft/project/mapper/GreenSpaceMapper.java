package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;

public class GreenSpaceMapper {

    public GreenSpaceDto greenSpaceToGreenSpaceDto(GreenSpace greenSpace){
        return new GreenSpaceDto(greenSpace.getArea(), greenSpace.getAddress(), greenSpace.getName(), greenSpace.getType());
    }
}
