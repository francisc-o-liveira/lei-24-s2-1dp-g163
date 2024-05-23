package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

public class RegisterGreenSpaceController {

    private Organization org = new Organization();

    public RegisterGreenSpaceController() {
        org = Repositories.getInstance().getOrganizationRepository();
    }

    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    public boolean registerGreenSpace(String name, String address, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,address,name,type);
        return org.registerGreenSpace(newGreenSpaceDto).isPresent();
    }

}
