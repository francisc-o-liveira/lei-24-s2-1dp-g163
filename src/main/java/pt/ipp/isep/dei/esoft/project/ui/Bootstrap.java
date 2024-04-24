package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Employee;
import pt.ipp.isep.dei.esoft.project.domain.task.TaskCategory;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TaskCategoryRepository;

public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run(){
        addTaskCategories();
        addOrganization();
        addUsers();
    }

    private void addOrganization(){
        // EMPLOYEE PODE SER SUPER DE COLLABORATOR E DE HRM E DE VFM E DE GSM
        // DE FORMA A UTILIZAR O EMAIL DOS COLLABORADORES PARA OS REGISTAR NO PROGRAMA! MAIS TARDE POR CAUSA DA AGENDA
        //TODO: add organizations bootstrap here
        //get organization repository
        Organization organizationRepository = Repositories.getInstance().getOrganizationRepository();
        organizationRepository.addEmployee(new Employee("admin@this.app"));
        organizationRepository.addEmployee(new Employee("employee@this.app"));
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here
        //get task category repository
        TaskCategoryRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();
        taskCategoryRepository.add(new TaskCategory("Analysis"));
        taskCategoryRepository.add(new TaskCategory("Design"));
        taskCategoryRepository.add(new TaskCategory("Implementation"));
        taskCategoryRepository.add(new TaskCategory("Development"));
        taskCategoryRepository.add(new TaskCategory("Testing"));
        taskCategoryRepository.add(new TaskCategory("Deployment"));
        taskCategoryRepository.add(new TaskCategory("Maintenance"));
    }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_GSM, AuthenticationController.ROLE_GSM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_VFM,AuthenticationController.ROLE_VFM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_HRM,AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin", AuthenticationController.ROLE_GSM);
        authenticationRepository.addUserWithRole("HRM","hrm@this.app","hrm", AuthenticationController.ROLE_HRM);
        //TODO: COMO ADICIONAR UTILIZADORES
        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd", AuthenticationController.ROLE_EMPLOYEE);
    }
}