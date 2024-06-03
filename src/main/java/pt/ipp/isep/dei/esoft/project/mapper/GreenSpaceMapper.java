package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

public class GreenSpaceMapper {

    public GreenSpaceDto greenSpaceToGreenSpaceDto(GreenSpace greenSpace){
        return new GreenSpaceDto(greenSpace.getArea(), greenSpace.getAddress(), greenSpace.getName(), greenSpace.getType(), greenSpace.createdBy());
    }

    public List<GreenSpaceDto> greenSpaceListToGreenSpaceDto(List<GreenSpace> greenSpaceList){
        List<GreenSpaceDto> greenSpaceDtoList = new ArrayList<GreenSpaceDto>();
        for(GreenSpace greenSpace : greenSpaceList){
            greenSpaceDtoList.add(greenSpaceToGreenSpaceDto(greenSpace));
        }
        return greenSpaceDtoList;
    }

    public GreenSpace greenSpaceDtoToGreenSpace(GreenSpaceDto greenSpaceDto){
         List<GreenSpace> greenSpaces = Repositories.getInstance().getOrganizationRepository().getGreenSpaceList();
        if (greenSpaces == null || greenSpaces.size() == 0){
            throw new RuntimeException("Dont Find Your Green Space - Fatal Error");
        }
         for (GreenSpace greenSpace : greenSpaces) {
             if(greenSpace.equals(greenSpaceDto)){
                 return greenSpace;
             }else {
                 throw new RuntimeException("Dont Find Your Green Space - Fatal Error");
             }
         }
        return null;
    }
}
