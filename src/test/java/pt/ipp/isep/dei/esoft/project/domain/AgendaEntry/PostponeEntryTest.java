package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class PostponeEntryTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;

    private Date postDate;

    private Tempo startTime;
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
        Organization greenSpaceRepository = Repositories.getInstance().getOrganizationRepository();
        name = "isep";
        address = "rua sao tome";
        city = "Porto";
        zipCode ="4400-123";
        area = 20.0;
        type = GreenSpace.Type.Garden;
        email = "gsm@this.app";
        GreenSpaceDto gs=new GreenSpaceDto(area, new Address(zipCode,address,city),name,type,email);
        greenSpaceRepository.registerGreenSpace(gs);
        entryDto = new EntryDto(
                new Date(2023, 6, 1),
                new EntryState(EntryState.State.Assigned),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                gs,
                "123"
        );
        postDate = new Date(2024,7,30);
        startTime =new Tempo(14,0);
        entryRepository.registerNewTask(entryDto);
        entryRepository.assignEntryOnAgenda(entryDto);
    }

    @Test
    void postponeTest(){
        entryDto.postpone(postDate,startTime);
        Optional<Entry> entry = entryRepository.postponeEntry(entryDto);
        assertNotNull(entry.get());
        assertTrue(entry.get().isPostpone());
        assertEquals(entry.get().getStartDate(),postDate);
        assertEquals(entry.get().getStartHour(),startTime);
    }
}
