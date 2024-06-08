package pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterToDoEntryTest {

    private EntryRepository repo;
    private EntryMapper mapper;
    private Date startDate;
    private EntryState status;
    private List<VehicleDto> vehicleList;
    private TeamDto teamAssigned;
    private String reference;
    private Date finishDate;
    private Tempo startHour;

    private String name;
    private String address;
    private String city;
    private String zipCode;
    private double area;

    private String email;
    private GreenSpace.Type type;


    @BeforeEach
    void setupAll() {
        repo = new EntryRepository();
        mapper = new EntryMapper();
        startDate = new Date();
        status = new EntryState();
        vehicleList =new ArrayList<>();
        vehicleList.add(new VehicleDto("ferrari","spider", Vehicle.Type.LightPassenger,"11-AA-11",30000,new Date()));
        teamAssigned  = new TeamDto(null,null,"FC Porto");
        reference = "1";
        finishDate = new Date(2024,6,7);
        startHour = new Tempo();
        name = "isep";
        address = "Rua Sao Tome";
        city = "Porto";
        zipCode ="4400-123";
        area = 20.0;
        type = GreenSpace.Type.Garden;
        email = "gsm@this.app";
    }

    @Test
    void testEntryRegistry(){
        Bootstrap boot = new Bootstrap();
        boot.run();
        EntryDto entryDto = new EntryDto("x","banana", Task.DegreeUrgency.High,new Tempo(0,15),new GreenSpaceDto(area,new Address(zipCode,address,city),name,type,email));
        Optional<Entry> entry = repo.registerNewTask(entryDto);
        assertNotNull(entry.get());
        assertEquals(entry.get().getReference(),reference);
        assertEquals(entry.get().getTitle(),"x");
        assertEquals(entry.get().getDescription(),"banana");

    }
}
