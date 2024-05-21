package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.task.TaskCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Organization{
    private static final String NAME_PER_OMISSION = "MusgoSublime";
    private static final String EMAIL_PREFIX_PER_OMISSION = "@this.app" ;
    private static final String VAT_NUMBER_PER_OMISSION = "0000000000";
    private static final String PHONE_PER_OMISSION = "0123456789";
    private final List<Manager> managers;
    private String name;
    private String vatNumber;
    private String phone;
    private String emailPrefix;

    /**
     * This method is the constructor of the organization.
     *
     */
    public Organization(String vatNumber) {
        managers = new ArrayList<>();
        name=NAME_PER_OMISSION;
        this.vatNumber=vatNumber;
        emailPrefix=EMAIL_PREFIX_PER_OMISSION;
        phone=PHONE_PER_OMISSION;
    }
    public Organization() {
        managers = new ArrayList<>();
        name=NAME_PER_OMISSION;
        emailPrefix=EMAIL_PREFIX_PER_OMISSION;
        vatNumber=VAT_NUMBER_PER_OMISSION;
        phone=PHONE_PER_OMISSION;
    }

    /**
     * This method checks if a manager works for the organization.
     *
     * @param manager The manager to be checked.
     * @return True if the manager works for the organization.
     */
    public boolean employs(Manager manager) {
        return managers.contains(manager);
    }
/*
    /**
     * This method creates a new task.
     *
     * @param reference            The reference of the task to be created.
     * @param description          The description of the task to be created.
     * @param informalDescription  The informal description of the task to be created.
     * @param technicalDescription The technical description of the task to be created.
     * @param duration             The duration of the task to be created.
     * @param cost                 The cost of the task to be created.
     * @param taskCategory         The task category of the task to be created.
     * @return

    public Optional<Task> createTask(String reference, String description, String informalDescription,
                                     String technicalDescription, int duration, double cost,
                                     TaskCategory taskCategory) {

        //TODO: we could also check if the employee works for the organization before proceeding
        //checkIfManagerWorksForOrganization(employee);

        // When a Task is added, it should fail if the Task already exists in the list of Tasks.
        // In order to not return null if the operation fails, we use the Optional class.
        Optional<Task> optionalValue = Optional.empty();

        Task task = new Task(reference, description, informalDescription, technicalDescription, duration, cost,
                taskCategory);

        if (addTask(task)) {
            optionalValue = Optional.of(task);
        }
        return optionalValue;
    }

    /**
     * This method adds a task to the list of tasks.
     *
     * @param task The task to be added.
     * @return True if the task was added successfully.

    private boolean addTask(Task task) {
        boolean success = false;
        if (validate(task)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = tasks.add(task.clone());
        }
        return success;

    }

    /**
     * This method validates the task, checking for duplicates.
     *
     * @param task The task to be validated.
     * @return True if the task is valid.

    private boolean validate(Task task) {
        return tasksDoNotContain(task);
    }

    /**
     * This method checks if the task is already in the list of tasks.
     *
     * @param task The task to be checked.
     * @return True if the task is not in the list of tasks.

    private boolean tasksDoNotContain(Task task) {
        return !tasks.contains(task);
    }
 */
    /**
     * These methos check if the organization has an employee with the given email.
     *
     * @param email The email to be checked.
     * @return True if the organization has an employee with the given email.
     */
    public boolean anyManagerHasEmail(String email) {
        boolean result = false;
        for (Manager manager : managers) {
            if (manager.hasEmail(email)) {
                result = true;
            }
        }
        return result;
    }



    //add employee to organization
    public boolean addManager(String name, String position, String phone, String email) {
        Manager newManager = new Manager(name, position, phone, email);
        boolean success = false;
        if (validateManager(newManager)) {
            success = managers.add(newManager);
        }
        return success;
    }

    private boolean validateManager(Manager manager) {
        return employeesDoNotContain(manager);
    }

    private boolean employeesDoNotContain(Manager manager) {
        return !managers.contains(manager);
    }

    public boolean haveManagerWithEmail(String email) {
        for (Manager manager : managers) {
            if (manager.hasEmail(email)) {
                return true;
            }
        }
        return false;
    }

    //Clone organization

}