package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ.SortingAlgorithm1;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ.SortingAlgorithm2;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ.SortingList;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class RegisterGreenSpaceController {

    private Organization org ;

    private static GreenSpaceMapper mapper;

    private ApplicationSession session;

    private static SortingList sort;

    private RegisterGreenSpaceController() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        org = Repositories.getInstance().getOrganizationRepository();
        mapper = new GreenSpaceMapper();
        session = ApplicationSession.getInstance();
        sort = getSortAlgorithm();
    }

    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    public boolean registerGreenSpace(String name, String address,String addressCity, String addressZipCode, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,new Address(addressZipCode,address,addressCity),name,type,getManagerFromSession());
        return org.registerGreenSpace(newGreenSpaceDto).isPresent();
    }

    public List<GreenSpaceDto> getGreenSpaces() {
        return sort.sortingList(mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceList()));
    }

    public List<GreenSpaceDto> getGreenSpacesByEmail() {
        return sort.sortingList(mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceListByManagerEmail(getManagerFromSession())));
    }

    private String getManagerFromSession(){
        return session.getCurrentSession().getUserEmail();
    }

    private SortingList getSortAlgorithm() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return ApplicationSession.getAlgorithmService();
    }


    private static RegisterGreenSpaceController instance;

    public static RegisterGreenSpaceController getInstance() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(instance == null){
            synchronized (RegisterGreenSpaceController.class) {
                instance = new RegisterGreenSpaceController();
            }
        }
        return instance;
    }
}

