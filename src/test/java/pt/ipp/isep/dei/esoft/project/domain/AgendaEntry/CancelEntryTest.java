package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CancelEntryTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;

    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();

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
    void cancelEntryTest(){
        entryDto.cancel();
        Optional<Entry> entry = entryRepository.cancelEntry(entryDto);
        assertNotNull(entry.get());
        assertTrue(entry.get().isCanceled());
        assertEquals(entry.get().getStatus().getState(),EntryState.State.Canceled);

    }
}
