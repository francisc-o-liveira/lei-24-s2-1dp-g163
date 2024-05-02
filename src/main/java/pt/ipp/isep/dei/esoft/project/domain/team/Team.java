package pt.ipp.isep.dei.esoft.project.domain.team;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;

/** Domain class for Team Object*/
public class Team {

    /** Parameters to generate a Team */
    private List<Collaborator> collaboratorsTeam;
    private int maxSizeTeam;
    private int minSizeTeam;
    private List<Skill> skillsSelected;

    /** Constructor method to create a team
     *
     * @param maxSize of a team
     * @param minSize of a team
     * @param skillsSelected needed for the team
     */
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

    /** Method to add a collaborator to the team
     *
     * @param collab - collaborator for the team
     * @return true if collaborator has been added to the team
     */

    public boolean addCollaborator(Collaborator collab){
        if(this.maxSizeTeam>collaboratorsTeam.size()){
            collaboratorsTeam.add(collab);
            return true;
        }
        return false;
    }

    /** Gets the list of Collaborators on the team
     *
     * @return collaborators on the team
     */
    public List<Collaborator> getTeamList(){
        return collaboratorsTeam;
    }

    /** Gets the list of Skills of the team
     *
     * @return List of Skills of the Team
     */
    public List<Skill> getSkills(){
        return this.skillsSelected;
    }

    /** Gets the size of the Team
     *
     * @return Team's size
     */
    public int getSize(){
        return collaboratorsTeam.size();
    }


    /** Clone method of Team */
    public Team clone(){
        return new Team(this.collaboratorsTeam, this.skillsSelected, this.maxSizeTeam,this.minSizeTeam);
    }

    /** Checks if it is possible to create the Team
     *
     * @return true if team is possible to be created
     */
    public boolean isPossible() {
        return collaboratorsTeam.size()<=maxSizeTeam && collaboratorsTeam.size()>=minSizeTeam;
    }

    /** Method to get the data of Team in a String
     *
     * @return String with the Team's data
     */
    @Override
    public String toString(){
        return String.format("Collaborators Team: %s\n" +
                "Max Size Team: %d\n" +
                "Min Size Team: %d\n" +
                "Skills Selected: %s\n", collaboratorsTeam, maxSizeTeam, minSizeTeam, skillsSelected);
    }

    @Override
    public boolean equals(Object other){
        if(this==other){
            return true;
        }
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Team otherTeam= (Team) other;
        return this.getTeamList()==otherTeam.getTeamList();
    }

}
