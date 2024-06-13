package pt.ipp.isep.dei.esoft.project.domain.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.core.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.VehicleDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.TeamMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.core.application.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Address;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private AssignEntryOnAgendaController controller;
    private EntryRepository entryRepository;
    private EntryDto entryDto;
    private Entry entry;

    private TeamDto teamDto;
    private TeamMapper teamMapper;
    private TeamRepository teamRepository;
    private EntryMapper entryMapper;
    private VehicleDto vehicleDto;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private double area;

    private String email;
    private GreenSpace.Type type;
    private Team team;

    @BeforeEach
    void setUp() {
        entryMapper =new EntryMapper();
        entryRepository = Repositories.getInstance().getEntryRepository();
        teamRepository=Repositories.getInstance().getTeamRepository();
        teamMapper=new TeamMapper();
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
                "456"
        );
        List<Skill> skillList  = new ArrayList<>();
        String name="";
        team = new Team(4, 1, skillList, name);
        teamRepository.addTeam(team);
        entry = entryMapper.toDomain(entryDto);
        entryRepository.getAgenda().add(entry);
    }

    @Test
    void addCollaborator() {
        List<Skill> skillList  = new ArrayList<>();
        String name="";
        Team team = new Team(4,1,skillList,name);
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,743626422,new JobCategory("Garder"));
        assertTrue(team.addCollaborator(cTest2));
    }

    @Test
    void getTeamList() {
        List<Skill> skillList  = new ArrayList<>();
        String name="";
        Team team = new Team(4,1,skillList,name);
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,743626422,new JobCategory("Garder"));
        team.addCollaborator(cTest2);
        List<Collaborator>expectResult=new ArrayList<>();
        expectResult.add(cTest2);
        List<Collaborator>result=team.getTeamList();
        assertEquals(expectResult,result);
    }

    @Test
    void isPossible() {
    }

    @Test
    void assignTeamTest(){
        teamDto=teamMapper.teamToTeamDto(team);
        entryDto.assignTeam(teamDto);
        Optional<Entry> entry = entryRepository.assignTeamOnEntry(entryDto);
        assertNotNull(entry.get());
        assertNotNull(entry.get().getTeamAssigned());
        assertEquals(entry.get().getTeamAssigned().getTeamName(),"");
    }
}