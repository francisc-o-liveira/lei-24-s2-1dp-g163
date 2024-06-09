package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.controller.AssignEntryOnAgendaController;
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
import pt.ipp.isep.dei.esoft.project.core.application.mapper.TeamMapper;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.*;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssignVehicleTest {
    private AssignEntryOnAgendaController controller;
    private EntryRepository entryRepository;
    private EntryDto entryDto;
    private Entry entry;

    private VehicleDto vehicleDto;
    private VehicleMapper vehicleMapper;
    private VehicleRepository vehicleRepository;
    private EntryMapper entryMapper;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private double area;
    private String email;
    private GreenSpace.Type type;
    private Vehicle v1;
    private List<Vehicle> vehiclesAssigning;


    @BeforeEach
    void setUp() throws CloneNotSupportedException {
        entryMapper =new EntryMapper();
        entryRepository = Repositories.getInstance().getEntryRepository();
        vehicleRepository=Repositories.getInstance().getVehicleRepository();
        vehicleMapper=new VehicleMapper();
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
                "123",
                null,
                new ArrayList<>()
        );
        v1 = new Vehicle("VW4", "Golf14", Vehicle.Type.LightCargo, 1300, 1600, 160000, new Date(2005, 10, 1), new Date(2010, 12, 3), 11000, "42-AB-91", new Date(2010, 10, 1), 150000);
        vehiclesAssigning=new ArrayList<>();
        vehiclesAssigning.add(v1);
        vehicleRepository.addVehicle(v1);
        entry = entryMapper.toDomain(entryDto);
        entryRepository.getAgenda().add(entry);
    }

    @Test
    void assignVehicleTest(){
        vehicleDto=vehicleMapper.vehicleToVehicleDto(v1);
        entryDto.assignVehicle(vehicleDto);
        Optional<Entry> entry = entryRepository.assignVehicleOnEntry(entryDto);
        assertNotNull(entry.get());
        assertNotNull(entry.get().getVehicleList());
        assertEquals(entry.get().getVehicleList(),vehiclesAssigning);
    }
}
