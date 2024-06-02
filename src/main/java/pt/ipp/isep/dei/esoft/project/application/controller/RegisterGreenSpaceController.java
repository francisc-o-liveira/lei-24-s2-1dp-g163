package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.sortingAlgorithmsForUS27.SortingAlgorithm1;
import pt.ipp.isep.dei.esoft.project.sortingAlgorithmsForUS27.SortingAlgorithm2;
import pt.ipp.isep.dei.esoft.project.sortingAlgorithmsForUS27.SortingList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class RegisterGreenSpaceController {

    private Organization org = new Organization();

    /** For the sorting algorithms */
    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    private static final String SORTING_ALGORITHM="SortingList.Class";

    private static SortingList sort;
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
