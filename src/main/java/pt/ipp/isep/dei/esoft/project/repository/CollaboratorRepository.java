package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.Active;
import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.NotActive;

public class CollaboratorRepository {
    public List<Collaborator> collaboratorList;

    /**Method to get the List of Not Active Collaborators
     *
     * @return List of Not Active Collaborators
     */
    public List<Collaborator> getCollaboratorsNotActive(){
        List<Collaborator> collaboratorNotActive=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==NotActive){
                collaboratorNotActive.add(c);
            }
        }
        return collaboratorNotActive;
    }

    /**Method to change the status of Not Active Collaborators to Active
     *
     * @param team
     */

    public void activateCollaborators(Team team){
        for(Collaborator c : team.getTeamList()){
            if(c.getStatus()==NotActive){
                c.setStatus(Active);
            }
        }
    }


    /**This method gets the List of Collaborators that are not active according to the List of Skills given
     *
     * @param skill
     * @return List of Not Active Collaborators with the given skills
     */

    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skill){
        List<Collaborator> collaboratorNotActiveBySkills=new ArrayList<>();
        for(int i=0; i<skill.size(); i++){
            for(Collaborator c : collaboratorList){
                if(c.getStatus()==NotActive && c.verifyIfHaveSkill(skill.get(i))){
                    collaboratorNotActiveBySkills.add(c);
                }
            }
        }
        sortCollaboratorsByNumberOfSkills(collaboratorNotActiveBySkills);
        return collaboratorNotActiveBySkills;
    }


    /** This method sorts, in ascending order, the List of Collaborators by the number of Skills they have
     *
     * @param collaborators
     */

    public void sortCollaboratorsByNumberOfSkills(List<Collaborator> collaborators){
        Collaborator temp;
        for(int i=0; i<collaborators.size()-1; i++){
            for(int j=i+1; j< collaborators.size(); j++){
                if(collaborators.get(i).getNumberOfSkills()<collaborators.get(j).getNumberOfSkills()){
                    temp=collaborators.get(i);
                    collaborators.set(i, collaborators.get(j));
                    collaborators.set(j, temp);
                }
            }
        }
    }

    public Optional<Collaborator> addCollaborator(Collaborator collaborator){
        Optional<Collaborator> newCollaborator = Optional.empty();
        newCollaborator = Optional.of(collaborator);
        if (isValidCollaborator(collaborator)){
            saveCollaborator(collaborator);
        }
        return newCollaborator;
    }

    private void saveCollaborator(Collaborator collaborator) {
        collaboratorList.add(collaborator);
    }

    private boolean isValidCollaborator(Collaborator collaborator) {
        boolean isValid = !collaboratorList.contains(collaborator);
        return isValid;
    }

    /** The method searches for the Collaborator by their DocIDNumber
     *
     * @param docIDNumber
     * @return collaborator
     */

    public Collaborator searchForCollaborator(int docIDNumber){
        Collaborator collaboratorFound = null;
        List<Collaborator> collaborators=getCollaboratorList();
        for(Collaborator c : collaborators){
            if(c.getDocIDNumber()==docIDNumber){
                collaboratorFound=c;
            }
        }
        return collaboratorFound;
    }

    /** The method gets the List of Collaborators
     *
     * @return List of Collaborators
     */
    public List<Collaborator> getCollaboratorList(){
        return List.copyOf(collaboratorList);
    }
}
