package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private List<Team> teams;

    public TeamRepository(Team team){
        teams.add(team);
    }
    public List<Team> getTeam(){

    }

    public List<Team> getTeamBySkill(Skill skills){
        List<Team> teamWithSkills=new ArrayList<>();
        for(Team team : teams){
            if(team.getSkill()==skills){
                teamWithSkills.add(team);
            }
        }
        return teamWithSkills;
    }
}
