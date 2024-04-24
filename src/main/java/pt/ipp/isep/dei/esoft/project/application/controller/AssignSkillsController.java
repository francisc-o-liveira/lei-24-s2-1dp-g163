package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;

public class AssignSkillsController {

    public CollaboratorRepository collaboratorRepository;

    public SkillRepository skillRepository;

    public AssignSkillsController(){
        getCollaboratorRepository();
        getSkillRepository();
    }

    /**Returns the repository of Skills
     *
     * @return repository of Skills
     */
    public SkillRepository getSkillRepository(){
        if (skillRepository==null) {
            Repositories repositories=Repositories.getInstance();
            skillRepository=repositories.getSkillRepository();
        }
        return skillRepository;
    }

    /**Returns the repository of Collaborators
     *
     * @return collaborator repository
     */
    public CollaboratorRepository getCollaboratorRepository(){
        if(collaboratorRepository==null){
            Repositories repositories=Repositories.getInstance();
            collaboratorRepository=repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }



    /**This method assigns Skills to a Collaborator
     *
     * @param docIDNumber
     * @param skills
     * @throws NullPointerException if the collaborator is not found, which means the collaborator does not exist
     * @throws IllegalArgumentException if the skill does not exist
     */
    public void assignSkills(int docIDNumber, List<Skill> skills){
        Collaborator assigningCollaborator=collaboratorRepository.searchForCollaborator(docIDNumber);
        if(assigningCollaborator==null){
            throw new NullPointerException("The Collaborator does not exist.");
        }
        for(Skill s : skills){
            if(skillRepository.verifyIfExistAndSave(s)){
                throw new IllegalArgumentException("The Skill does not exist.");
            }
            /*if(assigningCollaborator.verifyIfHaveSkill(s)){
                throw new NullPointerException("The Collaborator already has this Skill."); //needs to be changed
            }*/
            assigningCollaborator.setAddSkill(s);
        }
    }

    /** This method shows the details of the collaborator
     *
     * @return details of Collaborator
     * @throws NullPointerException if the collaborator is not found, which means the collaborator does not exist
     */
    public Collaborator showCollaborator(int docIDNumber){
        Collaborator collaborator= collaboratorRepository.searchForCollaborator(docIDNumber);
        if(collaborator==null){
            throw new NullPointerException("The Collaborator does not exist.");
        }
        return collaborator;
    }


    /** Method to show Skills that have been assigned to a Collaborator
     *
     * @param collaborator
     * @param allSkills
     */
    public List<Skill> filterAssignedSkills(Collaborator collaborator, List<Skill> allSkills){
        List<Skill> skillsOfCollaborator=new ArrayList<>();
        for(Skill s : allSkills){
           if(collaborator.verifyIfHaveSkill(s)){
               skillsOfCollaborator.add(s);
           }
        }
        return skillsOfCollaborator;
    }

    /*public boolean checkAuthorization(Employee userEmail){

    }*/


}
