package pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntryRepositoryTest {

    private AssignEntryOnAgendaController controller;
    private EntryRepository entryRepository;
    private EntryDto entryDto;

    private EntryMapper mapper;

    private String name;
    private String address;
    private String city;
    private String zipCode;
    private double area;

    private String email;
    private GreenSpace.Type type;


    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        controller = AssignEntryOnAgendaController.getInstance();
        Organization greenSpaceRepository = Repositories.getInstance().getOrganizationRepository();
        name = "isep";
        address = "rua sao tome";
        city = "Porto";
        zipCode ="4400-123";
        area = 20.0;
        type = GreenSpace.Type.Garden;
        email = "gsm@this.app";
        GreenSpaceDto gs =new GreenSpaceDto(area, new Address(zipCode,address,city),name,type,email);
        greenSpaceRepository.registerGreenSpace(gs);

        entryDto = new EntryDto(
                new Date(2023, 6, 1),new Tempo(8),
                new EntryState(),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                gs,
                "123"
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
    void testGetStates() {
        List<EntryState.State> states = controller.getStates();
        assertNotNull(states);
        assertTrue(states.contains(EntryState.State.Assigned));
    }

}
