package pt.ipp.isep.dei.esoft.project.core.application.controller;

import pt.ipp.isep.dei.esoft.project.core.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;

/**
 * Controller for viewing task lists assigned to a collaborator.
 */
public class ViewTaskListAssignedCollabController {
    private final EntryRepository entryRepository;
    private final ApplicationSession session;
    private final CollaboratorRepository collaboratorRepository;
    private final EntryMapper mapper;

    private static ViewTaskListAssignedCollabController instance;

    /**
     * Initializes a new instance of the ViewTaskListAssignedCollabController class.
     */
    public ViewTaskListAssignedCollabController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        session = ApplicationSession.getInstance();
        mapper = new EntryMapper();
    }

    /**
     * Gets the singleton instance of the controller.
     *
     * @return the singleton instance
     */
    public static ViewTaskListAssignedCollabController getInstance() {
        if (instance == null) {
            synchronized (ViewTaskListAssignedCollabController.class) {
                if (instance == null) {
                    instance = new ViewTaskListAssignedCollabController();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the entries assigned to the current user within the specified date range.
     *
     * @param firstDate  the start date of the range
     * @param secondDate the end date of the range
     * @return a list of EntryDto objects representing the entries
     */
    public List<EntryDto> getEntrysAssignedToMe(Date firstDate, Date secondDate) {
        Collaborator collaborator = getCollaboratorByEmail(getCollaboratorFromSession());
        return mapper.entryListToEntryDtoList(entryRepository.getAgenda().getEntrysByCollaboratorInAgenda(collaborator, firstDate, secondDate));
    }

    /**
     * Marks an entry as completed by the current user on the specified date.
     *
     * @param entryDto      the entry to be marked as completed
     * @param completedDate the date the entry was completed
     * @return true if the entry was successfully marked as completed, false otherwise
     */
    public boolean assignEntryCompleted(EntryDto entryDto, Date completedDate) {
        Collaborator collaborator = getCollaboratorByEmail(getCollaboratorFromSession());
        entryDto.completeTask(completedDate, collaborator);
        return entryRepository.completeTaskCollaborator(entryDto).isPresent();
    }

    /**
     * Gets the email address of the current collaborator from the session.
     *
     * @return the email address of the current collaborator
     */
    private String getCollaboratorFromSession() {
        return session.getCurrentSession().getUserEmail();
    }

    /**
     * Retrieves a collaborator by their email address.
     *
     * @param email the email address of the collaborator
     * @return the Collaborator object
     */
    private Collaborator getCollaboratorByEmail(String email) {
        return collaboratorRepository.getCollaboratorByEmail(email);
    }
}
