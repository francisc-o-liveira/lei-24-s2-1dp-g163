package pt.ipp.isep.dei.esoft.project.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * The GreenSpaceMapper class is responsible for mapping between GreenSpace and GreenSpaceDto objects.
 */
public class GreenSpaceMapper {

    /**
     * Converts a GreenSpace object to a GreenSpaceDto object.
     *
     * @param greenSpace The GreenSpace object to be converted.
     * @return The converted GreenSpaceDto object.
     */
    public GreenSpaceDto greenSpaceToGreenSpaceDto(GreenSpace greenSpace) {
        return new GreenSpaceDto(greenSpace.getArea(), greenSpace.getAddress(), greenSpace.getName(), greenSpace.getType(), greenSpace.createdBy());
    }

    public GreenSpace toDomain(GreenSpaceDto greenSpaceDto){
        return new GreenSpace(greenSpaceDto.getAreaInHectares(),greenSpaceDto.getName(),greenSpaceDto.getType(),greenSpaceDto.createdBy(),greenSpaceDto.getAddress(),null);
    }

    /**
     * Converts a list of GreenSpace objects to a list of GreenSpaceDto objects.
     *
     * @param greenSpaceList The list of GreenSpace objects to be converted.
     * @return The list of converted GreenSpaceDto objects.
     */
    public List<GreenSpaceDto> greenSpaceListToGreenSpaceDto(List<GreenSpace> greenSpaceList) {
        List<GreenSpaceDto> greenSpaceDtoList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaceList) {
            greenSpaceDtoList.add(greenSpaceToGreenSpaceDto(greenSpace));
        }
        return greenSpaceDtoList;
    }

    /**
     * Converts a GreenSpaceDto object to a GreenSpace object.
     *
     * @param greenSpaceDto The GreenSpaceDto object to be converted.
     * @return The converted GreenSpace object.
     * @throws RuntimeException if the corresponding GreenSpace object is not found.
     */
    public GreenSpace greenSpaceDtoToGreenSpace(GreenSpaceDto greenSpaceDto) {
        List<GreenSpace> greenSpaces = Repositories.getInstance().getOrganizationRepository().getGreenSpaceList();
        if (greenSpaces == null || greenSpaces.isEmpty()) {
            throw new RuntimeException("Didn't find the GreenSpace - Fatal Error");
        }
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.equals(greenSpaceDto)) {
                return greenSpace;
            }
        }
        throw new RuntimeException("Didn't find the GreenSpace - Fatal Error");
    }

    //No Mapper, nao se usa as classes de repositorios, 
    //só pegas no objeto de dominio e transformas em DTO, ou o inverso.

}
