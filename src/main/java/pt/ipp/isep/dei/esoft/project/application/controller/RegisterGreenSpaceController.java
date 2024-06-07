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

/**
 * Controller class for registering green spaces.
 */
public class RegisterGreenSpaceController {

    private Organization org ;
    private static GreenSpaceMapper mapper;
    private ApplicationSession session;
    private static SortingList sort;

    /**
     * Constructs a new RegisterGreenSpaceController.
     *
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class is not found.
     * @throws InstantiationException If the instantiation is illegal.
     * @throws IllegalAccessException If the access is illegal.
     */
    public RegisterGreenSpaceController() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        org = Repositories.getInstance().getOrganizationRepository();
        mapper = new GreenSpaceMapper();
        session = ApplicationSession.getInstance();
        sort = getSortAlgorithm();
    }

    /**
     * Retrieves the enumeration of green space types.
     *
     * @return An array of green space types.
     */
    public GreenSpace.Type[] getEnumGreenSpaceType(){
        return Organization.getEnumGreenSpaceType();
    }

    /**
     * Registers a new green space.
     *
     * @param name The name of the green space.
     * @param address The address of the green space.
     * @param addressCity The city of the green space address.
     * @param addressZipCode The ZIP code of the green space address.
     * @param areaInHectares The area of the green space in hectares.
     * @param type The type of the green space.
     * @return True if the green space is successfully registered, false otherwise.
     */
    public boolean registerGreenSpace(String name, String address,String addressCity, String addressZipCode, double areaInHectares, GreenSpace.Type type){
        GreenSpaceDto newGreenSpaceDto = new GreenSpaceDto(areaInHectares,new Address(addressZipCode,address,addressCity),name,type,getManagerFromSession());
        return org.registerGreenSpace(newGreenSpaceDto).isPresent();
    }

    /**
     * Retrieves a sorted list of all green spaces.
     *
     * @return A sorted list of green space DTOs.
     */
    public List<GreenSpaceDto> getGreenSpaces() {
        return sort.sortingList(mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceList()));
    }

    /**
     * Retrieves a sorted list of green spaces managed by the current user.
     *
     * @return A sorted list of green space DTOs.
     */
    public List<GreenSpaceDto> getGreenSpacesByEmail() {
        return sort.sortingList(mapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceListByManagerEmail(getManagerFromSession())));
    }

    // Retrieves the manager's email from the current session
    private String getManagerFromSession(){
        return session.getCurrentSession().getUserEmail();
    }

    // Retrieves the sorting algorithm from the application session
    private SortingList getSortAlgorithm() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return ApplicationSession.getAlgorithmService();
    }

    // Singleton pattern implementation
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
