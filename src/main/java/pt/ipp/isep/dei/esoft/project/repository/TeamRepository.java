package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {

    private List<Team> teams;

    public TeamRepository(){
        teams = new ArrayList<>();
    }

    public Optional<Team> addTeam(Team team){
        Optional<Team> newTeam = Optional.empty();
        boolean operationSucess = false;
        if(teamIsValid(team)){
            newTeam = Optional.of(team.clone());
            operationSucess = teams.add(newTeam.get());
        }
        if (!operationSucess){
            newTeam = Optional.empty();
        }
        return newTeam;
    }

    private boolean teamIsValid(Team team) {
        boolean isValid = !teams.contains(team);
        return isValid;
    }

    public List<Team> removeTeam(Team team){
        if (teams.contains(team)){
            teams.remove(team);
        }
        return teams;
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
