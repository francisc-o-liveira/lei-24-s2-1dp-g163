package pt.ipp.isep.dei.esoft.project.domain.team;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Domain class for Team Object*/
public class Team implements Serializable {

    /** Parameters to generate a Team */
    private List<Collaborator> collaboratorsTeam;
    private int maxSizeTeam;
    private int minSizeTeam;
    private List<Skill> skillsSelected;
    private String teamName;

    /** Constructor method to create a team
     *
     * @param maxSize of a team
     * @param minSize of a team
     * @param skillsSelected needed for the team
     * @param teamName for the team
     */
    public Team(int minSize, int maxSize, List<Skill> skillsSelected, String teamName){
        collaboratorsTeam = new ArrayList<Collaborator>();
        this.maxSizeTeam=maxSize;
        this.minSizeTeam=minSize;
        this.skillsSelected=skillsSelected;
        this.teamName=teamName;
    }

    /**
     * For clone method only
     * @param collaboratorsTeam
     * @param skillsSelected
     * @param maxSizeTeam
     * @param minSizeTeam
     * @param teamName
     */
    public Team(List<Collaborator> collaboratorsTeam, List<Skill> skillsSelected,int maxSizeTeam, int minSizeTeam, String teamName){
        this.collaboratorsTeam=collaboratorsTeam;
        this.skillsSelected=skillsSelected;
        this.maxSizeTeam=maxSizeTeam;
        this.minSizeTeam=minSizeTeam;
        this.teamName=teamName;
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
        return new Team(this.collaboratorsTeam, this.skillsSelected, this.maxSizeTeam,this.minSizeTeam, this.teamName);
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
    /** Method to compare if two Teams are the same
     *
     * Two Teams are the same if both have the same list of collaborators
     *
     * @param other - Team to be compared along with other
     * @return true if they are the same
     */
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

    /** Gets the team's name
     *
     * @return name of the team
     */
    public String getTeamName(){
        return teamName;
    }

    /** Gets the ObservableList to fill the list with collaborators of a team
     *
     * @return list of Collaborators of a Team for TableView
     */
    public ObservableList<Collaborator> getObservableTeamList(){
        ObservableList<Collaborator> collabsOfTeam = FXCollections.observableArrayList();
        for(Collaborator c : getTeamList()){
            collabsOfTeam.add(c);
        }
        return collabsOfTeam;
    }

    /** Gets the ObservableList to fill the list with skills of a team
     *
     * @return list of Skills of a Team for TableView
     */
    public ObservableList<Skill> getObservableSkillList(){
        ObservableList<Skill> skillsOfTeam = FXCollections.observableArrayList();
        for(Skill c : getSkills()){
            skillsOfTeam.add(c);
        }
        return skillsOfTeam;
    }


}
