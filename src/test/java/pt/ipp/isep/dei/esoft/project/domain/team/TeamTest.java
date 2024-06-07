package pt.ipp.isep.dei.esoft.project.domain.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignEntryOnAgendaController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TeamDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
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

    private TeamDto teamDto;

    @BeforeEach
    void setUp() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        controller = AssignEntryOnAgendaController.getInstance();

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

        teamDto =new TeamDto(null,null,"FC Porto");


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
        entryDto.assignTeam(teamDto);
        Optional<Entry> entry = entryRepository.assignTeamOnEntry(entryDto);
        assertNotNull(entry.get());
        assertNotNull(entry.get().getTeamAssigned());
        assertEquals(entry.get().getTeamAssigned().getTeamName(),"FC Porto");
    }
}