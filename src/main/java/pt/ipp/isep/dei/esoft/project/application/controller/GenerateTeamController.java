package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.repository.serv.GenerateTeamServ;
import pt.ipp.isep.dei.esoft.project.repository.serv.GenerateTeamServClass;

import java.util.List;
import java.util.Optional;

/** Controller to generate a team */
public class GenerateTeamController{

    /** Repository of Teams*/
    private TeamRepository teamRepository;
    /** Repository of Collaborators */
    private CollaboratorRepository collaboratorRepository;
    /** Repository of Skills */
    private SkillRepository skillRepository;

    private GenerateTeamServClass serv;

    /** Initializes the controller */
    public GenerateTeamController() {
        getDataNeededToGenerate();
    }

    /** Gets the repositories from Repositories Instances to generate a team
     *
     */
    private void getDataNeededToGenerate() {
        if (skillRepository== null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the JobCategory Repository
            skillRepository = repositories.getSkillRepository();
        }
        if (teamRepository== null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the JobCategory Repository
            teamRepository = repositories.getTeamRepository();
        }
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            // Getting the Collaborator Repository
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        serv = new GenerateTeamServClass();
    }

    /**
     * Method to generate a team according to the List of Skills given
     *
     * @return team if it has been successfully generated
     */
    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, String teamName){
            List<Collaborator> collaboratorsForTeam=getCollaboratorsNotActiveBySkills(skillsSelected);
            return serv.generateTeam(minSizeTeam,maxSizeTeam,skillsSelected,numberCollabForSkill,collaboratorsForTeam,teamName);
    }

    /**Gets the list of not active collaborators by the list of Skills needed
     *
     * @param skillsSelected - the skills needed for the team
     * @return Not Active Collaborators
     */
    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skillsSelected){
        return collaboratorRepository.getCollaboratorsNotActiveBySkills(skillsSelected);
    }

    /** Gets List of Skills
     *
     * @return List of Skills
     */

    public List<Skill> getSkillList(){
        return skillRepository.getSkillList();
    }

    /** Changes the status of collaborators that have been selected for a team
     *
     * @param team - team of selected collaborators
     */
    public void activateCollaborators(Team team){
        collaboratorRepository.activateCollaborators(team);
    }

    /** Gets list of teams
     */
    public List<Team> getTeams(){
        return teamRepository.getTeams();
    }

    /** Saves the team created
     *
     * If the team has been successfully saved, the collaborators assigned to the team change their status
     * @param teamCreated - team with collaborators
     * @return if the team has been saved
     */
    public boolean saveTeam(Team teamCreated) {
        boolean operationSucess=teamRepository.saveTeam(teamCreated);
        if (operationSucess){
            activateCollaborators(teamCreated);
        }
        return operationSucess;
    }

    /**Gets the list of teams with the Skills given
     *
     * @param skills
     * @return List of teams with Skill (or Skills)
     */
    public List<Team> getTeamBySkills(List<Skill> skills){
        return teamRepository.getTeamBySkill(skills);
    }

    /**Gets list of not active collaborators
     *
     * @return List of Not Active Collaborators
     */

    public List<Collaborator> getCollaboratorsNotActive(){
        return collaboratorRepository.getCollaboratorsNotActive();
    }

    public void removeTeam(Team team){
        teamRepository.removeTeam(team);
    }

    /*private (...) getHRMFromSession()*/

}
