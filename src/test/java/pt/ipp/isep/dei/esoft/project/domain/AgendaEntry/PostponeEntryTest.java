package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class PostponeEntryTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;

    private Date postDate;

    private Tempo startTime;


    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();

        entryDto = new EntryDto(
                new Date(2023, 6, 1),
                new EntryState(),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                null,
                "REF123"
        );
        postDate = new Date(2024,7,30);
        startTime =new Tempo(14,0);
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
