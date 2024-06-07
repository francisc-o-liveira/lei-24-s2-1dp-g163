package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssignVehicleTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;
    private VehicleMapper vehicleMapper;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        vehicleMapper = new VehicleMapper();
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
        vehicleDto = new VehicleDto("ferrari", "spider", Vehicle.Type.LightCargo,"11-AA-11",3000,new Date());

    }

    @Test
    void assignVehicleTest(){
        assertTrue(entryDto.assignVehicle(vehicleDto));
        Vehicle vehicle = vehicleMapper.vehicleDtoToVehicle(vehicleDto);
        Optional<Entry> entry = entryRepository.assignVehicleOnEntry(entryDto);
        assertNotNull(entry.get());
        assertNotNull(entry.get().getVehicleList());
        assertTrue(entry.get().getVehicleList().contains(vehicle));
    }
}
