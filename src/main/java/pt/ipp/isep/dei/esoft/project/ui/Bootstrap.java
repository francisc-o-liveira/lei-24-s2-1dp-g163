package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.core.application.repository.*;
import pt.ipp.isep.dei.esoft.project.core.application.repository.*;

import java.io.IOException;

public class Bootstrap {

    //Add some task categories to the repository as bootstrap
    public void run() throws Exception{
        try {
            addSkills();
            addJobCategories();
            addCollaborators();
            addVehicles();
            addEntries();
            addOrganization();
            addUsers();
        } catch (Exception e) {
            System.out.println("erro inicializando");
            throw new Exception();
        }
    }
    private void addSkills() throws Exception {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
    }
    private void addJobCategories() throws Exception {
        JobCategoryRepository jobCategoryRepository = Repositories.getInstance().getJobCategoryRepository();
    }
    private void addCollaborators() throws Exception {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
    }

    private void addVehicles() throws Exception {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
    }
    private void addOrganization() throws Exception {
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.loadSystem();
        organizationRepository.addManager("ADMIN","GSM","+351910000000","admin@this.app");
    }
    private void addUsers() throws Exception {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.loadFromAuthDataBase();
    }
    private void addEntries() throws Exception {
        EntryRepository entryRepository=Repositories.getInstance().getEntryRepository();
    }
}