package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.GenerateTeamUI;

import java.util.ArrayList;
import java.util.List;

public class GenerateTeamController{
    public TeamRepository teamRepository;
    public CollaboratorRepository collaboratorRepository;
    public SkillRepository skillRepository;

    /** Method to generate a team according to the List of Skills given
     *
     * @param collaborators
     * @param sizeTeam
     * @param skillsSelected
     * @param maxSize
     */
    public void generateTeam(List<Collaborator> collaborators, int sizeTeam, List<Skill> skillsSelected, int maxSize){
            List<Collaborator> collaboratorsForTeam=getCollaboratorsNotActiveBySkills(skillsSelected);
            List<Collaborator> collaboratorsTeam= new ArrayList<>();
            for(Collaborator c : collaboratorsForTeam){
                if(GenerateTeamUI.getSelected(c) && sizeTeam<maxSize){
                    collaboratorsTeam.add(c);
                    sizeTeam++;
                }
            }
            Team team=new Team(collaboratorsTeam,sizeTeam,skillsSelected);
    }

    /**Gets the list of not active collaborators by the list of Skills needed
     *
     * @param skillsSelected
     * @return Not Active Collaborators
     */
    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skillsSelected){
        return collaboratorRepository.getCollaboratorsNotActiveBySkills(skillsSelected);
    }

    /**Gets the list of teams with the Skills given
     *
     * @param skills
     * @return List of teams with Skill (or Skills)
     */
    public List<Team> getTeamBySkills(List<Skill> skills){
        return teamRepository.getTeamBySkill(skills);
    }

    /** Gets List of Skills
     *
     * @return List of Skills
     */

    public List<Skill> getSkillList(){
        return skillRepository.getSkillList();
    }

    /**Gets list of not active collaborators
     *
     * @return List of Not Active Collaborators
     */

    public List<Collaborator> getCollaboratorsNotActive(){
        return collaboratorRepository.getCollaboratorsNotActive();
    }

    /** Changes the status of collaborators that have been selected for a team
     *
     * @param team
     */
    public void activateCollaborators(Team team){
        collaboratorRepository.activateCollaborators(team);
    }

    /** Gets list of teams
     */
    public List<Team> getTeams(){
        return teamRepository.getTeams();
    }

    /*private (...) getHRMFromSession()*/

}
