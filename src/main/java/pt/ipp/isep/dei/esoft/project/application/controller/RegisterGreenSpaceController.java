package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import java.util.List;

public class RegisterGreenSpaceController {

    private Organization org ;

    private static GreenSpaceMapper mapper;

    private ApplicationSession session;

    public RegisterGreenSpaceController() {
        org = Repositories.getInstance().getOrganizationRepository();
        mapper = new GreenSpaceMapper();
        session = ApplicationSession.getInstance();
    }

    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    public boolean registerGreenSpace(String name, String address,String addressCity, String addressZipCode, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,address,addressCity,addressZipCode,name,type,getManagerFromSession());
        return org.registerGreenSpace(newGreenSpaceDto).isPresent();
    }

    public List<GreenSpaceDto> getGreenSpaces() {
        return mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceList());
    }

    public List<GreenSpaceDto> getGreenSpacesByEmail() {
        return mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceListByManagerEmail(getManagerFromSession()));
    }

    private String getManagerFromSession(){
        return session.getCurrentSession().getUserEmail();
    }
}
