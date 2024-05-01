package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator.StatusType.Active;
import static pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator.StatusType.NotActive;

public class CollaboratorRepository {
    public List<Collaborator> collaboratorList;

    public CollaboratorRepository(){
        collaboratorList=new ArrayList<>();
    }

    /** The method gets the List of Collaborators
     *
     * @return List of Collaborators
     */
    public List<Collaborator> getCollaboratorList(){
        return List.copyOf(collaboratorList);
    }

    /**
     * This method creates and instances a new Collaborator after verification made in UI
     * @param name of collaborator
     * @param birthday of collaborator
     * @param admissionDate of collaborator
     * @param address of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity of collaborator
     * @param email of collaborator
     * @param phoneNumber of collaborator
     * @param docType of collaborator
     * @param docIDNumber of collaborator
     * @param jobCategory of collaborator
     *
     */

    public Optional<Collaborator> createCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String email, String phoneNumber, DocType.Type docType, int docIDNumber, JobCategory jobCategory){
        Optional<Collaborator> newCollab;
        Collaborator collab = new Collaborator(name,birthday,admissionDate,address,addressZipCode,addressCity,phoneNumber,email,docType,docIDNumber,jobCategory);
        newCollab = verifyCollaboratorExistAndSave(collab);
        addCollaborator(collab);
        return newCollab;
    }


    private Optional<Collaborator> verifyCollaboratorExistAndSave(Collaborator collab) {
        Optional<Collaborator> newCollab = Optional.empty();
        boolean operationSucess = false;
        if (!collaboratorList.contains(collab)){
            operationSucess=collaboratorList.add(collab);
            newCollab=Optional.of(collab);
        }
        if (!operationSucess){
            newCollab=Optional.empty();
        }
        return newCollab;
    }

    /**Adds the collaborator to the List of Collaborators
     *
     * @param collaborator
     * @return
     */
    public Optional<Collaborator> addCollaborator(Collaborator collaborator){
        Optional<Collaborator> newCollaborator = Optional.empty();
        newCollaborator = Optional.of(collaborator);
        if (isValidCollaborator(collaborator)){
            collaboratorList.add(collaborator);
        }
        return newCollaborator;
    }


    /**Verifies if collaborator does not exist
     *
     * @param collaborator
     * @return true if collaborator does not exist
     */
    private boolean isValidCollaborator(Collaborator collaborator) {
        boolean isValid = !collaboratorList.contains(collaborator);
        return isValid;
    }

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

    /**
     * Method to change the status of Not Active Collaborators to Active
     *
     * @param team where collaborator has been added to
     * @return
     */

    public boolean activateCollaborators(Team team){
        for(Collaborator c : team.getTeamList()){
            if(c.getStatus()==NotActive){
                c.setStatus(Active);
            }
        }
        return false;
    }


    /**This method gets the List of Collaborators that are not active according to the List of Skills given
     *
     * @param skill of collaborator
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
     * @param collaborators - List of Collaborators
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

    /** The method searches for the Collaborator by their DocIDNumber
     *
     * @param docIDNumber of collaboraator
     * @return collaborator
     */

    public Collaborator searchForCollaboratorByIDNumber(int docIDNumber){
        Collaborator collaboratorFound = null;
        List<Collaborator> collaborators=getCollaboratorList();
        for(Collaborator c : collaborators){
            if(c.getDocIDNumber()==docIDNumber){
                collaboratorFound=c;
            }
        }
        return collaboratorFound;
    }

    /** The method searches for the Collaborator by their index on the list
     *
     * @param index of collaboraator
     * @return collaborator
     */

    public Collaborator searchForCollaborator(int index){
        List<Collaborator> collaborators=getCollaboratorList();
        Collaborator collaboratorFound=collaborators.get(index);

        return collaboratorFound;
    }

    /** Gets the list of Skills of Collaborator
     *
     * @param collaborator
     * @return list of Skills the Collaborator has
     */

    public List<Skill> getCollaboratorSkillsList(Collaborator collaborator){
        return collaborator.getSkills();
    }

    /** Assigns a Skill to Collaborator
     *
     * @param collaborator
     * @param skillName
     * @return collaborator if skill has been assigned
     */

    public Optional<Collaborator> assignSkill(Collaborator collaborator, Skill skillName) throws CloneNotSupportedException {
        Optional<Collaborator> collabWithSkill=collaborator.setAddSkill(skillName);
        return collabWithSkill;
    }

}
