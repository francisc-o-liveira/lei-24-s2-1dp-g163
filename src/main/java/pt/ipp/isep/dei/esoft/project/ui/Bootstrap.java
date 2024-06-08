package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.core.application.repository.*;
import pt.ipp.isep.dei.esoft.project.core.application.repository.*;

public class Bootstrap {

    //Add some task categories to the repository as bootstrap
    public void run() throws Exception {
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