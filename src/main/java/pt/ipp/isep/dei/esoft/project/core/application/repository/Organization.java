package pt.ipp.isep.dei.esoft.project.core.application.repository;

import pt.ipp.isep.dei.esoft.project.core.application.database.ManagerBase;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.*;
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


    private static ManagerBase dataBaseManager;

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
        dataBaseManager =new ManagerBase();

    }

    public Organization() {
        managers = new ArrayList<>();
        name=NAME_PER_OMISSION;
        emailPrefix=EMAIL_PREFIX_PER_OMISSION;
        vatNumber=VAT_NUMBER_PER_OMISSION;
        phone=PHONE_PER_OMISSION;
        greenSpaces = new ArrayList<>();
        dataBaseManager = new ManagerBase();

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
            dataBaseManager.saveFromManagerInDataBase(managers);
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
        return saveFromGreenSpaceInDataBase(greenSpace);

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
            dataBaseManager.removeFromManagerDataBase(manager,managers);
        } else {
            throw new RuntimeException("This Collaborator does not exist in the Repository");
        }
    }

    public void removeGreenSpace(GreenSpace greenSpace) {
        if(greenSpaces.contains(greenSpace)){
            greenSpaces.remove(greenSpace);
            removeFromGreenSpaceDataBase(greenSpace);
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
    public void removeFromGreenSpaceDataBase(GreenSpace greenSpace) {
        greenSpaces.remove(greenSpace);
        saveGreenSpaces();
    }

    public boolean saveFromGreenSpaceInDataBase(GreenSpace greenSpace){
        if (!greenSpaces.contains(greenSpace)) {
            greenSpaces.add(greenSpace);
            return saveGreenSpaces();
        }
        return false;
    }

    private boolean saveGreenSpaces() {
        try (FileOutputStream fileOut = new FileOutputStream(MainApp.getGreenSpaceDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(greenSpaces);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromGreenSpaceDataBase() throws IOException {
        List<GreenSpace> greenSpaces;
        File file = new File(MainApp.getGreenSpaceDataBaseFile());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Organization database file did not exist and has been created. Starting with an empty list.");
                } else {
                    throw new IOException("Organization database file does not exist and could not be created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("An error occurred while trying to create the Organization database file.", e);
            }
        }
        try (FileInputStream fileIn = new FileInputStream(file)) {
            if (fileIn.getChannel().size()>0){
                ObjectInputStream in = new ObjectInputStream(fileIn);
                greenSpaces = (List<GreenSpace>) in.readObject();
                loadInSystem(greenSpaces);
            }
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<GreenSpace> greenSpaces) throws CloneNotSupportedException, IOException {
        if (greenSpaces == null) {
            throw new IOException("List is null, does not exist");
        }
        for (GreenSpace gs : greenSpaces) {
            saveGreenSpace(gs);
        }
    }

    public void loadManager(Manager manager) {
        if (validateManager(manager)){
            managers.add(manager);
        } else {
            throw new RuntimeException("This Already exists in the System");
        }
    }

    public void loadSystem(){
        try {
            dataBaseManager.loadFromManagerDataBase();
            loadFromGreenSpaceDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
