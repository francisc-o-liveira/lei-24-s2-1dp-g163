package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.repository.*;
import pt.ipp.isep.dei.esoft.project.core.application.repository.*;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.File;
import java.io.IOException;

public class Bootstrap {
    CollaboratorRepository collaboratorRepository;
    SkillRepository skillRepository;
    JobCategoryRepository jobCategoryRepository;
    VehicleRepository vehicleRepository;

    //Add some task categories to the repository as bootstrap
    public void run() throws IOException{
        jobCategoryRepository = Repositories.getInstance().getJobCategoryRepository();
        skillRepository = Repositories.getInstance().getSkillRepository();
        collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        vehicleRepository = Repositories.getInstance().getVehicleRepository();

        addSkills();
        addJobCategories();
        addCollaborators();
        addOrganization();
        addVehicles();
        try {
            addEntries();
            addUsers();
        } catch (CloneNotSupportedException e) {
            System.out.println("erro inicializando");
        }
    }
    private void addSkills() throws IOException {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.loadFromSkillDataBase();
    }
    private void addJobCategories() throws IOException {

        jobCategoryRepository.loadFromJobCategoryDataBase();
    }
    private void addCollaborators() throws IOException {
        collaboratorRepository.loadFromCollaboratorDataBase();
    }

    private void addVehicles() throws IOException {
        vehicleRepository.loadFromVehicleDataBase();
    }
    private void addOrganization() throws IOException {
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.loadSystem();
        organizationRepository.addManager("ADMIN","GSM","+351910000000","admin@this.app");
    }
    private void addUsers() throws CloneNotSupportedException {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.loadFromAuthDataBase();
    }
    private void addEntries() throws CloneNotSupportedException {
        EntryRepository entryRepository=Repositories.getInstance().getEntryRepository();
        //entryRepository.loadFromDataBase();
    }

}