package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import java.util.List;

public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run(){

        try {
            addSkills();
            addJobCategories();
            addCollaborators();
            addVehicles();
            addEntries();
            addOrganization();
            addUsers();
        } catch (CloneNotSupportedException e) {
            System.out.println("erro inicializando");
        }
    }
    private void addSkills() throws CloneNotSupportedException {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
    }
    private void addJobCategories() throws CloneNotSupportedException {
        JobCategoryRepository jobCategoryRepository = Repositories.getInstance().getJobCategoryRepository();
    }
    private void addCollaborators() throws CloneNotSupportedException {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
    }

    private void addVehicles() throws CloneNotSupportedException {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
    }
    private void addOrganization(){
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.loadSystem();
        organizationRepository.addManager("ADMIN","GSM","+351910000000","admin@this.app");
    }
    private void addUsers() {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.loadFromAuthDataBase();
    }
    private void addEntries(){
        EntryRepository entryRepository=Repositories.getInstance().getEntryRepository();
    }
}