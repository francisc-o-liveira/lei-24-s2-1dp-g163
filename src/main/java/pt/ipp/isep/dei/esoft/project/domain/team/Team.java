package pt.ipp.isep.dei.esoft.project.domain.team;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Collaborator> collaboratorsTeam;
    private int maxSizeTeam;
    private int minSizeTeam;
    private List<Skill> skillsSelected;

    public Team(int maxSize, int minSize, List<Skill> skillsSelected){
        collaboratorsTeam = new ArrayList<Collaborator>();
        this.maxSizeTeam=maxSize;
        this.minSizeTeam=minSize;
        this.skillsSelected=skillsSelected;
    }

    /**
     * For clone method only
     * @param collaboratorsTeam
     * @param skillsSelected
     * @param maxSizeTeam
     * @param minSizeTeam
     */
    public Team(List<Collaborator> collaboratorsTeam, List<Skill> skillsSelected,int maxSizeTeam, int minSizeTeam){
        this.collaboratorsTeam=collaboratorsTeam;
        this.skillsSelected=skillsSelected;
        this.maxSizeTeam=maxSizeTeam;
        this.minSizeTeam=minSizeTeam;
    }

    public boolean addCollaborator(Collaborator collab){
        if(this.maxSizeTeam>collaboratorsTeam.size()){
            collaboratorsTeam.add(collab);
            return true;
        }
        return false;
    }


    public List<Collaborator> getTeamList(){
        return collaboratorsTeam;
    }

    public List<Skill> getSkills(){
        return this.skillsSelected;
    }

    public Team clone(){
        return new Team(this.collaboratorsTeam, this.skillsSelected, this.maxSizeTeam,this.minSizeTeam);
    }

    public boolean isPossible() {
        return collaboratorsTeam.size()<=maxSizeTeam && collaboratorsTeam.size()>=minSizeTeam;
    }

    @Override
    public String toString(){
        return String.format("Collaborators Team: %s\n" +
                "Max Size Team: %d\n" +
                "Min Size Team: %d\n" +
                "Skills Selected: %s\n", collaboratorsTeam, maxSizeTeam, minSizeTeam, skillsSelected);
    }

    public int getSize(){
        return collaboratorsTeam.size();
    }

    /*public List<Collaborator> getTeamListBySkills(Skill skillsSelected){

    }*/

}
