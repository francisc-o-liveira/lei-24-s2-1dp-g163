package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.repository.serv.GenerateTeamServ;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {



    @Test
    void addTeamAndGetTeamListTest(){
        ArrayList<Skill> skillsSelected = new ArrayList<>();
        Skill skill1 = new Skill("Skill 1");
        Skill skill2 = new Skill("Skill 2");
        Skill skill3 = new Skill("Skill 3");
        skillsSelected.add(skill1);
        skillsSelected.add(skill2);
        skillsSelected.add(skill3);
        ArrayList<Integer> numbCollabForSkill = new ArrayList<>();
        numbCollabForSkill.add(1);
        numbCollabForSkill.add(2);
        numbCollabForSkill.add(3);
        Collaborator collab1= new Collaborator("Joaquim Manel Mendes Cunha Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        Collaborator collab2= new Collaborator(" Manel Mendes Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        Collaborator collab3= new Collaborator("Mendes Cunha Manuel Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        collab1.setAddSkill(skill1);
        collab2.setAddSkill(skill2);
        collab1.setAddSkill(skill2);
        collab3.setAddSkill(skill3);
        collab2.setAddSkill(skill3);
        collab1.setAddSkill(skill3);
        List<Collaborator> collaboratorsForTeam = new ArrayList<>();
        collaboratorsForTeam.add(collab1);
        collaboratorsForTeam.add(collab2);
        collaboratorsForTeam.add(collab3);
        GenerateTeamServ serv = new GenerateTeamServ();
        Optional<Team> team = serv.generateTeam(2,4,skillsSelected,numbCollabForSkill,collaboratorsForTeam);
        TeamRepository repo = new TeamRepository();
        repo.addTeam(team.get());
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team.get());


        assertEquals(repo.getTeams(),teams);
    }


}