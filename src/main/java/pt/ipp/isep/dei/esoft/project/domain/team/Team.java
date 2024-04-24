package pt.ipp.isep.dei.esoft.project.domain.team;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.List;

public class Team {
    private List<Collaborator> collaboratorsTeam;
    private int sizeTeam;
    private List<Skill> skillsSelected;

    public Team(List<Collaborator> collaboratorsTeam, int sizeTeam, List<Skill> skillsSelected){
        this.collaboratorsTeam=collaboratorsTeam;
        this.sizeTeam=sizeTeam;
        this.skillsSelected=skillsSelected;
    }


    public List<Collaborator> getTeamList(){
        return collaboratorsTeam;
    }

    public List<Skill> getSkills(){
        return this.skillsSelected;
    }

    public Team clone(){
        return new Team(this.collaboratorsTeam, this.sizeTeam, this.skillsSelected);
    }

    /*public List<Collaborator> getTeamListBySkills(Skill skillsSelected){

    }*/

}
