package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;

public class Team {
    private List<Collaborator> collaboratorsTeam;
    private int sizeTeam;
    private Skill skillsSelected;

    public Team(List<Collaborator> collaboratorsTeam, int sizeTeam, Skill skillsSelected){
            this.collaboratorsTeam=collaboratorsTeam;
            this.sizeTeam=sizeTeam;
            this.skillsSelected=skillsSelected;
    }

    public List<Collaborator> getTeamList(){
        return collaboratorsTeam;
    }

    /*public List<Collaborator> getTeamListBySkills(Skill skillsSelected){

    }*/

}
