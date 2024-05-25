package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import java.util.List;

public class RegisterGreenSpaceController {

    private Organization org ;

    private static GreenSpaceMapper mapper;

    public RegisterGreenSpaceController() {
        org = Repositories.getInstance().getOrganizationRepository();
        mapper = new GreenSpaceMapper();
    }

    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    public boolean registerGreenSpace(String name, String address, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,address,name,type);
        return org.registerGreenSpace(newGreenSpaceDto).isPresent();
    }


    public List<GreenSpaceDto> getGreenSpaces() {
        return mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceList());
    }
}
