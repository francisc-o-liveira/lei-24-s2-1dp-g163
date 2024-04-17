package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;

public class Team {
    private List<Collaborator> collaboratorsTeam;
    private int sizeTeam;
    private Skill skillSelected;

    private List<Skill> skillsSelected;

    public Team(List<Collaborator> collaboratorsTeam, int sizeTeam, Skill skillSelected){
            this.collaboratorsTeam=collaboratorsTeam;
            this.sizeTeam=sizeTeam;
            this.skillSelected=skillSelected;
    }

    public Team(List<Collaborator> collaboratorsTeam, int sizeTeam, List<Skill> skillsSelected){
        this.collaboratorsTeam=collaboratorsTeam;
        this.sizeTeam=sizeTeam;
        this.skillsSelected=skillsSelected;
    }


    public List<Collaborator> getTeamList(){
        return collaboratorsTeam;
    }

    public Skill getSkill(){
        return this.skillSelected;
    }

    /*public List<Collaborator> getTeamListBySkills(Skill skillsSelected){

    }*/

}
