package pt.ipp.isep.dei.esoft.project.domain.team;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void addCollaborator() {
        List<Skill> skillList  = new ArrayList<>();
        Team team = new Team(4,1,skillList);
        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,743626422,new JobCategory("Garder"));
        assertTrue(team.addCollaborator(cTest2));
    }

    @Test
    void getTeamList() {
        List<Skill> skillList  = new ArrayList<>();
        Team team = new Team(4,1,skillList);
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
}