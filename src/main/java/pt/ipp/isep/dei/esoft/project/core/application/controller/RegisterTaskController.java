package pt.ipp.isep.dei.esoft.project.core.application.controller;

import pt.ipp.isep.dei.esoft.project.core.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.List;

/**
 * Controller class for registering tasks.
 */
public class RegisterTaskController {
    private Organization org;
    private static GreenSpaceMapper greenMapper;

    private ApplicationSession session;

    private EntryMapper mapper;

    private EntryRepository entryRepository;

    // Private constructor to enforce singleton pattern
    private RegisterTaskController() {
        org = Repositories.getInstance().getOrganizationRepository();
        greenMapper = new GreenSpaceMapper();
        entryRepository = Repositories.getInstance().getEntryRepository();
        session = ApplicationSession.getInstance();
        mapper = new EntryMapper();
    }

    /**
     * Retrieves a list of green spaces managed by the current user.
     *
     * @return A list of green space DTOs.
     */
    public List<GreenSpaceDto> getGreenSpaceList(){
        return greenMapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceListByManagerEmail(getManagerFromSession()));
    }

    /**
     * Retrieves the degree of urgency for tasks.
     *
     * @return An array of task degree of urgency.
     */
    public static Task.DegreeUrgency[] getDegreOfUrgency(){
        return EntryRepository.getDegreeOfUrgency();
    }

    /**
     * Registers a new task entry.
     *
     * @param title The title of the task.
     * @param description The description of the task.
     * @param degreeUrgency The degree of urgency of the task.
     * @param expectedDuration The expected duration of the task.
     * @param greenSpaceDto The green space associated with the task.
     * @return True if the task entry is successfully registered, false otherwise.
     */
    public boolean registerTaskEntry( String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto){
        EntryDto entryDto = new EntryDto(title,description,degreeUrgency,expectedDuration,greenSpaceDto);
        if (entryRepository.registerNewTask(entryDto).isPresent()){
            return true;
        }
        return false;
    }

    /**
     * Retrieves the to-do list of tasks.
     *
     * @return A list of to-do task entries.
     */
    public List<EntryDto> getToDoList(){
        return mapper.entryListToEntryDtoList(entryRepository.getToDo().getToDo());
    }

    // Retrieves the manager's email from the current session
    private String getManagerFromSession(){
        return session.getCurrentSession().getUserEmail();
    }

    // Singleton pattern implementation
    private static RegisterTaskController instance;
    public static RegisterTaskController getInstance(){
        if(instance == null){
            synchronized (RegisterTaskController.class) {
                instance = new RegisterTaskController();
            }
        }
        return instance;
    }
}
