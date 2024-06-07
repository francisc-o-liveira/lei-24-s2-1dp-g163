package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;

import java.util.List;

/**
 * Controller class for managing organization-related tasks.
 */
public class OrganizationController {
    private Organization organization;
    private AuthenticationRepository authenticationRepository;

    // Private constructor to enforce singleton pattern
    private OrganizationController(){
        organization = new Organization();
        authenticationRepository = new AuthenticationRepository();
    }

    /**
     * Retrieves the organization.
     *
     * @return The organization.
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Adds a new manager to the organization.
     *
     * @param name The name of the manager.
     * @param position The position of the manager.
     * @param phone The phone number of the manager.
     * @param email The email address of the manager.
     * @return True if the manager is successfully added, false otherwise.
     */
    public boolean addEmployee(String name, String position, String phone, String email){
        return organization.addManager(name, position, phone, email);
    }

    /**
     * Retrieves a list of managers in the organization.
     *
     * @return A list of managers.
     */
    public List<Manager> getManagersList(){
        return organization.getManagers();
    }

    /**
     * Removes a manager from the organization.
     *
     * @param manager The manager to be removed.
     */
    public void removeManager(Manager manager){
        organization.removeManager(manager);
        authenticationRepository.removeUserCredentialsInDataBase(manager.getEmail());
    }

    // Singleton pattern implementation
    private static OrganizationController instance;
    public static OrganizationController getInstance(){
        if(instance == null){
            synchronized (OrganizationController.class) {
                instance = new OrganizationController();
            }
        }
        return instance;
    }
}
