package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsForUS27.SortingAlgorithm1;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsForUS27.SortingAlgorithm2;
import pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsForUS27.SortingList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class RegisterGreenSpaceController {

    private Organization org ;

    private static GreenSpaceMapper mapper;

    private ApplicationSession session;

    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    private static final String SORTING_ALGORITHM="SortingList.Class";
    private static SortingList sort;

    public RegisterGreenSpaceController() {
        org = Repositories.getInstance().getOrganizationRepository();
        mapper = new GreenSpaceMapper();
        session = ApplicationSession.getInstance();
        getProperties();
    }

    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    public boolean registerGreenSpace(String name, String address,String addressCity, String addressZipCode, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,address,addressCity,addressZipCode,name,type,getManagerFromSession());
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

    private void getProperties() {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(CONFIGURATION_FILENAME);
            props.load(in);
            in.close();
            String sortAlgorithm = props.getProperty(SORTING_ALGORITHM);
            if ("SortingAlgorithm1".equals(sortAlgorithm)) {
                sort = new SortingAlgorithm1();
            } else if ("SortingAlgorithm2".equals(sortAlgorithm)) {
                sort = new SortingAlgorithm2();
            } else {
                throw new IllegalArgumentException("Invalid sorting algorithm specified in the configuration file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
