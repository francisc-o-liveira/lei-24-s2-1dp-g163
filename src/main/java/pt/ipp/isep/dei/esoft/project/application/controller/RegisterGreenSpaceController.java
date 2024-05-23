package pt.ipp.isep.dei.esoft.project.application.controller;

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


}
