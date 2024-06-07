package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
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

public class AssignEntryCompletedTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;

    private Date completionDate;

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

        completionDate = new Date(2024,6,7);

    }

    @Test
    void completeTaskTest(){
        Collaborator c = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351 916835384","joaquim@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        entryDto.completeTask(completionDate,c);
        Optional<Entry> entry = entryRepository.completeTaskCollaborator(entryDto);
        assertNotNull(entry.get());
        assertTrue(entry.get().isCompleted());
        assertEquals(entry.get().getStatus().getState(),EntryState.State.Done);

    }
}
