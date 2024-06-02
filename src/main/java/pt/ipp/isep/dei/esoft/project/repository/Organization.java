package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Organization{
    private static final String NAME_PER_OMISSION = "MusgoSublime";
    private static final String EMAIL_PREFIX_PER_OMISSION = "@this.app" ;
    private static final String VAT_NUMBER_PER_OMISSION = "0000000000";
    private static final String PHONE_PER_OMISSION = "0123456789";
    private final List<Manager> managers;
    private final List<GreenSpace> greenSpaces;
    private String name;
    private String vatNumber;
    private String phone;
    private String emailPrefix;

    /**
     * This method is the constructor of the organization.
     *
     */
    public Organization(String vatNumber) {
        managers = new ArrayList<>();
        name=NAME_PER_OMISSION;
        this.vatNumber=vatNumber;
        emailPrefix=EMAIL_PREFIX_PER_OMISSION;
        phone=PHONE_PER_OMISSION;
        greenSpaces = new ArrayList<>();
    }

    public Organization() {
        managers = new ArrayList<>();
        name=NAME_PER_OMISSION;
        emailPrefix=EMAIL_PREFIX_PER_OMISSION;
        vatNumber=VAT_NUMBER_PER_OMISSION;
        phone=PHONE_PER_OMISSION;
        greenSpaces = new ArrayList<>();
    }

    public static GreenSpace.Type[] getEnumGreenSpaceType(){
        return GreenSpace.getEnumGreenSpaceTypes();
    }

    /**
     * This method checks if a manager works for the organization.
     *
     * @param manager The manager to be checked.
     * @return True if the manager works for the organization.
     */
    public boolean employs(Manager manager) {
        return managers.contains(manager);
    }

    /**
     * These methos check if the organization has an employee with the given email.
     *
     * @param email The email to be checked.
     * @return True if the organization has an employee with the given email.
     */
    public boolean anyManagerHasEmail(String email) {
        boolean result = false;
        for (Manager manager : managers) {
            if (manager.hasEmail(email)) {
                result = true;
            }
        }
        return result;
    }



    //add employee to organization
    public boolean addManager(String name, String position, String phone, String email) {
        Manager newManager = new Manager(name, position, phone, email);
        boolean success = false;
        if (validateManager(newManager)) {
            success = managers.add(newManager);
        }
        return success;
    }

    private boolean validateManager(Manager manager) {
        return employeesDoNotContain(manager);
    }

    private boolean employeesDoNotContain(Manager manager) {
        return !managers.contains(manager);
    }

    public boolean haveManagerWithEmail(String email) {
        for (Manager manager : managers) {
            if (manager.hasEmail(email)) {
                return true;
            }
        }
        return false;
    }

    public Optional<GreenSpace> registerGreenSpace(GreenSpaceDto newGreenSpaceDto) {
        Optional<GreenSpace> optionalValue = Optional.empty();
        GreenSpace greenSpace = new GreenSpace(newGreenSpaceDto);
        if (verifyIfExistAndSave(greenSpace)) {
            optionalValue = Optional.of(greenSpace);
        }
        return optionalValue;
    }

    private boolean verifyIfExistAndSave(GreenSpace greenSpace) {
        for (GreenSpace gs : greenSpaces) {
            if (greenSpace.equals(gs)) {
                return false;
            }
        }
        return saveGreenSpace(greenSpace);
    }

    private boolean saveGreenSpace(GreenSpace greenSpace) {
        return greenSpaces.add(greenSpace);
    }

    public List<GreenSpace> getGreenSpaceList() {
        return greenSpaces;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public static Manager.Role[] getEnumManagerRoles(){
        return Manager.getEnumManagerRoles();
    }

    public void removeManager(Manager manager) {
        if(managers.contains(manager)){
            managers.remove(manager);
        } else {
            throw new RuntimeException("This Collaborator does not exist in the Repository");
        }
    }

    public List<GreenSpace> getGreenSpaceListByManagerEmail(String email) {
        List<GreenSpace> greenSpaceList = new ArrayList<>();
        for (GreenSpace gs : greenSpaces) {
            if (gs.createdBy().equals(email)){
                greenSpaceList.add(gs);
            }
        }
        return greenSpaceList;
    }
    //Clone organization
}