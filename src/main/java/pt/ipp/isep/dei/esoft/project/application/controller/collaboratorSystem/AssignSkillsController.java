package pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem;

import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.AssignSkillsController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssignSkillsController {

    /**Repository of Collaborators*/
    private CollaboratorRepository collaboratorRepository;

    /**Repository of Skills*/
    private SkillRepository skillRepository;

    /** Initializes the controllers */
    private AssignSkillsController(){
        getDataNeededToAssign();
    }

    /** Gets the repositories from the Repositories Instances */
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
     * @return collaborator on the index given
     */
    public Collaborator getCollaborator(int index){
        return collaboratorRepository.searchForCollaborator(index);
    }

    /** Gets the list of Skills of the collaborator
     *
     * @param collaborator to get the skills from
     * @return list of skills of Collaborator
     */

    public List<Skill> getCollaboratorSkills(Collaborator collaborator){
        return collaboratorRepository.getCollaboratorSkillsList(collaborator);
    }

    /**Gets the list of all the skills
     *
     * @return List of all Skills
     */

    public List<Skill> getAllSkills(){
        return skillRepository.getSkillList();
    }

    /** Gets the list of Skills that Collaborator does not have
     *
     * @param collaborator that doesn't have the Skills
     * @return list of Skills left to assign to Collaborator
     */

    public List<Skill> filterSkillsToAssign(Collaborator collaborator){
        List<Skill> collaboratorSkills=getCollaboratorSkills(collaborator);
        List<Skill> allSkills=getAllSkills();
        List<Skill> skillsLeftToAssign = new ArrayList<>();
        for(Skill skill : allSkills){
            if(!collaboratorSkills.contains(skill)){
                skillsLeftToAssign.add(skill);
            }
        }
        return skillsLeftToAssign;
    }

    /**This method assigns a Skill to a Collaborator
     *
     * @return Optional of collaborator if skill has been assigned; null if skills haven't been assigned
     */
    public Optional<Collaborator> assignSkills(Collaborator collaborator, Skill skillName) throws CloneNotSupportedException {
        return collaboratorRepository.assignSkill(collaborator,skillName);
    }



    private static AssignSkillsController instance;
    public static AssignSkillsController getInstance(){
        if(instance == null){
            synchronized (AssignSkillsController.class) {
                instance = new AssignSkillsController();
            }
        }
        return instance;
    }
}

