package pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.mapper.TeamMapper;
import pt.ipp.isep.dei.esoft.project.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssignVehicleTest {

    private EntryRepository entryRepository;
    private EntryDto entryDto;
    private VehicleMapper vehicleMapper;
private TeamMapper teamMapper;
    private EntryMapper entryMapper;
    private VehicleDto vehicleDto;
    private TeamDto teamDto;
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
        vehicleMapper = new VehicleMapper();
        entryMapper = new EntryMapper();
        teamMapper = new TeamMapper();
        teamDto =new TeamDto(new ArrayList<>(),new ArrayList<>(),"FC Porto");
        name = "isep";
        address = "rua sao tome";
        city = "Porto";
        zipCode ="4400-123";
        area = 20.0;
        type = GreenSpace.Type.Garden;
        email = "gsm@this.app";
        GreenSpaceDto gs=new GreenSpaceDto(area, new Address(zipCode,address,city),name,type,email);
        vehicleDto = new VehicleDto("ferrari", "spider", Vehicle.Type.LightCargo,"11-AA-11",3000,new Date());
        ArrayList<VehicleDto> vList =  new ArrayList<>();
        vList.add(vehicleDto);
        entryDto = new EntryDto(new Date(2024,6,8),new Tempo(14), new EntryState(),"title","description", Task.DegreeUrgency.Medium,new Tempo(1),gs,"12",teamDto,vList);

    }

    @Test
    void assignVehicleTest() throws CloneNotSupportedException {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        Vehicle vehicle = new Vehicle("ferrari","spider", Vehicle.Type.LightCargo,800,1000,30000,new Date(2000,6,8), new Date(2024,6,7),1000,"11-11-AA",new Date(2024,6,8),20000);
        TeamRepository teamRepository = Repositories.getInstance().getTeamRepository();
        teamRepository.addTeam(teamMapper.toDomain(teamDto));
        entryRepository.getToDo().saveNewEntry(entryMapper.toDomain(entryDto));
        entryRepository.assignEntryOnAgenda(entryDto);
        Optional<Entry> entry = entryRepository.assignVehicleOnEntry(entryDto);
        assertNotNull(entry.get());
        assertNotNull(entry.get().getVehicleList());
//        assertTrue(entry.get().getVehicleList().contains(vehicle));
    }
}
