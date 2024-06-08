package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CancelEntryTest {

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
        mapper = new EntryMapper();
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
                new Date(2023, 6, 1),new Tempo(8),
                new EntryState(),
                "Test Task",
                "Description of Test Task",
                Task.DegreeUrgency.Medium,
                new Tempo(2),
                gs,
                "123"
        );
        entryRepository.getToDo().saveNewEntry(mapper.toDomain(entryDto));
        entryRepository.assignEntryOnAgenda(entryDto);

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
