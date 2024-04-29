package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;
import java.util.Optional;

public class AssignSkillsController {

    public CollaboratorRepository collaboratorRepository;

    public SkillRepository skillRepository;

    public AssignSkillsController(){
        getDataNeededToAssign();
    }

    /** Gets the repositories
     *
     */
    public void getDataNeededToAssign(){
        if (skillRepository==null) {
            Repositories repositories=Repositories.getInstance();
            skillRepository=repositories.getSkillRepository();
        }
        if(collaboratorRepository==null){
            Repositories repositories=Repositories.getInstance();
            collaboratorRepository=repositories.getCollaboratorRepository();
        }
    }

    /**Gets the list of collaborators
     *
     * @return List of Collaborators
     */
    public List<Collaborator> getCollaboratorList(){
        return collaboratorRepository.getCollaboratorList();
    }

    /** Searches for Collaborator given the index
     *
     * @param index of Collaborator
     * @return collaborator with that ID Number
     */
    public Collaborator getCollaborator(int index){
        return collaboratorRepository.searchForCollaborator(index);
    }

    /** Gets the list of Skills of the collaborator
     *
     * @param collaborator
     * @return list of skills of Collaborator
     */

    public List<Skill> getCollaboratorSkills(Collaborator collaborator){
        return collaboratorRepository.getCollaboratorSkillsList(collaborator);
    }

    /**Gets the list of all the skills
     *
     * @return All Skills
     */

    public List<Skill> getAllSkills(){
        return skillRepository.getSkillList();
    }

    /** Gets the list of Skills that Collaborator does not have
     *
     * @param collaborator
     * @return list of Skills left to assign to Collaborator
     */

    public List<Skill> filterSkillsToAssign(Collaborator collaborator){
        List<Skill> collaboratorSkills=getCollaboratorSkills(collaborator);
        List<Skill> allSkills=getAllSkills();
        List<Skill> skillsLeftToAssign = null;
        for(Skill skill : allSkills){
            if(!collaboratorSkills.contains(skill)){
                skillsLeftToAssign.add(skill);
            }
        }
        return skillsLeftToAssign;
    }

    /**This method assigns a Skill to a Collaborator
     *
     * @return collaborator if skill has been assigned
     */
    public Optional<Collaborator> assignSkills(Collaborator collaborator, Skill skillName){
        return collaboratorRepository.assignSkill(collaborator,skillName);
    }
}

