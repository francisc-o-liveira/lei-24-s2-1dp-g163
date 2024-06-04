package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;

import java.util.List;

public class OrganizationController {
    private Organization organization;
    private AuthenticationRepository authenticationRepository;

    private OrganizationController(){
        organization = new Organization();
        authenticationRepository = new AuthenticationRepository();
    }

    public Organization getOrganization() {
        return organization;
    }

    public boolean addEmployee(String name, String position, String phone, String email){
        return organization.addManager(name, position, phone, email);
    }

    public List<Manager> getManagersList(){
        return organization.getManagers();
    }

    public void removeManager(Manager manager){
        organization.removeManager(manager);
        authenticationRepository.removeUserCredentialsInDataBase(manager.getEmail());
    }

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
