package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class RegisterTaskController {
    private Organization org;
    private static GreenSpaceMapper greenMapper;
    public RegisterTaskController() {
        org = Repositories.getInstance().getOrganizationRepository();
        greenMapper = new GreenSpaceMapper();
    }
    public List<GreenSpaceDto> getGreenSpaceList(){
        return greenMapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceList());

    }
}
