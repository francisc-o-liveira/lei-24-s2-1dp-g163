package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run(){
        addOrganization();
        addUsers();
        try {
            addSkills();
            addJobCategories();
            addCollaborators();
            addVehicles();
        } catch (CloneNotSupportedException e) {
            System.out.println("erro inicializando");
        }
    }

    private void addSkills() throws CloneNotSupportedException {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.registerSkill("Plant Identification");
        skillRepository.registerSkill("Pruning");
        skillRepository.registerSkill("Pest and Disease Management");
        skillRepository.registerSkill("Watering Skills");
        skillRepository.registerSkill("Seasonal Planning");
        skillRepository.registerSkill("Tool Maintenance");
    }

    private void addJobCategories() throws CloneNotSupportedException {
        JobCategoryRepository jobCategoryRepository = Repositories.getInstance().getJobCategoryRepository();
        jobCategoryRepository.registerJobCategory("Gardener");
        jobCategoryRepository.registerJobCategory("Gardener Specialist");
        jobCategoryRepository.registerJobCategory("Pruner");
        jobCategoryRepository.registerJobCategory("Electrician");
    }

    private void addCollaborators() throws CloneNotSupportedException {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        collaboratorRepository.createCollaborator("Joaquim Mendes",new Date(1999,10,5),new Date(2024,5,2),"Rua das moinas","4630-132","Penafiel","joaquim@gmail.com","+351916834123", DocType.Type.CitizenCard,123456789,Repositories.getInstance().getJobCategoryRepository().getJobCategoryList().get(1));
        collaboratorRepository.createCollaborator("Maria Silva",new Date(1985,3,15),new Date(2024,1,10),"123 Main Street","1234-125","London","maria.silva@gmail.com","+441234567890", DocType.Type.Passport,987655,Repositories.getInstance().getJobCategoryRepository().getJobCategoryList().get(1));
    }

    private void addVehicles() throws CloneNotSupportedException {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        vehicleRepository.registerVehicle("Audi", "R8", new Date(2023, 10, 10), new Date(2020, 10, 10), 500000, 100000, 10000, 9000, "AB-12-AB", Vehicle.Type.LightPassenger, new Date(2024, 4, 11), 400000);
        vehicleRepository.registerVehicle("Mercedes", "AMG", new Date(2022, 5, 5), new Date(2016, 10, 10), 390000, 100000, 10000, 9000, "20-XX-20", Vehicle.Type.LightPassenger, new Date(2024, 4, 11), 295000);
        vehicleRepository.registerVehicle("Volkswagen", "T-Cross", new Date(2022, 5, 5), new Date(2016, 10, 10), 310000, 100000, 10000, 9000, "21-XX-21", Vehicle.Type.LightPassenger, new Date(2024, 4, 11), 300000);
    }
    private void addOrganization(){
        // EMPLOYEE PODE SER SUPER DE COLLABORATOR E DE HRM E DE VFM E DE GSM
        // DE FORMA A UTILIZAR O EMAIL DOS COLLABORADORES PARA OS REGISTAR NO PROGRAMA! MAIS TARDE POR CAUSA DA AGENDA
        //TODO: add organizations bootstrap here
        //get organization repository
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.addManager("ADMIN","GSM","910000000","admin@this.app");
    }


    private void addUsers() {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.loadFromAuthDataBase();
    }
}