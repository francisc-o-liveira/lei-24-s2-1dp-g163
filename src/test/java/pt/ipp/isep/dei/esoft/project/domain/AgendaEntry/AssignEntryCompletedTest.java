package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssignEntryCompletedTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;
    private EntryMapper entryMapper;
    private Entry entry;
    private Date completionDate;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private double area;

    private String email;
    private GreenSpace.Type type;

    @BeforeEach
    void setUp() {
        entryMapper =new EntryMapper();
        entryRepository = Repositories.getInstance().getEntryRepository();
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
                new EntryState(EntryState.State.Assigned),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                gs,
                "123"
        );
        completionDate=new Date(2024,6,7);
        Collaborator c = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,4,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351 916835384","joaquim@gmail.com", DocType.Type.CitizenCard,197232131,new JobCategory("Gardener"));
        entryDto.setCollaboratorThatCompleted(c);
        entry = entryMapper.toDomain(entryDto);
        entry.setCollaboratorThatCompleted(c);
        entry.getStatus().setState(EntryState.State.Done);
        entryRepository.getAgenda().add(entry);
    }

    @Test
    void completeTaskTest(){
        Optional<Entry> entry = entryRepository.completeTaskCollaborator(entryDto);
        assertNotNull(entry.get());
        assertTrue(entry.get().isCompleted());
        assertEquals(entry.get().getStatus().getState(),EntryState.State.Done);

    }
}
