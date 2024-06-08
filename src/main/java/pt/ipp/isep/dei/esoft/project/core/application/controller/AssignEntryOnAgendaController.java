package pt.ipp.isep.dei.esoft.project.core.application.controller;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for assigning entries on the agenda.
 */
public class AssignEntryOnAgendaController {

    private EntryRepository entryRepository;
    private EntryMapper mapper;

    // Private constructor to enforce singleton pattern
    private AssignEntryOnAgendaController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        mapper = new EntryMapper();
    }

    /**
     * Retrieves the to-do list of entries.
     *
     * @return A list of entry DTOs.
     */
    public List<EntryDto> getToDoList(){
        return mapper.entryListToEntryDtoList(entryRepository.getToDo().getToDo());
    }

    /**
     * Retrieves the agenda.
     *
     * @return A list of entry DTOs representing the agenda.
     */
    public List<EntryDto> getAgenda(){
        return mapper.entryListToEntryDtoList(entryRepository.getAgenda().getList());
    }

    /**
     * Assigns an entry on the agenda.
     *
     * @param entryDto The entry to be assigned.
     * @param newDate The new date for the entry.
     * @param startHour The start hour for the entry.
     * @return True if the entry is successfully assigned on the agenda, false otherwise.
     */
    public boolean assignEntryOnAgenda(EntryDto entryDto, Date newDate, Tempo startHour){
        entryDto.setEntryAgenda(newDate, startHour);
        return entryRepository.assignEntryOnAgenda(entryDto).isPresent();
    }

    /**
     * Retrieves the states of entries.
     *
     * @return A list of entry states.
     */
    public List<EntryState.State> getStates(){
        return Arrays.asList(EntryState.State.values());
    }

    // Singleton pattern implementation
    private static AssignEntryOnAgendaController instance;
    public static AssignEntryOnAgendaController getInstance(){
        if(instance == null){
            synchronized (AssignEntryOnAgendaController.class) {
                instance = new AssignEntryOnAgendaController();
            }
        }
        return instance;
    }

    /**
     * Saves the agenda and to-do list to the database.
     */
    public void saveToDB() {
        entryRepository.getAgenda().saveToDB();
        entryRepository.getToDo().saveToDB();
    }
}
