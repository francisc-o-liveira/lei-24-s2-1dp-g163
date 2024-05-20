package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.repository.Organization;

public class OrganizationController {
    private Organization organization;

    public OrganizationController(){
        organization = new Organization();
    }

    private Organization getOrganization() {
        return organization;
    }

    public boolean addEmployee(String name, String position, String phone, String email){
        return organization.addEmployee(name, position, phone, email);
    }
}
