package pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntryRepositoryTest {

    private AssignEntryOnAgendaController controller;
    private EntryRepository entryRepository;
    private EntryDto entryDto;

    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        controller = AssignEntryOnAgendaController.getInstance();

        entryDto = new EntryDto(
                new Date(2023, 6, 1),new Tempo(8),
                new EntryState(),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                null,
                "REF123"
        );
    }

    @Test
    void testGetToDoList() {
        entryRepository.registerNewTask(entryDto);
        List<EntryDto> toDoList = controller.getToDoList();
        assertFalse(toDoList.isEmpty());
        assertEquals("Test Task", toDoList.get(0).getTitle());
    }

    @Test
    void testGetAgenda() {
        entryRepository.registerNewTask(entryDto);
        controller.assignEntryOnAgenda(entryDto, new Date(2023, 6, 2), new Tempo(9));
        List<EntryDto> agendaList = controller.getAgenda();
        assertFalse(agendaList.isEmpty());
        assertEquals("Test Task", agendaList.get(0).getTitle());
    }

    @Test
    void testAssignEntryOnAgenda() {
        entryRepository.registerNewTask(entryDto);
        boolean result = controller.assignEntryOnAgenda(entryDto, new Date(2023, 6, 2), new Tempo(9));
        assertTrue(result);
    }

    @Test
    void testGetStates() {
        List<EntryState.State> states = controller.getStates();
        assertNotNull(states);
        assertTrue(states.contains(EntryState.State.Assigned));
    }

    @Test
    void testSaveToDB() {
        entryRepository.registerNewTask(entryDto);
        controller.assignEntryOnAgenda(entryDto, new Date(2023, 6, 2), new Tempo(9));
        assertDoesNotThrow(() -> controller.saveToDB());
    }
}
