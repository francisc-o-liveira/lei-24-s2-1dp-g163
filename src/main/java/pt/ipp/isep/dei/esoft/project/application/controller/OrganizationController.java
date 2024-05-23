package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.Organization;

import java.util.List;

public class OrganizationController {
    private Organization organization;

    public OrganizationController(){
        organization = new Organization();
    }

    private Organization getOrganization() {
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
    }
}
